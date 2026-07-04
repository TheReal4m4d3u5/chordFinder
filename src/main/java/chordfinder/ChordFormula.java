package chordfinder;
import java.util.*;

public class ChordFormula {
    private String qualityName;
    private String displaySuffix;
    private int rootToThird;
    private int rootToFifth;

    public ChordFormula(String qualityName, String displaySuffix, int rootToThird, int rootToFifth) {
        this.qualityName = qualityName;
        this.displaySuffix = displaySuffix;
        this.rootToThird = rootToThird;
        this.rootToFifth = rootToFifth;
    }

    public boolean matches(Note rootNote, List<Note> submittedNotes) {
        Set<Integer> submittedPositions = new HashSet<>();

        for (Note note : submittedNotes) {
            submittedPositions.add(note.getPitchPosition());
        }

        Set<Integer> expectedPositions = new HashSet<>();
        expectedPositions.add(rootNote.getPitchPosition());
        expectedPositions.add((rootNote.getPitchPosition() + rootToThird) % 12);
        expectedPositions.add((rootNote.getPitchPosition() + rootToFifth) % 12);

        return submittedPositions.equals(expectedPositions);
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public String getDisplaySuffix() {
        return displaySuffix;
    }

    public void setDisplaySuffix(String displaySuffix) {
        this.displaySuffix = displaySuffix;
    }

    public int getRootToThird() {
        return rootToThird;
    }

    public void setRootToThird(int rootToThird) {
        this.rootToThird = rootToThird;
    }

    public int getRootToFifth() {
        return rootToFifth;
    }

    public void setRootToFifth(int rootToFifth) {
        this.rootToFifth = rootToFifth;
    }

    @Override
    public String toString() {
        return qualityName + " (" + displaySuffix + ")";
    }
}