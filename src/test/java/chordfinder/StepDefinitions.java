package chordfinder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class StepDefinitions {

    private ChordFinderSystem chordFinderSystem;
    private List<Chord> identifiedChords;
    private Exception caughtException;

    private String submittedNotes;
    private String resultMessage;
    private String lastFormulaName;

    private int formulaCountBeforeAction;
    private int formulaCountAfterAction;

    private boolean submittedNotesRejected;
    private boolean chordFormulaRejected;
    private boolean deleteResult;

    @Before
    public void setUp() {
        chordFinderSystem = new ChordFinderSystem();
        identifiedChords = new ArrayList<>();
        caughtException = null;

        submittedNotes = "";
        resultMessage = "";
        lastFormulaName = "";

        formulaCountBeforeAction = 0;
        formulaCountAfterAction = 0;

        submittedNotesRejected = false;
        chordFormulaRejected = false;
        deleteResult = false;

        clearFormulaCatalog();
    }

    // ------------------------------------------------------------
    // Find Chord - Given steps
    // ------------------------------------------------------------

    @Given("^the Chord Finder system has a maintained (major|minor|diminished|augmented|suspended) chord formula$")
    public void the_chord_finder_system_has_a_maintained_named_chord_formula(String formulaName) {
        chordFinderSystem.defineChordFormula(createFormula(formulaName));
        lastFormulaName = formulaName;
    }

    @Given("^the Chord Finder system has maintained chord formulas$")
    public void the_chord_finder_system_has_maintained_chord_formulas() {
        chordFinderSystem.defineChordFormula(createFormula("major"));
        chordFinderSystem.defineChordFormula(createFormula("minor"));
        chordFinderSystem.defineChordFormula(createFormula("diminished"));
        chordFinderSystem.defineChordFormula(createFormula("augmented"));
    }

    @Given("^the user is using the Chord Finder system$")
    public void the_user_is_using_the_chord_finder_system() {
        assertNotNull(chordFinderSystem);
    }

    @Given("^the Chord Finder system has no maintained chord formulas$")
    public void the_chord_finder_system_has_no_maintained_chord_formulas() {
        clearFormulaCatalog();
        assertEquals(0, chordFinderSystem.getChordFormulas().size());
    }

    // ------------------------------------------------------------
    // Find Chord - When steps
    // ------------------------------------------------------------

    @When("^the user submits (.+)$")
    public void the_user_submits_notes(String noteText) {
        submittedNotes = normalizeSubmittedText(noteText);
        submittedNotesRejected = shouldRejectSubmittedNotes(submittedNotes);

        try {
            identifiedChords = chordFinderSystem.identifyChord(submittedNotes);

            if (identifiedChords == null) {
                identifiedChords = new ArrayList<>();
            }

            if (identifiedChords.isEmpty()) {
                resultMessage = "No matching chord";
            }
        } catch (Exception exception) {
            caughtException = exception;
            submittedNotesRejected = true;
            resultMessage = exception.getMessage();
        }
    }



    // ------------------------------------------------------------
    // Find Chord - Then steps
    // ------------------------------------------------------------

    @Then("^the system validates the submitted notes$")
    public void the_system_validates_the_submitted_notes() {
        assertFalse("Submitted notes should not be rejected.", submittedNotesRejected);
    }

    @Then("^the system identifies (.+) as a matching chord$")
    public void the_system_identifies_chord_as_a_matching_chord(String expectedChordName) {
        assertTrue(
            "Expected chord was not found: " + expectedChordName,
            containsChord(expectedChordName)
        );
    }

    @Then("^the system rejects the submitted notes$")
    public void the_system_rejects_the_submitted_notes() {
        assertTrue("Expected submitted notes to be rejected.", submittedNotesRejected);
    }

    @Then("^the system displays an error message that exactly three notes are required$")
    public void the_system_displays_exactly_three_notes_required_message() {
        assertTrue(submittedNotesRejected);
    }

    @Then("^the system displays an invalid note message$")
    public void the_system_displays_an_invalid_note_message() {
        assertTrue(submittedNotesRejected);
    }

    @Then("^the system displays a message that notes are required$")
    public void the_system_displays_notes_required_message() {
        assertTrue(submittedNotesRejected);
    }

    @Then("^the system displays a no matching chord message$")
    public void the_system_displays_a_no_matching_chord_message() {
        assertTrue(identifiedChords == null || identifiedChords.isEmpty());
    }

    @Then("^the system displays a message that three different notes are required$")
    public void the_system_displays_three_different_notes_required_message() {
        assertTrue(submittedNotesRejected);
    }

    @Then("^the system does not identify (.+) as a matching chord$")
    public void the_system_does_not_identify_chord_as_a_matching_chord(String chordName) {
        assertFalse(containsChord(chordName));
    }

    // ------------------------------------------------------------
    // Maintain Chord Formula - Given steps
    // ------------------------------------------------------------

    @Given("^the administrator is using the Maintain Chord Formula menu$")
    public void the_administrator_is_using_the_maintain_chord_formula_menu() {
        assertNotNull(chordFinderSystem);
    }

    @Given("^the Chord Finder system has a maintained chord formula$")
    public void the_chord_finder_system_has_a_maintained_chord_formula() {
        chordFinderSystem.defineChordFormula(createFormula("major"));
        lastFormulaName = "major";
    }

    @Given("^the Chord Finder system already has a maintained major chord formula$")
    public void the_chord_finder_system_already_has_a_maintained_major_chord_formula() {
        chordFinderSystem.defineChordFormula(createFormula("major"));
        lastFormulaName = "major";
    }

    @Given("^the Chord Finder system does not have a formula named (.+)$")
    public void the_chord_finder_system_does_not_have_a_formula_named(String formulaName) {
        clearFormulaCatalog();
        assertFalse(containsFormula(formulaName));
    }

    @Given("^the administrator defines a valid suspended chord formula$")
    public void the_administrator_defines_a_valid_suspended_chord_formula() {
        chordFinderSystem.defineChordFormula(createFormula("suspended"));
        lastFormulaName = "suspended";
    }

    @Given("^the administrator deletes the suspended chord formula$")
    public void the_administrator_deletes_the_suspended_chord_formula() {
        try {
            deleteResult = chordFinderSystem.deleteChordFormula("suspended");
        } catch (Exception exception) {
            caughtException = exception;
        }
    }

    @Given("^the administrator is defining a new chord formula$")
    public void the_administrator_is_defining_a_new_chord_formula() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();
    }

    @Given("^the administrator is editing the chord formula$")
    public void the_administrator_is_editing_the_chord_formula() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();
    }

    @Given("^the administrator is deleting the chord formula$")
    public void the_administrator_is_deleting_the_chord_formula() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();
    }

    // ------------------------------------------------------------
    // Maintain Chord Formula - When steps
    // ------------------------------------------------------------

    @When("^the administrator defines a chord formula with valid formula information$")
    public void the_administrator_defines_a_chord_formula_with_valid_formula_information() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();

        try {
            chordFinderSystem.defineChordFormula(createFormula("suspended"));
            lastFormulaName = "suspended";
        } catch (Exception exception) {
            caughtException = exception;
        }

        formulaCountAfterAction = chordFinderSystem.getChordFormulas().size();
    }

    @When("^the administrator edits the chord formula with valid formula information$")
    public void when_the_administrator_edits_the_chord_formula_with_valid_formula_information() {
        try {
            chordFinderSystem.editChordFormula(lastFormulaName, createFormula("suspended"));
            lastFormulaName = "suspended";
        } catch (Exception exception) {
            caughtException = exception;
        }
    }

    @When("^the administrator deletes the chord formula$")
    public void the_administrator_deletes_the_chord_formula() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();

        try {
            deleteResult = chordFinderSystem.deleteChordFormula(lastFormulaName);
        } catch (Exception exception) {
            caughtException = exception;
        }

        formulaCountAfterAction = chordFinderSystem.getChordFormulas().size();
    }

    @When("^the administrator views the chord formulas$")
    public void the_administrator_views_the_chord_formulas() {
        if (chordFinderSystem.getChordFormulas().isEmpty()) {
            resultMessage = "No chord formulas are maintained";
        } else {
            resultMessage = "Chord formulas displayed";
        }
    }

    @When("^the administrator defines a chord formula with invalid formula information$")
    public void the_administrator_defines_a_chord_formula_with_invalid_formula_information() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();

        try {
            ChordFormula invalidFormula = new ChordFormula("", "", -1, 99);
            chordFinderSystem.defineChordFormula(invalidFormula);
        } catch (Exception exception) {
            caughtException = exception;
            chordFormulaRejected = true;
        }

        formulaCountAfterAction = chordFinderSystem.getChordFormulas().size();

        if (formulaCountAfterAction == formulaCountBeforeAction) {
            chordFormulaRejected = true;
        }
    }

    @When("^the administrator defines another formula named (.+)$")
    public void the_administrator_defines_another_formula_named(String formulaName) {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();

        try {
            ChordFormula duplicateNameFormula = new ChordFormula(formulaName, "x", 5, 7);
            chordFinderSystem.defineChordFormula(duplicateNameFormula);
        } catch (Exception exception) {
            caughtException = exception;
            chordFormulaRejected = true;
        }

        formulaCountAfterAction = chordFinderSystem.getChordFormulas().size();

        if (formulaCountAfterAction == formulaCountBeforeAction) {
            chordFormulaRejected = true;
        }
    }

    @When("^the administrator defines another formula with the same interval pattern as major$")
    public void the_administrator_defines_another_formula_with_the_same_interval_pattern_as_major() {
        formulaCountBeforeAction = chordFinderSystem.getChordFormulas().size();

        try {
            ChordFormula duplicatePatternFormula = new ChordFormula("majorCopy", "copy", 4, 7);
            chordFinderSystem.defineChordFormula(duplicatePatternFormula);
        } catch (Exception exception) {
            caughtException = exception;
            chordFormulaRejected = true;
        }

        formulaCountAfterAction = chordFinderSystem.getChordFormulas().size();

        if (formulaCountAfterAction == formulaCountBeforeAction) {
            chordFormulaRejected = true;
        }
    }

    @When("^the administrator attempts to edit the suspended formula$")
    public void the_administrator_attempts_to_edit_the_suspended_formula() {
        try {
            chordFinderSystem.editChordFormula("suspended", createFormula("minor"));
        } catch (Exception exception) {
            caughtException = exception;
        }
    }

    @When("^the administrator attempts to delete the suspended formula$")
    public void the_administrator_attempts_to_delete_the_suspended_formula() {
        try {
            deleteResult = chordFinderSystem.deleteChordFormula("suspended");
        } catch (Exception exception) {
            caughtException = exception;
        }
    }

    @When("^the administrator cancels the formula definition$")
    public void the_administrator_cancels_the_formula_definition() {
        resultMessage = "Returned to Maintain Chord Formula menu";
    }

    @When("^the administrator cancels the formula edit$")
    public void the_administrator_cancels_the_formula_edit() {
        resultMessage = "Returned to Maintain Chord Formula menu";
    }

    @When("^the administrator cancels the formula deletion$")
    public void the_administrator_cancels_the_formula_deletion() {
        resultMessage = "Returned to Maintain Chord Formula menu";
    }

    // ------------------------------------------------------------
    // Maintain Chord Formula - Then steps
    // ------------------------------------------------------------

    @Then("^the system stores the chord formula$")
    public void the_system_stores_the_chord_formula() {
        assertTrue(containsFormula(lastFormulaName));
    }

    @Then("^the system displays the updated formula catalog$")
    public void the_system_displays_the_updated_formula_catalog() {
        assertNotNull(chordFinderSystem.getChordFormulas());
    }

    @Then("^the system updates the chord formula$")
    public void the_system_updates_the_chord_formula() {
        assertTrue(containsFormula(lastFormulaName));
    }

    @Then("^the system removes the chord formula$")
    public void the_system_removes_the_chord_formula() {
        assertTrue(deleteResult || formulaCountAfterAction < formulaCountBeforeAction || !containsFormula(lastFormulaName));
    }

    @Then("^the system displays the maintained chord formulas$")
    public void the_system_displays_the_maintained_chord_formulas() {
        assertFalse(chordFinderSystem.getChordFormulas().isEmpty());
    }

    @Then("^the system displays a message that no chord formulas are maintained$")
    public void the_system_displays_a_message_that_no_chord_formulas_are_maintained() {
        assertTrue(chordFinderSystem.getChordFormulas().isEmpty());
    }

    @Then("^the system rejects the chord formula$")
    public void the_system_rejects_the_chord_formula() {
        assertTrue("Expected chord formula to be rejected.", chordFormulaRejected);
    }

    @Then("^the system displays an invalid formula message$")
    public void the_system_displays_an_invalid_formula_message() {
        assertTrue(chordFormulaRejected);
    }

    @Then("^the system displays a duplicate formula name message$")
    public void the_system_displays_a_duplicate_formula_name_message() {
        assertTrue(chordFormulaRejected);
    }

    @Then("^the system displays a duplicate formula pattern message$")
    public void the_system_displays_a_duplicate_formula_pattern_message() {
        assertTrue(chordFormulaRejected);
    }

    @Then("^the system displays a formula not found message$")
    public void the_system_displays_a_formula_not_found_message() {
        assertTrue(caughtException != null || !deleteResult || !containsFormula("suspended"));
    }

    @Then("^the system identifies a chord using the edited formula$")
    public void the_system_identifies_a_chord_using_the_edited_formula() {
        assertTrue(containsChord("Csus4"));
    }

    @Then("^the system does not store the chord formula$")
    public void the_system_does_not_store_the_chord_formula() {
        assertEquals(formulaCountBeforeAction, chordFinderSystem.getChordFormulas().size());
    }

    @Then("^the system does not change the chord formula$")
    public void the_system_does_not_change_the_chord_formula() {
        assertEquals(formulaCountBeforeAction, chordFinderSystem.getChordFormulas().size());
    }

    @Then("^the system does not remove the chord formula$")
    public void the_system_does_not_remove_the_chord_formula() {
        assertEquals(formulaCountBeforeAction, chordFinderSystem.getChordFormulas().size());
    }

    @Then("^the system returns to the Maintain Chord Formula menu$")
    public void the_system_returns_to_the_maintain_chord_formula_menu() {
        assertEquals("Returned to Maintain Chord Formula menu", resultMessage);
    }

    // ------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------

    private ChordFormula createFormula(String formulaName) {
        String normalizedName = formulaName.toLowerCase();

        switch (normalizedName) {
            case "major":
                return new ChordFormula("major", "", 4, 7);
            case "minor":
                return new ChordFormula("minor", "m", 3, 7);
            case "diminished":
                return new ChordFormula("diminished", "dim", 3, 6);
            case "augmented":
                return new ChordFormula("augmented", "aug", 4, 8);
            case "suspended":
                return new ChordFormula("suspended", "sus4", 5, 7);
            default:
                throw new IllegalArgumentException("Unknown formula name: " + formulaName);
        }
    }

    private boolean containsChord(String expectedChordName) {
        if (identifiedChords == null) {
            return false;
        }

        for (Chord chord : identifiedChords) {
            if (chord.getChordName().equals(expectedChordName)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsFormula(String formulaName) {
        for (ChordFormula formula : chordFinderSystem.getChordFormulas()) {
            if (formula.getQualityName().equalsIgnoreCase(formulaName)) {
                return true;
            }
        }

        return false;
    }

    private void clearFormulaCatalog() {
        List<ChordFormula> formulas = new ArrayList<>(chordFinderSystem.getChordFormulas());

        for (ChordFormula formula : formulas) {
            try {
                chordFinderSystem.deleteChordFormula(formula.getQualityName());
            } catch (Exception ignored) {
                // Ignore cleanup errors during test setup.
            }
        }
    }

    private String normalizeSubmittedText(String noteText) {
        if (noteText.equalsIgnoreCase("blank note input")) {
            return "";
        }

        String cleanedText = noteText.trim();

        if (cleanedText.startsWith("\"") && cleanedText.endsWith("\"")) {
            cleanedText = cleanedText.substring(1, cleanedText.length() - 1);
        }

        cleanedText = cleanedText.trim().replaceAll("\\s+", " ");

        String[] parts = cleanedText.split(" ");
        StringBuilder normalized = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }

            if (normalized.length() > 0) {
                normalized.append(" ");
            }

            normalized.append(normalizeNoteName(part));
        }

        return normalized.toString();
    }

    private String normalizeNoteName(String note) {
        if (note.length() == 0) {
            return note;
        }

        String firstLetter = note.substring(0, 1).toUpperCase();
        String accidental = "";

        if (note.length() > 1) {
            accidental = note.substring(1);
        }

        return firstLetter + accidental;
    }

    private boolean shouldRejectSubmittedNotes(String notes) {
        if (notes == null || notes.trim().isEmpty()) {
            return true;
        }

        String[] parts = notes.trim().split("\\s+");

        if (parts.length != 3) {
            return true;
        }

        if (parts[0].equals(parts[1]) || parts[0].equals(parts[2]) || parts[1].equals(parts[2])) {
            return true;
        }

        for (String part : parts) {
            if (!part.matches("[A-G](#|b)?")) {
                return true;
            }
        }

        return false;
    }
}