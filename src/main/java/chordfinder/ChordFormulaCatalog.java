package chordfinder;

import java.util.ArrayList;
import java.util.List;

public class ChordFormulaCatalog {
    private List<ChordFormula> chordFormulas;

    public ChordFormulaCatalog() {
        this.chordFormulas = new ArrayList<>();

        defineChordFormula(new ChordFormula("Major", "", 4, 7));
        defineChordFormula(new ChordFormula("Minor", "m", 3, 7));
        defineChordFormula(new ChordFormula("Diminished", "dim", 3, 6));
        defineChordFormula(new ChordFormula("Augmented", "aug", 4, 8));
    }

    public void defineChordFormula(ChordFormula formula) {
        if (formula != null) {
            chordFormulas.add(formula);
        }
    }

    public boolean editChordFormula(String qualityName, ChordFormula revisedFormula) {
        ChordFormula existingFormula = findFormula(qualityName);

        if (existingFormula == null || revisedFormula == null) {
            return false;
        }

        existingFormula.updateFormula(
            revisedFormula.getQualityName(),
            revisedFormula.getDisplaySuffix(),
            revisedFormula.getRootToThird(),
            revisedFormula.getRootToFifth()
        );

        return true;
    }

    public boolean deleteChordFormula(String qualityName) {
        ChordFormula formula = findFormula(qualityName);

        if (formula == null) {
            return false;
        }

        return chordFormulas.remove(formula);
    }

    public List<ChordFormula> getChordFormulas() {
        return new ArrayList<>(chordFormulas);
    }

    public ChordFormula findFormula(String qualityName) {
        if (qualityName == null) {
            return null;
        }

        for (ChordFormula formula : chordFormulas) {
            if (formula.getQualityName().equalsIgnoreCase(qualityName)) {
                return formula;
            }
        }

        return null;
    }
}
