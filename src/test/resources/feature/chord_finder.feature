Feature: Find Chord

  Scenario: Identify G major from D G B
    Given the Chord Finder system has a maintained major chord formula
    When the user submits D G B
    Then the system validates the submitted notes
    And the system identifies G as a matching chord

  Scenario: Identify C minor from C Eb G
    Given the Chord Finder system has a maintained minor chord formula
    When the user submits C Eb G
    Then the system validates the submitted notes
    And the system identifies Cm as a matching chord

  Scenario: Identify multiple augmented chords from B D# G
    Given the Chord Finder system has a maintained augmented chord formula
    When the user submits B D# G
    Then the system validates the submitted notes
    And the system identifies Baug as a matching chord
    And the system identifies D#aug as a matching chord
    And the system identifies Gaug as a matching chord

  Scenario: Reject fewer than three notes
    Given the user is using the Chord Finder system
    When the user submits C E
    Then the system rejects the submitted notes
    And the system displays an invalid note entry message

  Scenario: Reject more than three notes
    Given the user is using the Chord Finder system
    When the user submits C E G B
    Then the system rejects the submitted notes
    And the system displays an invalid note entry message

  Scenario: Reject invalid note spelling
    Given the user is using the Chord Finder system
    When the user submits C H G
    Then the system rejects the submitted notes
    And the system displays an invalid note entry message

  Scenario: Reject blank note input
    Given the user is using the Chord Finder system
    When the user submits blank note input
    Then the system rejects the submitted notes
    And the system displays an invalid note entry message

  Scenario: Display no matching chord message
    Given the Chord Finder system has maintained chord formulas
    When the user submits C D G
    Then the system validates the submitted notes
    And the system does not identify a matching chord
    And the system displays a no matching chord message

  Scenario: Display no matching chord for duplicate notes
    Given the Chord Finder system has maintained chord formulas
    When the user submits C C G
    Then the system validates the submitted notes
    And the system does not identify a matching chord
    And the system displays a no matching chord message

  Scenario: Identify chord using flat note spelling
    Given the Chord Finder system has a maintained major chord formula
    When the user submits Db F Ab
    Then the system validates the submitted notes
    And the system identifies Db as a matching chord

  Scenario: Identify chord using sharp note spelling
    Given the Chord Finder system has a maintained major chord formula
    When the user submits C# F G#
    Then the system validates the submitted notes
    And the system identifies C# as a matching chord

  Scenario: View maintained chord formula list
    Given the administrator is maintaining chord formulas
    When the administrator selects view chord formulas
    Then the system retrieves the maintained chord formulas
    And the system displays the chord formula list

  Scenario: View newly added formula in formula list
    Given the administrator has added a suspended fourth formula
    When the administrator views maintained chord formulas
    Then the system displays the suspended fourth formula in the chord formula list

  Scenario: Confirm deleted formula is removed from list
    Given the administrator has deleted the minor chord formula
    When the administrator views maintained chord formulas
    Then the system does not display the minor chord formula in the chord formula list

  Scenario: Identify chord using newly added formula
    Given the administrator has added a suspended fourth formula
    When the user submits C F G
    Then the system validates the submitted notes
    And the system identifies Csus4 as a matching chord

  Scenario: Removed formula is no longer used
    Given the administrator has deleted the minor chord formula
    When the user submits C Eb G
    Then the system validates the submitted notes
    And the system does not identify C minor

  Scenario: Updated formula changes chord matching
    Given the administrator has edited an existing chord formula
    When the user submits notes that match the updated formula
    Then the system uses the updated formula during chord identification
    And the system returns the matching chord result

  Scenario: Display formula not found when editing
    Given the administrator is maintaining chord formulas
    When the administrator tries to edit a formula that does not exist
    Then the system does not update the ChordFormulaCatalog
    And the system displays a formula not found message

  Scenario: Display formula not found when deleting
    Given the administrator is maintaining chord formulas
    When the administrator tries to delete a formula that does not exist
    Then the system does not update the ChordFormulaCatalog
    And the system displays a formula not found message