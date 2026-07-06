package chordfinder;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ChordFormulaCatalogTest {

    @Test
    public void shouldStartWithDefaultFormulas() {
        ChordFormulaCatalog catalog = new ChordFormulaCatalog();

        List<ChordFormula> formulas = catalog.getChordFormulas();

        assertTrue(formulas.size() >= 4);
        assertNotNull(catalog.findFormula("Major"));
        assertNotNull(catalog.findFormula("Minor"));
        assertNotNull(catalog.findFormula("Diminished"));
        assertNotNull(catalog.findFormula("Augmented"));
    }

    @Test
    public void shouldDefineNewFormula() {
        ChordFormulaCatalog catalog = new ChordFormulaCatalog();

        catalog.defineChordFormula(new ChordFormula("Suspended Fourth", "sus4", 5, 7));

        ChordFormula formula = catalog.findFormula("Suspended Fourth");

        assertNotNull(formula);
        assertEquals("sus4", formula.getDisplaySuffix());
        assertEquals(5, formula.getRootToThird());
        assertEquals(7, formula.getRootToFifth());
    }

    @Test
    public void shouldEditExistingFormula() {
        ChordFormulaCatalog catalog = new ChordFormulaCatalog();

        boolean updated = catalog.editChordFormula(
            "Minor",
            new ChordFormula("Minor", "min", 3, 7)
        );

        ChordFormula formula = catalog.findFormula("Minor");

        assertTrue(updated);
        assertEquals("min", formula.getDisplaySuffix());
    }

    @Test
    public void shouldDeleteExistingFormula() {
        ChordFormulaCatalog catalog = new ChordFormulaCatalog();

        boolean deleted = catalog.deleteChordFormula("Minor");

        assertTrue(deleted);
        assertNull(catalog.findFormula("Minor"));
    }
}
