package chordfinder;

import java.util.List;

public class ChordFormula {
    private String qualityName;
    private String displaySuffix;
    private int rootToThird;
    private int rootToFifth;

    
    public int getFirstInterval() {
        return rootToThird;
    }

    public int getSecondInterval() {
        return rootToFifth;
    }
    
    
    
    
    public ChordFormula(String qualityName, String displaySuffix, int rootToThird, int rootToFifth) {
        this.qualityName = qualityName;
        this.displaySuffix = displaySuffix;
        this.rootToThird = rootToThird;
        this.rootToFifth = rootToFifth;
    }

    public boolean matchesNotes(Note rootNote, List<Note> submittedNotes) {
        boolean hasThird = false;
        boolean hasFifth = false;

        for (Note note : submittedNotes) {
            int distance = rootNote.distanceTo(note);

            if (distance == rootToThird) {
                hasThird = true;
            }

            if (distance == rootToFifth) {
                hasFifth = true;
            }
        }

        return hasThird && hasFifth;
    }

    public void updateFormula(String qualityName, String displaySuffix, int rootToThird, int rootToFifth) {
        this.qualityName = qualityName;
        this.displaySuffix = displaySuffix;
        this.rootToThird = rootToThird;
        this.rootToFifth = rootToFifth;
    }

    public String getQualityName() {
        return qualityName;
    }

    public String getDisplaySuffix() {
        return displaySuffix;
    }

    public int getRootToThird() {
        return rootToThird;
    }

    public int getRootToFifth() {
        return rootToFifth;
    }
}
