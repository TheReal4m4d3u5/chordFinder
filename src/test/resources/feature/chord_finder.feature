Feature: Chord finder

  The chord finder helps a music instructor or student identify supported triads
  from exactly three valid note names.

  The system should also allow an administrator to maintain chord formula
  definitions so future searches use the current formulas.

  Background:
    Given the available chord formulas include "maj"
    And the available chord formulas include "min"
    And the available chord formulas include "aug"

  Scenario: Identify a supported major triad
    When a user submits the notes "D G B"
    Then the system should identify "G maj"

  Scenario: Identify a supported minor triad
    When a user submits the notes "C Eb G"
    Then the system should identify "C min"

  Scenario: Identify a supported augmented triad
    When a user submits the notes "B D# G"
    Then the system should identify the following chords:
      | B aug  |
      | D# aug |
      | G aug  |

  Scenario: Reject fewer than three notes
    When a user submits the notes "C E"
    Then the system should reject the submission with a clear error message

  Scenario: Reject more than three notes
    When a user submits the notes "C E G B"
    Then the system should reject the submission with a clear error message

  Scenario: Reject an unrecognized note spelling
    When a user submits the notes "C H G"
    Then the system should reject the submission with a clear error message

  Scenario: Handle valid notes with no matching chord
    When a user submits the notes "C D G"
    Then the system should indicate that no matching chord was found

  Scenario: Administrator adds a new chord formula
    Given an administrator defines a new chord formula named "sus4" with intervals "0 5 7"
    When a user submits the notes "C F G"
    Then the system should identify "C sus4"

  Scenario: Administrator edits an existing chord formula
    Given the available chord formulas include "sus4" with intervals "0 5 7"
    When an administrator changes the formula "sus4" to intervals "0 4 7"
    And a user submits the notes "C E G"
    Then the system should identify "C sus4"

  Scenario: Administrator deletes an existing chord formula
    Given the available chord formulas include "sus4" with intervals "0 5 7"
    When an administrator deletes the chord formula "sus4"
    And a user submits the notes "C F G"
    Then the system should indicate that no matching chord was found