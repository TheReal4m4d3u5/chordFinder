package chordfinder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChordFormulaTest {

    @Test
    public void majorFormulaShouldMatchMajorTriad() {
        ChordFormula major = new ChordFormula("Major", "maj", 4, 7);

        List<Note> notes = List.of(
                Note.from("C"),
                Note.from("E"),
                Note.from("G")
        );

        assertTrue(major.matches(Note.from("C"), notes));
    }

    @Test
    public void minorFormulaShouldMatchMinorTriad() {
        ChordFormula minor = new ChordFormula("Minor", "min", 3, 7);

        List<Note> notes = List.of(
                Note.from("C"),
                Note.from("Eb"),
                Note.from("G")
        );

        assertTrue(minor.matches(Note.from("C"), notes));
    }

    @Test
    public void majorFormulaShouldNotMatchMinorTriad() {
        ChordFormula major = new ChordFormula("Major", "maj", 4, 7);

        List<Note> notes = List.of(
                Note.from("C"),
                Note.from("Eb"),
                Note.from("G")
        );

        assertFalse(major.matches(Note.from("C"), notes));
    }
}