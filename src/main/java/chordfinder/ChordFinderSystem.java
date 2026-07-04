package chordfinder;
import java.util.*;

public class ChordFinderSystem {
    private final List<ChordFormula> chordFormulas;

    public ChordFinderSystem() {
        this.chordFormulas = new ArrayList<>();
        loadStandardFormulas();
    }

    private void loadStandardFormulas() {
        chordFormulas.add(new ChordFormula("Major", "maj", 4, 7));
        chordFormulas.add(new ChordFormula("Minor", "min", 3, 7));
        chordFormulas.add(new ChordFormula("Augmented", "aug", 4, 8));
        chordFormulas.add(new ChordFormula("Diminished", "dim", 3, 6));
    }

    public List<Chord> identifyChord(String submittedNoteText) {
        List<Note> notes = parseNotes(submittedNoteText);
        List<Chord> matchingChords = new ArrayList<>();
        Set<String> chordNamesAlreadyFound = new LinkedHashSet<>();

        for (Note possibleRoot : notes) {
            for (ChordFormula formula : chordFormulas) {
                if (formula.matches(possibleRoot, notes)) {
                    Chord chord = new Chord(possibleRoot, formula, notes);

                    if (!chordNamesAlreadyFound.contains(chord.getChordName())) {
                        matchingChords.add(chord);
                        chordNamesAlreadyFound.add(chord.getChordName());
                    }
                }
            }
        }

        return matchingChords;
    }

    private List<Note> parseNotes(String submittedNoteText) {
        if (submittedNoteText == null || submittedNoteText.isBlank()) {
            throw new IllegalArgumentException("Please enter exactly three notes.");
        }

        String[] noteTexts = submittedNoteText.trim().split("\\s+");

        if (noteTexts.length < 3) {
            throw new IllegalArgumentException("Please enter exactly three notes. Fewer than three notes were entered.");
        }

        if (noteTexts.length > 3) {
            throw new IllegalArgumentException("Please enter exactly three notes. More than three notes were entered.");
        }

        List<Note> notes = new ArrayList<>();

        for (String noteText : noteTexts) {
            notes.add(Note.from(noteText));
        }

        return notes;
    }

    public void addFormula(ChordFormula formula) {
        if (findFormula(formula.getQualityName()) != null) {
            throw new IllegalArgumentException("A formula with that quality name already exists.");
        }

        chordFormulas.add(formula);
    }

    public void editFormula(String qualityName, ChordFormula revisedFormula) {
        deleteFormula(qualityName);
        chordFormulas.add(revisedFormula);
    }

    public boolean deleteFormula(String qualityName) {
        ChordFormula formula = findFormula(qualityName);

        if (formula == null) {
            return false;
        }

        return chordFormulas.remove(formula);
    }

    public ChordFormula findFormula(String qualityName) {
        for (ChordFormula formula : chordFormulas) {
            if (formula.getQualityName().equalsIgnoreCase(qualityName)) {
                return formula;
            }
        }

        return null;
    }

    public List<ChordFormula> getChordFormulas() {
        return Collections.unmodifiableList(chordFormulas);
    }
}