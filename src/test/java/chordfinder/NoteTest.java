package chordfinder;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteTest {

    @Test
    public void shouldCreateValidNote() {
        Note note = Note.from("C");

        assertEquals("C", note.getSpelling());
        assertEquals(3, note.getPitchPosition());
    }

    @Test
    public void shouldTreatLowercaseInputAsValid() {
        Note note = Note.from("eb");

        assertEquals("Eb", note.getSpelling());
        assertEquals(6, note.getPitchPosition());
    }

    @Test
    public void shouldMapEnharmonicNotesToSamePitchPosition() {
        Note sharpNote = Note.from("A#");
        Note flatNote = Note.from("Bb");

        assertEquals(sharpNote.getPitchPosition(), flatNote.getPitchPosition());
    }

    @Test
    public void shouldRejectInvalidNoteSpelling() {
        assertThrows(IllegalArgumentException.class, () -> Note.from("H"));
    }

    @Test
    public void shouldCalculateDistanceBetweenNotes() {
        Note c = Note.from("C");
        Note e = Note.from("E");

        assertEquals(4, c.distanceTo(e));
    }
}