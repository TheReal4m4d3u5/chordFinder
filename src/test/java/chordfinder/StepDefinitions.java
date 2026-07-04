package chordfinder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class StepDefinitions {

    private final Map<String, int[]> formulas = new HashMap<>();
    private List<String> results;
    private String errorMessage;

    public StepDefinitions() {
        formulas.put("maj", new int[] {0, 4, 7});
        formulas.put("min", new int[] {0, 3, 7});
        formulas.put("aug", new int[] {0, 4, 8});
    }

    @Given("the available chord formulas include {string}")
    public void the_available_chord_formulas_include(String formulaName) {
        assertTrue(
            "Formula should exist: " + formulaName,
            formulas.containsKey(formulaName)
        );
    }

    @Given("the available chord formulas include {string} with intervals {string}")
    public void the_available_chord_formulas_include_with_intervals(String formulaName, String intervals) {
        formulas.put(formulaName, parseIntervals(intervals));
    }

    @Given("an administrator defines a new chord formula named {string} with intervals {string}")
    public void an_administrator_defines_a_new_chord_formula_named_with_intervals(String formulaName, String intervals) {
        formulas.put(formulaName, parseIntervals(intervals));
    }

    @When("an administrator changes the formula {string} to intervals {string}")
    public void an_administrator_changes_the_formula_to_intervals(String formulaName, String intervals) {
        formulas.put(formulaName, parseIntervals(intervals));
    }

    @When("an administrator deletes the chord formula {string}")
    public void an_administrator_deletes_the_chord_formula(String formulaName) {
        formulas.remove(formulaName);
    }

    @When("a user submits the notes {string}")
    public void a_user_submits_the_notes(String notesText) {
        try {
            results = findChords(notesText);
            errorMessage = null;
        } catch (IllegalArgumentException e) {
            results = new ArrayList<>();
            errorMessage = e.getMessage();
        }
    }

    @Then("the system should identify {string}")
    public void the_system_should_identify(String expectedChord) {
        assertNotNull("Expected chord results, but results were null.", results);

        assertTrue(
            "Expected chord not found: " + expectedChord + ". Actual results: " + results,
            results.contains(expectedChord)
        );
    }

    @Then("the system should identify the following chords:")
    public void the_system_should_identify_the_following_chords(DataTable table) {
        List<String> expectedChords = table.asList(String.class);

        assertNotNull("Expected chord results, but results were null.", results);

        for (String expectedChord : expectedChords) {
            assertTrue(
                "Expected chord not found: " + expectedChord + ". Actual results: " + results,
                results.contains(expectedChord)
            );
        }
    }

    @Then("the system should reject the submission with a clear error message")
    public void the_system_should_reject_the_submission_with_a_clear_error_message() {
        assertNotNull("Expected an error message.", errorMessage);
        assertFalse("Error message should not be empty.", errorMessage.trim().isEmpty());
    }

    @Then("the system should indicate that no matching chord was found")
    public void the_system_should_indicate_that_no_matching_chord_was_found() {
        assertNotNull("Expected results list, but results were null.", results);

        assertTrue(
            "Expected no matching chords, but got: " + results,
            results.isEmpty()
        );
    }

    private List<String> findChords(String notesText) {
        String[] noteNames = notesText.trim().split("\\s+");

        if (noteNames.length != 3) {
            throw new IllegalArgumentException("Please enter exactly three notes.");
        }

        int[] notes = new int[3];

        for (int i = 0; i < noteNames.length; i++) {
            notes[i] = noteToNumber(noteNames[i]);
        }

        List<String> matches = new ArrayList<>();

        for (int rootIndex = 0; rootIndex < notes.length; rootIndex++) {
            int root = notes[rootIndex];

            int[] intervals = new int[] {
                normalize(notes[0] - root),
                normalize(notes[1] - root),
                normalize(notes[2] - root)
            };

            Arrays.sort(intervals);

            for (Map.Entry<String, int[]> entry : formulas.entrySet()) {
                int[] formulaIntervals = entry.getValue().clone();
                Arrays.sort(formulaIntervals);

                if (Arrays.equals(intervals, formulaIntervals)) {
                    matches.add(noteNames[rootIndex] + " " + entry.getKey());
                }
            }
        }

        return matches;
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
                throw new IllegalArgumentException("Unrecognized note: " + note);
        }
    }
}