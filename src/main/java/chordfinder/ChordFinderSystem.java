package chordfinder;

import java.util.ArrayList;
import java.util.List;

public class ChordFinderSystem {
    private ChordFormulaCatalog chordFormulaCatalog;
    private List<Note> submittedNotes;
    private List<Chord> identifiedChords;
    private String resultMessage;

    public ChordFinderSystem() {
        this.chordFormulaCatalog = new ChordFormulaCatalog();
        this.submittedNotes = new ArrayList<>();
        this.identifiedChords = new ArrayList<>();
        this.resultMessage = "";
    }

    public List<Chord> identifyChord(String submittedNoteText) {
        submittedNotes = validateNotes(submittedNoteText);
        identifiedChords.clear();

        if (submittedNotes.isEmpty()) {
            resultMessage = "Invalid note entry.";
            return identifiedChords;
        }

        List<ChordFormula> formulas = chordFormulaCatalog.getChordFormulas();

        for (Note rootNote : submittedNotes) {
            for (ChordFormula formula : formulas) {
                if (formula.matchesNotes(rootNote, submittedNotes)) {
                    Chord chord = new Chord(rootNote, formula, submittedNotes);
                    identifiedChords.add(chord);
                }
            }
        }

        if (identifiedChords.isEmpty()) {
            resultMessage = "No matching chord found.";
        } else {
            resultMessage = "Matching chord found.";
        }

        return identifiedChords;
    }

    public List<Note> validateNotes(String submittedNoteText) {
        List<Note> validNotes = new ArrayList<>();

        if (submittedNoteText == null || submittedNoteText.isBlank()) {
            return validNotes;
        }

        String[] noteTexts = submittedNoteText.trim().split("\\s+");

        if (noteTexts.length != 3) {
            return validNotes;
        }

        for (String noteText : noteTexts) {
            if (!Note.isValid(noteText)) {
                return new ArrayList<>();
            }

            validNotes.add(Note.from(noteText));
        }

        return validNotes;
    }

    public void defineChordFormula(ChordFormula formula) {
        validateChordFormula(formula);
        chordFormulaCatalog.defineChordFormula(formula);
    }

    private void validateChordFormula(ChordFormula formula) {
        if (formula == null) {
            throw new IllegalArgumentException("Chord formula is required.");
        }

        if (formula.getQualityName() == null || formula.getQualityName().trim().isEmpty()) {
            throw new IllegalArgumentException("Formula name is required.");
        }

        for (ChordFormula existingFormula : getChordFormulas()) {
            if (existingFormula.getQualityName().equalsIgnoreCase(formula.getQualityName())) {
                throw new IllegalArgumentException("Duplicate formula name.");
            }

            if (existingFormula.getFirstInterval() == formula.getFirstInterval()
                    && existingFormula.getSecondInterval() == formula.getSecondInterval()) {
                throw new IllegalArgumentException("Duplicate formula pattern.");
            }
        }
    }

    public void editChordFormula(String qualityName, ChordFormula revisedFormula) {
        boolean updated = chordFormulaCatalog.editChordFormula(qualityName, revisedFormula);

        if (updated) {
            resultMessage = "Chord formula updated.";
        } else {
            resultMessage = "Chord formula not found.";
        }
    }

    public boolean deleteChordFormula(String qualityName) {
        boolean deleted = chordFormulaCatalog.deleteChordFormula(qualityName);

        if (deleted) {
            resultMessage = "Chord formula deleted.";
        } else {
            resultMessage = "Chord formula not found.";
        }

        return deleted;
    }

    public List<ChordFormula> getChordFormulas() {
        return chordFormulaCatalog.getChordFormulas();
    }

    public void displayResult() {
        System.out.println(resultMessage);

        for (Chord chord : identifiedChords) {
            System.out.println(chord.getChordName());
        }
    }

    public ChordFormulaCatalog getChordFormulaCatalog() {
        return chordFormulaCatalog;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
