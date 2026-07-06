package chordfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Note {
    private String spelling;
    private int pitchPosition;
    private String sharpName;
    private String flatName;
    private List<String> otherNames;

    private Note(String spelling, int pitchPosition, String sharpName, String flatName, List<String> otherNames) {
        this.spelling = spelling;
        this.pitchPosition = pitchPosition;
        this.sharpName = sharpName;
        this.flatName = flatName;
        this.otherNames = new ArrayList<>(otherNames);
    }

    public static Note from(String noteText) {
        if (noteText == null) {
            return null;
        }

        String normalized = noteText.trim();

        switch (normalized) {
            case "C":
                return new Note("C", 0, "C", "C", new ArrayList<>());
            case "C#":
            case "Db":
                return new Note(normalized, 1, "C#", "Db", Arrays.asList("C#", "Db"));
            case "D":
                return new Note("D", 2, "D", "D", new ArrayList<>());
            case "D#":
            case "Eb":
                return new Note(normalized, 3, "D#", "Eb", Arrays.asList("D#", "Eb"));
            case "E":
                return new Note("E", 4, "E", "E", new ArrayList<>());
            case "F":
                return new Note("F", 5, "F", "F", new ArrayList<>());
            case "F#":
            case "Gb":
                return new Note(normalized, 6, "F#", "Gb", Arrays.asList("F#", "Gb"));
            case "G":
                return new Note("G", 7, "G", "G", new ArrayList<>());
            case "G#":
            case "Ab":
                return new Note(normalized, 8, "G#", "Ab", Arrays.asList("G#", "Ab"));
            case "A":
                return new Note("A", 9, "A", "A", new ArrayList<>());
            case "A#":
            case "Bb":
                return new Note(normalized, 10, "A#", "Bb", Arrays.asList("A#", "Bb"));
            case "B":
                return new Note("B", 11, "B", "B", new ArrayList<>());
            default:
                return null;
        }
    }

    public static boolean isValid(String noteText) {
        return from(noteText) != null;
    }

    public boolean isValid() {
        return pitchPosition >= 0 && pitchPosition <= 11;
    }

    public int distanceTo(Note otherNote) {
        int distance = otherNote.pitchPosition - this.pitchPosition;

        if (distance < 0) {
            distance += 12;
        }

        return distance;
    }

    public String getSpelling() {
        return spelling;
    }

    public int getPitchPosition() {
        return pitchPosition;
    }

    public String getSharpName() {
        return sharpName;
    }

    public String getFlatName() {
        return flatName;
    }

    public List<String> getOtherNames() {
        return new ArrayList<>(otherNames);
    }
}
