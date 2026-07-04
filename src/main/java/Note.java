import java.util.*;

public class Note {
    private static final String[] SHARP_NAMES = {
            "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"
    };

    private static final String[] FLAT_NAMES = {
            "A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"
    };

    private static final Map<Integer, List<String>> OTHER_NAMES = new HashMap<>();
    private static final Map<String, Integer> PITCH_POSITIONS = new HashMap<>();

    static {
        OTHER_NAMES.put(0, List.of());
        OTHER_NAMES.put(1, List.of());
        OTHER_NAMES.put(2, List.of("Cb"));
        OTHER_NAMES.put(3, List.of("B#"));
        OTHER_NAMES.put(4, List.of());
        OTHER_NAMES.put(5, List.of());
        OTHER_NAMES.put(6, List.of());
        OTHER_NAMES.put(7, List.of("Fb"));
        OTHER_NAMES.put(8, List.of("E#"));
        OTHER_NAMES.put(9, List.of());
        OTHER_NAMES.put(10, List.of());
        OTHER_NAMES.put(11, List.of());

        for (int i = 0; i < 12; i++) {
            PITCH_POSITIONS.put(SHARP_NAMES[i], i);
            PITCH_POSITIONS.put(FLAT_NAMES[i], i);

            for (String otherName : OTHER_NAMES.get(i)) {
                PITCH_POSITIONS.put(otherName, i);
            }
        }
    }

    private final String spelling;
    private final int pitchPosition;
    private final String sharpName;
    private final String flatName;
    private final List<String> otherNames;

    private Note(String spelling, int pitchPosition) {
        this.spelling = spelling;
        this.pitchPosition = pitchPosition;
        this.sharpName = SHARP_NAMES[pitchPosition];
        this.flatName = FLAT_NAMES[pitchPosition];
        this.otherNames = OTHER_NAMES.get(pitchPosition);
    }

    public static Note from(String noteText) {
        String normalized = normalize(noteText);

        if (!PITCH_POSITIONS.containsKey(normalized)) {
            throw new IllegalArgumentException("Invalid note spelling: " + noteText);
        }

        return new Note(normalized, PITCH_POSITIONS.get(normalized));
    }

    public static boolean isValid(String noteText) {
        try {
            from(noteText);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private static String normalize(String noteText) {
        if (noteText == null || noteText.isBlank()) {
            throw new IllegalArgumentException("Missing note.");
        }

        String trimmed = noteText.trim();

        if (trimmed.length() == 1) {
            return trimmed.substring(0, 1).toUpperCase();
        }

        if (trimmed.length() == 2) {
            String letter = trimmed.substring(0, 1).toUpperCase();
            String accidental = trimmed.substring(1, 2);

            if (accidental.equals("#")) {
                return letter + "#";
            }

            if (accidental.equalsIgnoreCase("b")) {
                return letter + "b";
            }
        }

        throw new IllegalArgumentException("Invalid note spelling: " + noteText);
    }

    public int distanceTo(Note otherNote) {
        return (otherNote.pitchPosition - this.pitchPosition + 12) % 12;
    }

    public boolean isValid() {
        return PITCH_POSITIONS.containsKey(spelling);
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
        return otherNames;
    }

    @Override
    public String toString() {
        return spelling;
    }
}