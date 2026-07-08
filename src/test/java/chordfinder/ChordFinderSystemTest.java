package chordfinder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChordFinderSystemTest {

	
	
	
    @Test
    public void shouldIdentifyMajorChord() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C E G");

        assertEquals(1, chords.size());
        assertEquals("C", chords.get(0).getChordName());
    }

    @Test
    public void shouldIdentifyMinorChord() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C Eb G");

        assertEquals(1, chords.size());
        assertEquals("Cm", chords.get(0).getChordName());
    }

    @Test
    public void shouldReturnEmptyListForNoMatchingChord() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C D G");

        assertTrue(chords.isEmpty());
    }

    @Test
    public void shouldUseNewFormulaAfterAdministratorDefinesFormula() {
        ChordFinderSystem system = new ChordFinderSystem();

        system.defineChordFormula(new ChordFormula("Suspended Fourth", "sus4", 5, 7));

        List<Chord> chords = system.identifyChord("C F G");

        assertEquals(1, chords.size());
        assertEquals("Csus4", chords.get(0).getChordName());
    }

    @Test
    public void shouldNotUseFormulaAfterAdministratorDeletesFormula() {
        ChordFinderSystem system = new ChordFinderSystem();

        boolean deleted = system.deleteChordFormula("Minor");

        assertTrue(deleted);

        List<Chord> chords = system.identifyChord("C Eb G");

        assertTrue(chords.isEmpty());
    }

    @Test
    public void shouldRejectFewerThanThreeNotes() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C G");

        assertTrue(chords.isEmpty());
    }

    @Test
    public void shouldRejectMoreThanThreeNotes() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C E G B");

        assertTrue(chords.isEmpty());
    }

    @Test
    public void shouldRejectInvalidNoteSpelling() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C H G");

        assertTrue(chords.isEmpty());
    }
}
