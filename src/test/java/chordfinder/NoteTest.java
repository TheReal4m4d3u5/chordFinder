package chordfinder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoteTest {

    @Test
    public void shouldCreateValidNaturalNote() {
        Note note = Note.from("C");

        assertEquals("C", note.getSpelling());
        assertEquals(0, note.getPitchPosition());
        assertTrue(note.isValid());
    }

    @Test
    public void shouldCreateValidSharpNote() {
        Note note = Note.from("C#");

        assertEquals("C#", note.getSpelling());
        assertEquals(1, note.getPitchPosition());
        assertEquals("C#", note.getSharpName());
        assertEquals("Db", note.getFlatName());
    }

    @Test
    public void shouldCreateValidFlatNote() {
        Note note = Note.from("Eb");

        assertEquals("Eb", note.getSpelling());
        assertEquals(3, note.getPitchPosition());
        assertEquals("D#", note.getSharpName());
        assertEquals("Eb", note.getFlatName());
    }

    @Test
    public void shouldRejectInvalidNoteText() {
        assertFalse(Note.isValid("H"));
    }

    @Test
    public void shouldCalculateDistanceForward() {
        Note c = Note.from("C");
        Note e = Note.from("E");

        assertEquals(4, c.distanceTo(e));
    }

    @Test
    public void shouldCalculateDistanceAcrossOctave() {
        Note g = Note.from("G");
        Note c = Note.from("C");

        assertEquals(5, g.distanceTo(c));
    }
}
