package chordfinder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChordFinderSystemTest {

    @Test
    public void shouldIdentifyGMajorFromNotesInAnyOrder() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("D G B");

        assertEquals(1, chords.size());
        assertEquals("G maj", chords.get(0).getChordName());
    }

    @Test
    public void shouldIdentifyCMinor() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C Eb G");

        assertEquals(1, chords.size());
        assertEquals("C min", chords.get(0).getChordName());
    }

    @Test
    public void shouldIdentifyMultipleAugmentedChords() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("B D# G");

        assertEquals(3, chords.size());

        assertTrue(chords.stream().anyMatch(chord -> chord.getChordName().equals("B aug")));
        assertTrue(chords.stream().anyMatch(chord -> chord.getChordName().equals("D# aug")));
        assertTrue(chords.stream().anyMatch(chord -> chord.getChordName().equals("G aug")));
    }

    @Test
    public void shouldReturnEmptyListWhenNoChordMatches() {
        ChordFinderSystem system = new ChordFinderSystem();

        List<Chord> chords = system.identifyChord("C D E");

        assertTrue(chords.isEmpty());
    }

    @Test
    public void shouldRejectFewerThanThreeNotes() {
        ChordFinderSystem system = new ChordFinderSystem();

        assertThrows(IllegalArgumentException.class, () -> system.identifyChord("C G"));
    }

    @Test
    public void shouldRejectMoreThanThreeNotes() {
        ChordFinderSystem system = new ChordFinderSystem();

        assertThrows(IllegalArgumentException.class, () -> system.identifyChord("C E G B"));
    }

    @Test
    public void shouldRejectInvalidNoteSpelling() {
        ChordFinderSystem system = new ChordFinderSystem();

        assertThrows(IllegalArgumentException.class, () -> system.identifyChord("C H G"));
    }

    @Test
    public void shouldUseNewFormulaAfterAdministratorDefinesFormula() {
        ChordFinderSystem system = new ChordFinderSystem();

        system.addFormula(new ChordFormula("Suspended Fourth", "sus4", 5, 7));

        List<Chord> chords = system.identifyChord("C F G");

        assertEquals(1, chords.size());
        assertEquals("C sus4", chords.get(0).getChordName());
    }

    @Test
    public void shouldNotUseFormulaAfterAdministratorDeletesFormula() {
        ChordFinderSystem system = new ChordFinderSystem();

        boolean deleted = system.deleteFormula("Minor");

        assertTrue(deleted);

        List<Chord> chords = system.identifyChord("C Eb G");

        assertTrue(chords.isEmpty());
    }
}