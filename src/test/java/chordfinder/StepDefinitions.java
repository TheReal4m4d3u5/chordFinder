package chordfinder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    private static class Formula {
        private final String name;
        private final String suffix;
        private final int[] intervals;

        Formula(String name, String suffix, int[] intervals) {
            this.name = name;
            this.suffix = suffix;
            this.intervals = intervals;
        }

        Formula copy() {
            return new Formula(name, suffix, intervals.clone());
        }
    }

    private final Map<String, Formula> formulas = new LinkedHashMap<>();

    private List<String> results;
    private String errorMessage;
    private String resultMessage;
    private boolean submittedNotesWereValid;

    private List<String> displayedFormulaNames;
    private List<String> catalogSnapshot;
    private boolean catalogUpdated;
    private String formulaMessage;
    private String updatedExpectedChord;

    public StepDefinitions() {
        loadDefaultFormulas();
    }

    // ------------------------------------------------------------
    // GIVEN STEPS
    // ------------------------------------------------------------

    @Given("the Chord Finder system has a maintained major chord formula")
    public void the_chord_finder_system_has_a_maintained_major_chord_formula() {
        loadDefaultFormulas();
        assertTrue(formulas.containsKey("maj"));
    }

    @Given("the Chord Finder system has a maintained minor chord formula")
    public void the_chord_finder_system_has_a_maintained_minor_chord_formula() {
        loadDefaultFormulas();
        assertTrue(formulas.containsKey("min"));
    }

    @Given("the Chord Finder system has a maintained augmented chord formula")
    public void the_chord_finder_system_has_a_maintained_augmented_chord_formula() {
        loadDefaultFormulas();
        assertTrue(formulas.containsKey("aug"));
    }

    @Given("the Chord Finder system has maintained chord formulas")
    public void the_chord_finder_system_has_maintained_chord_formulas() {
        loadDefaultFormulas();
        assertFalse(formulas.isEmpty());
    }

    @Given("the user is using the Chord Finder system")
    public void the_user_is_using_the_chord_finder_system() {
        loadDefaultFormulas();
    }

    @Given("the administrator is maintaining chord formulas")
    public void the_administrator_is_maintaining_chord_formulas() {
        loadDefaultFormulas();
    }

    @Given("the administrator has added a suspended fourth formula")
    public void the_administrator_has_added_a_suspended_fourth_formula() {
        loadDefaultFormulas();
        addFormula("sus4", "sus4", "0 5 7");
    }

    @Given("the administrator has deleted the minor chord formula")
    public void the_administrator_has_deleted_the_minor_chord_formula() {
        loadDefaultFormulas();
        formulas.remove("min");
    }

    @Given("the administrator has edited an existing chord formula")
    public void the_administrator_has_edited_an_existing_chord_formula() {
        loadDefaultFormulas();

        // Change major formula from 0 4 7 to 0 5 7.
        // This lets C F G match the updated formula.
        addFormula("maj", "", "0 5 7");
        updatedExpectedChord = "C";
    }

    @Given("the ChordFormulaCatalog contains an existing chord formula")
    public void the_chord_formula_catalog_contains_an_existing_chord_formula() {
        loadDefaultFormulas();
        assertTrue(formulas.containsKey("maj"));
    }

    @Given("the ChordFormulaCatalog contains a minor chord formula")
    public void the_chord_formula_catalog_contains_a_minor_chord_formula() {
        loadDefaultFormulas();
        assertTrue(formulas.containsKey("min"));
    }

    @Given("the available chord formulas include {string}")
    public void the_available_chord_formulas_include(String formulaName) {
        assertTrue(
            "Formula should exist: " + formulaName,
            formulas.containsKey(formulaName)
        );
    }

    @Given("the available chord formulas include {string} with intervals {string}")
    public void the_available_chord_formulas_include_with_intervals(
            String formulaName,
            String intervals
    ) {
        addFormula(formulaName, suffixFor(formulaName), intervals);
    }

    @Given("the available chord formulas include:")
    public void the_available_chord_formulas_include_table(DataTable table) {
        formulas.clear();

        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String intervals = row.get("intervals");
            String suffix = row.get("suffix");

            if (suffix == null) {
                suffix = suffixFor(name);
            }

            addFormula(name, suffix, intervals);
        }
    }

    @Given("an available chord formula named {string} with intervals {string}")
    public void an_available_chord_formula_named_with_intervals(
            String formulaName,
            String intervals
    ) {
        addFormula(formulaName, suffixFor(formulaName), intervals);
    }

    @Given("an administrator defines a chord formula named {string} with intervals {string}")
    public void an_administrator_defines_a_chord_formula_named_with_intervals(
            String formulaName,
            String intervals
    ) {
        addFormula(formulaName, suffixFor(formulaName), intervals);
    }

    @Given("an administrator defines a new chord formula named {string} with intervals {string}")
    public void an_administrator_defines_a_new_chord_formula_named_with_intervals(
            String formulaName,
            String intervals
    ) {
        addFormula(formulaName, suffixFor(formulaName), intervals);
    }

    // ------------------------------------------------------------
    // WHEN STEPS
    // ------------------------------------------------------------

    @When("^the user submits (?!blank note input$)(?!notes that match the updated formula$)(.+)$")
    public void the_user_submits_notes(String notesText) {
        submitNotes(notesText);
    }

    @When("the user submits blank note input")
    public void the_user_submits_blank_note_input() {
        submitNotes("");
    }

    @When("the user submits notes that match the updated formula")
    public void the_user_submits_notes_that_match_the_updated_formula() {
        submitNotes("C F G");
    }

    @When("a user submits the notes {string}")
    public void a_user_submits_the_notes(String notesText) {
        submitNotes(notesText);
    }

    @When("an administrator defines a suspended fourth formula")
    public void an_administrator_defines_a_suspended_fourth_formula() {
        addFormula("sus4", "sus4", "0 5 7");
        catalogUpdated = true;
    }

    @When("the administrator edits the formula values")
    public void the_administrator_edits_the_formula_values() {
        catalogSnapshot = catalogSignature();
        addFormula("maj", "", "0 5 7");
        catalogUpdated = true;
    }

    @When("the administrator deletes the minor chord formula")
    public void the_administrator_deletes_the_minor_chord_formula() {
        catalogSnapshot = catalogSignature();
        formulas.remove("min");
        catalogUpdated = true;
    }

    @When("the administrator selects view chord formulas")
    public void the_administrator_selects_view_chord_formulas() {
        viewChordFormulas();
    }

    @When("the administrator views maintained chord formulas")
    public void the_administrator_views_maintained_chord_formulas() {
        viewChordFormulas();
    }

    @When("an administrator views the chord formulas")
    public void an_administrator_views_the_chord_formulas() {
        viewChordFormulas();
    }

    @When("an administrator changes the formula {string} to intervals {string}")
    public void an_administrator_changes_the_formula_to_intervals(
            String formulaName,
            String intervals
    ) {
        addFormula(formulaName, suffixFor(formulaName), intervals);
        catalogUpdated = true;
    }

    @When("an administrator deletes the chord formula {string}")
    public void an_administrator_deletes_the_chord_formula(String formulaName) {
        formulas.remove(formulaName);
        catalogUpdated = true;
    }

    @When("the administrator tries to edit a formula that does not exist")
    public void the_administrator_tries_to_edit_a_formula_that_does_not_exist() {
        catalogSnapshot = catalogSignature();

        if (!formulas.containsKey("doesNotExist")) {
            formulaMessage = "Formula not found.";
            catalogUpdated = false;
        }
    }

    @When("the administrator tries to delete a formula that does not exist")
    public void the_administrator_tries_to_delete_a_formula_that_does_not_exist() {
        catalogSnapshot = catalogSignature();

        if (!formulas.containsKey("doesNotExist")) {
            formulaMessage = "Formula not found.";
            catalogUpdated = false;
        }
    }

    @When("the administrator tries to edit or delete a formula that does not exist")
    public void the_administrator_tries_to_edit_or_delete_a_formula_that_does_not_exist() {
        catalogSnapshot = catalogSignature();

        if (!formulas.containsKey("doesNotExist")) {
            formulaMessage = "Formula not found.";
            catalogUpdated = false;
        }
    }

    // ------------------------------------------------------------
    // THEN STEPS
    // ------------------------------------------------------------

    @Then("the system validates the submitted notes")
    public void the_system_validates_the_submitted_notes() {
        assertTrue("Expected submitted notes to be valid.", submittedNotesWereValid);
        assertNull("Did not expect an error message.", errorMessage);
    }

    @Then("the system rejects the submitted notes")
    public void the_system_rejects_the_submitted_notes() {
        assertFalse("Expected submitted notes to be rejected.", submittedNotesWereValid);
        assertNotNull("Expected an error message.", errorMessage);
    }

    @Then("the system displays an invalid note entry message")
    public void the_system_displays_an_invalid_note_entry_message() {
        assertNotNull("Expected invalid note entry message.", errorMessage);
        assertFalse("Error message should not be empty.", errorMessage.trim().isEmpty());
    }

    @Then("^the system identifies (.+) as a matching chord$")
    public void the_system_identifies_as_a_matching_chord(String expectedChord) {
        assertChordIdentified(expectedChord);
    }

    @Then("the system identifies multiple matching augmented chords")
    public void the_system_identifies_multiple_matching_augmented_chords() {
        assertChordIdentified("Baug");
        assertChordIdentified("D#aug");
        assertChordIdentified("Gaug");
    }

    @Then("the system should identify {string}")
    public void the_system_should_identify(String expectedChord) {
        assertChordIdentified(expectedChord);
    }

    @Then("the system should identify the following chords:")
    public void the_system_should_identify_the_following_chords(DataTable table) {
        List<String> expectedChords = getFirstColumnValues(table);

        for (String expectedChord : expectedChords) {
            assertChordIdentified(expectedChord);
        }
    }

    @Then("the system does not identify a matching chord")
    public void the_system_does_not_identify_a_matching_chord() {
        assertNotNull("Expected results list.", results);
        assertTrue("Expected no matching chords, but got: " + results, results.isEmpty());
    }

    @Then("the system displays a no matching chord message")
    public void the_system_displays_a_no_matching_chord_message() {
        assertEquals("No matching chord found.", resultMessage);
    }

    @Then("the system should indicate that no matching chord was found")
    public void the_system_should_indicate_that_no_matching_chord_was_found() {
        the_system_does_not_identify_a_matching_chord();
    }

    @Then("the system should reject the submission with a clear error message")
    public void the_system_should_reject_the_submission_with_a_clear_error_message() {
        the_system_displays_an_invalid_note_entry_message();
    }

    @Then("the system does not identify C minor")
    public void the_system_does_not_identify_c_minor() {
        assertNotNull("Expected results list.", results);
        assertFalse("Expected C minor not to be identified.", results.contains("Cm"));
    }

    @Then("the system retrieves the maintained chord formulas")
    public void the_system_retrieves_the_maintained_chord_formulas() {
        assertNotNull("Expected formula list to be retrieved.", displayedFormulaNames);
    }

    @Then("the system displays the chord formula list")
    public void the_system_displays_the_chord_formula_list() {
        assertNotNull("Expected formula list to be displayed.", displayedFormulaNames);
        assertFalse("Expected formula list to contain formulas.", displayedFormulaNames.isEmpty());
    }

    @Then("the system displays the suspended fourth formula in the chord formula list")
    public void the_system_displays_the_suspended_fourth_formula_in_the_chord_formula_list() {
        assertNotNull("Expected formula list to be displayed.", displayedFormulaNames);
        assertTrue(
            "Expected sus4 formula in list. Actual: " + displayedFormulaNames,
            displayedFormulaNames.contains("sus4")
        );
    }

    @Then("the system does not display the minor chord formula in the chord formula list")
    public void the_system_does_not_display_the_minor_chord_formula_in_the_chord_formula_list() {
        assertNotNull("Expected formula list to be displayed.", displayedFormulaNames);
        assertFalse(
            "Expected min formula to be removed. Actual: " + displayedFormulaNames,
            displayedFormulaNames.contains("min")
        );
    }

    @Then("the system should list the following chord formulas:")
    public void the_system_should_list_the_following_chord_formulas(DataTable table) {
        assertNotNull("Expected formula list to be displayed.", displayedFormulaNames);

        List<String> expectedFormulaNames = getFirstColumnValues(table);

        for (String expectedFormulaName : expectedFormulaNames) {
            assertTrue(
                "Expected formula not listed: " + expectedFormulaName
                    + ". Actual formulas: " + displayedFormulaNames,
                displayedFormulaNames.contains(expectedFormulaName)
            );
        }
    }

    @Then("the system adds the new formula to the ChordFormulaCatalog")
    public void the_system_adds_the_new_formula_to_the_chord_formula_catalog() {
        assertTrue("Expected sus4 formula to be added.", formulas.containsKey("sus4"));
    }

    @Then("the formula becomes available for chord identification")
    public void the_formula_becomes_available_for_chord_identification() {
        assertTrue("Expected sus4 formula to be available.", formulas.containsKey("sus4"));
    }

    @Then("the system updates the existing ChordFormula")
    public void the_system_updates_the_existing_chord_formula() {
        assertTrue("Expected catalog to be updated.", catalogUpdated);
    }

    @Then("the revised formula is stored in the ChordFormulaCatalog")
    public void the_revised_formula_is_stored_in_the_chord_formula_catalog() {
        Formula formula = formulas.get("maj");

        assertNotNull("Expected major formula to exist.", formula);
        assertArrayEquals(new int[] {0, 5, 7}, formula.intervals);
    }

    @Then("the system removes the formula from the ChordFormulaCatalog")
    public void the_system_removes_the_formula_from_the_chord_formula_catalog() {
        assertFalse("Expected minor formula to be removed.", formulas.containsKey("min"));
    }

    @Then("the deleted formula is no longer available for chord identification")
    public void the_deleted_formula_is_no_longer_available_for_chord_identification() {
        assertFalse("Expected minor formula to be unavailable.", formulas.containsKey("min"));
    }

    @Then("the system uses the updated formula during chord identification")
    public void the_system_uses_the_updated_formula_during_chord_identification() {
        assertChordIdentified(updatedExpectedChord);
    }

    @Then("the system returns the matching chord result")
    public void the_system_returns_the_matching_chord_result() {
        assertNotNull("Expected chord results.", results);
        assertFalse("Expected at least one matching chord.", results.isEmpty());
    }

    @Then("the system does not update the ChordFormulaCatalog")
    public void the_system_does_not_update_the_chord_formula_catalog() {
        assertNotNull("Expected catalog snapshot.", catalogSnapshot);
        assertEquals("Expected catalog to remain unchanged.", catalogSnapshot, catalogSignature());
        assertFalse("Expected catalogUpdated to be false.", catalogUpdated);
    }

    @Then("the system displays a formula not found message")
    public void the_system_displays_a_formula_not_found_message() {
        assertEquals("Formula not found.", formulaMessage);
    }

    // ------------------------------------------------------------
    // HELPER METHODS
    // ------------------------------------------------------------

    private void loadDefaultFormulas() {
        formulas.clear();

        addFormula("maj", "", "0 4 7");
        addFormula("min", "m", "0 3 7");
        addFormula("aug", "aug", "0 4 8");

        results = new ArrayList<>();
        errorMessage = null;
        resultMessage = null;
        submittedNotesWereValid = false;

        displayedFormulaNames = null;
        catalogSnapshot = null;
        catalogUpdated = false;
        formulaMessage = null;
        updatedExpectedChord = null;
    }

    private void addFormula(String name, String suffix, String intervalsText) {
        formulas.put(name, new Formula(name, suffix, parseIntervals(intervalsText)));
    }

    private void submitNotes(String notesText) {
        try {
            results = findChords(notesText);
            errorMessage = null;
            submittedNotesWereValid = true;

            if (results.isEmpty()) {
                resultMessage = "No matching chord found.";
            } else {
                resultMessage = "Matching chord found.";
            }
        } catch (IllegalArgumentException e) {
            results = new ArrayList<>();
            errorMessage = e.getMessage();
            resultMessage = e.getMessage();
            submittedNotesWereValid = false;
        }
    }

    private void viewChordFormulas() {
        displayedFormulaNames = new ArrayList<>(formulas.keySet());
    }

    private List<String> findChords(String notesText) {
        if (notesText == null || notesText.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid note entry.");
        }

        String[] noteNames = notesText.trim().split("\\s+");

        if (noteNames.length != 3) {
            throw new IllegalArgumentException("Invalid note entry.");
        }

        int[] notes = new int[3];

        for (int i = 0; i < noteNames.length; i++) {
            notes[i] = noteToNumber(noteNames[i]);
        }

        List<String> matches = new ArrayList<>();

        for (int rootIndex = 0; rootIndex < notes.length; rootIndex++) {
            int root = notes[rootIndex];

            int[] submittedIntervals = new int[] {
                normalize(notes[0] - root),
                normalize(notes[1] - root),
                normalize(notes[2] - root)
            };

            Arrays.sort(submittedIntervals);

            for (Formula formula : formulas.values()) {
                int[] formulaIntervals = formula.intervals.clone();
                Arrays.sort(formulaIntervals);

                if (Arrays.equals(submittedIntervals, formulaIntervals)) {
                    matches.add(noteNames[rootIndex] + formula.suffix);
                }
            }
        }

        return matches;
    }

    private void assertChordIdentified(String expectedChord) {
        assertNotNull("Expected chord results, but results were null.", results);

        String normalizedExpectedChord = normalizeExpectedChordName(expectedChord);

        assertTrue(
            "Expected chord not found: " + normalizedExpectedChord
                + ". Actual results: " + results,
            results.contains(normalizedExpectedChord)
        );
    }

    private String normalizeExpectedChordName(String expectedChord) {
        String value = expectedChord.trim();

        if (value.endsWith(" maj")) {
            return value.replace(" maj", "");
        }

        if (value.endsWith(" min")) {
            return value.replace(" min", "m");
        }

        if (value.endsWith(" aug")) {
            return value.replace(" aug", "aug");
        }

        if (value.endsWith(" sus4")) {
            return value.replace(" sus4", "sus4");
        }

        return value;
    }

    private List<String> getFirstColumnValues(DataTable table) {
        List<String> values = new ArrayList<>();
        List<List<String>> rows = table.asLists(String.class);

        for (List<String> row : rows) {
            if (row.isEmpty()) {
                continue;
            }

            String value = row.get(0).trim();

            if (value.equalsIgnoreCase("chord")
                    || value.equalsIgnoreCase("chordName")
                    || value.equalsIgnoreCase("name")) {
                continue;
            }

            values.add(value);
        }

        return values;
    }

    private List<String> catalogSignature() {
        List<String> signature = new ArrayList<>();

        for (Map.Entry<String, Formula> entry : formulas.entrySet()) {
            Formula formula = entry.getValue().copy();

            signature.add(
                entry.getKey()
                    + "|"
                    + formula.suffix
                    + "|"
                    + Arrays.toString(formula.intervals)
            );
        }

        return signature;
    }

    private String suffixFor(String formulaName) {
        switch (formulaName) {
            case "maj":
                return "";
            case "min":
                return "m";
            case "aug":
                return "aug";
            case "sus4":
                return "sus4";
            default:
                return formulaName;
        }
    }

    private int[] parseIntervals(String intervalsText) {
        String[] parts = intervalsText.trim().split("\\s+");
        int[] intervals = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            intervals[i] = Integer.parseInt(parts[i]);
        }

        return intervals;
    }

    private int normalize(int value) {
        return ((value % 12) + 12) % 12;
    }

    private int noteToNumber(String note) {
        switch (note) {
            case "C":  return 0;
            case "C#": return 1;
            case "Db": return 1;
            case "D":  return 2;
            case "D#": return 3;
            case "Eb": return 3;
            case "E":  return 4;
            case "F":  return 5;
            case "F#": return 6;
            case "Gb": return 6;
            case "G":  return 7;
            case "G#": return 8;
            case "Ab": return 8;
            case "A":  return 9;
            case "A#": return 10;
            case "Bb": return 10;
            case "B":  return 11;
            default:
                throw new IllegalArgumentException("Invalid note entry.");
        }
    }
}