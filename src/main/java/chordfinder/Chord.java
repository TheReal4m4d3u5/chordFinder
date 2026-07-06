package chordfinder;

import java.util.ArrayList;
import java.util.List;

public class Chord {
    private Note rootNote;
    private ChordFormula chordFormula;
    private List<Note> notes;
    private String chordName;

    public Chord(Note rootNote, ChordFormula chordFormula, List<Note> notes) {
        this.rootNote = rootNote;
        this.chordFormula = chordFormula;
        this.notes = new ArrayList<>(notes);
        this.chordName = rootNote.getSpelling() + chordFormula.getDisplaySuffix();
    }

    public String getChordName() {
        return chordName;
    }

    public Note getRootNote() {
        return rootNote;
    }

    public ChordFormula getChordFormula() {
        return chordFormula;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }
}
