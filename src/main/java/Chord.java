import java.util.*;

public class Chord {
    private final Note rootNote;
    private final ChordFormula chordFormula;
    private final List<Note> notes;

    public Chord(Note rootNote, ChordFormula chordFormula, List<Note> notes) {
        if (notes.size() != 3) {
            throw new IllegalArgumentException("A chord must contain exactly three notes.");
        }

        this.rootNote = rootNote;
        this.chordFormula = chordFormula;
        this.notes = new ArrayList<>(notes);
    }

    public String getChordName() {
        return rootNote.getSpelling() + " " + chordFormula.getDisplaySuffix();
    }

    public Note getRootNote() {
        return rootNote;
    }

    public ChordFormula getChordFormula() {
        return chordFormula;
    }

    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    @Override
    public String toString() {
        return getChordName();
    }
}