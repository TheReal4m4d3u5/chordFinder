package chordfinder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChordFormulaTest {

    @Test
    public void majorFormulaShouldMatchMajorTriad() {
        ChordFormula major = new ChordFormula("Major", "maj", 4, 7);

        List<Note> notes = List.of(
            Note.from("C"),
            Note.from("E"),
            Note.from("G")
        );

        assertTrue(major.matchesNotes(Note.from("C"), notes));
    }

    @Test
    public void minorFormulaShouldMatchMinorTriad() {
        ChordFormula minor = new ChordFormula("Minor", "min", 3, 7);

        List<Note> notes = List.of(
            Note.from("C"),
            Note.from("Eb"),
            Note.from("G")
        );

        assertTrue(minor.matchesNotes(Note.from("C"), notes));
    }

    @Test
    public void majorFormulaShouldNotMatchMinorTriad() {
        ChordFormula major = new ChordFormula("Major", "maj", 4, 7);

        List<Note> notes = List.of(
            Note.from("C"),
            Note.from("Eb"),
            Note.from("G")
        );

        assertFalse(major.matchesNotes(Note.from("C"), notes));
    }

    @Test
    public void updateFormulaShouldReviseFormulaValues() {
        ChordFormula formula = new ChordFormula("Major", "", 4, 7);

        formula.updateFormula("Suspended Fourth", "sus4", 5, 7);

        assertTrue(formula.getQualityName().equals("Suspended Fourth"));
        assertTrue(formula.getDisplaySuffix().equals("sus4"));
        assertTrue(formula.getRootToThird() == 5);
        assertTrue(formula.getRootToFifth() == 7);
    }
}
