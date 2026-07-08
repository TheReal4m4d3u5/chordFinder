Feature: Find Chord



Scenario: Rebuild formula catalog and only use newly added formula
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message
  When the administrator defines a major chord formula
  And the user submits C E G
  Then the system identifies C as a matching chord
  And the system does not identify Cm as a matching chord
  And the system does not identify Cdim as a matching chord
  And the system does not identify Caug as a matching chord


Scenario: Rebuilt formula catalog only uses newly added major formula
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the administrator defines a major chord formula
  And the user submits C E G
  Then the system validates the submitted notes
  And the system identifies C as a matching chord
  And the system does not identify Cm as a matching chord
  And the system does not identify Cdim as a matching chord
  And the system does not identify Caug as a matching chord


Scenario: Rebuilt formula catalog only uses newly added minor formula
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the administrator defines a minor chord formula
  And the user submits C Eb G
  Then the system validates the submitted notes
  And the system identifies Cm as a matching chord
  And the system does not identify C as a matching chord
  And the system does not identify Cdim as a matching chord
  And the system does not identify Caug as a matching chord


Scenario: Rebuilt formula catalog only uses newly added diminished formula
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the administrator defines a diminished chord formula
  And the user submits C Eb Gb
  Then the system validates the submitted notes
  And the system identifies Cdim as a matching chord
  And the system does not identify C as a matching chord
  And the system does not identify Cm as a matching chord
  And the system does not identify Caug as a matching chord


Scenario: Rebuilt formula catalog only uses newly added augmented formula
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the administrator defines an augmented chord formula
  And the user submits C E G#
  Then the system validates the submitted notes
  And the system identifies Caug as a matching chord
  And the system does not identify C as a matching chord
  And the system does not identify Cm as a matching chord
  And the system does not identify Cdim as a matching chord





Scenario: Submit valid notes when formula catalog is empty
  Given the Chord Finder system has no maintained chord formulas
  When the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message


Scenario: Deleted formulas are not used after catalog rebuild
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  When the administrator defines a major chord formula
  And the user submits C E G
  Then the system identifies C as a matching chord
  And the system does not identify Cm as a matching chord
  And the system does not identify Cdim as a matching chord
  And the system does not identify Caug as a matching chord


Scenario: Same submitted notes are evaluated against current catalog state
  Given the administrator deletes all chord formulas
  When the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message
  When the administrator defines a major chord formula
  And the user submits C E G
  Then the system identifies C as a matching chord


Scenario: Chord results are not reused after formulas are deleted
  Given the Chord Finder system has maintained chord formulas
  When the user submits C E G
  Then the system identifies C as a matching chord
  When the administrator deletes all chord formulas
  And the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message


Scenario: Invalid notes are rejected before searching empty catalog
  Given the administrator deletes all chord formulas
  When the user submits C E H
  Then the system rejects the submitted notes


Scenario: Fewer than three notes are rejected before searching empty catalog
  Given the administrator deletes all chord formulas
  When the user submits C E
  Then the system rejects the submitted notes


Scenario: More than three notes are rejected before searching empty catalog
  Given the administrator deletes all chord formulas
  When the user submits C E G B
  Then the system rejects the submitted notes


Scenario: Delete all formulas from an already empty catalog
  Given the Chord Finder system has no maintained chord formulas
  When the administrator deletes all chord formulas
  And the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message


Scenario: Formula catalog uses current state after multiple rebuilds
  Given the Chord Finder system has maintained chord formulas
  And the administrator deletes all chord formulas
  And the administrator defines a major chord formula
  When the user submits C E G
  Then the system identifies C as a matching chord
  When the administrator deletes all chord formulas
  And the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message



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

Scenario: Identify C diminished from C Eb Gb
  Given the Chord Finder system has a maintained diminished chord formula
  When the user submits C Eb Gb
  Then the system validates the submitted notes
  And the system identifies Cdim as a matching chord

Scenario: Identify multiple augmented chords from B D# G
  Given the Chord Finder system has a maintained augmented chord formula
  When the user submits B D# G
  Then the system validates the submitted notes
  And the system identifies Baug as a matching chord
  And the system identifies D#aug as a matching chord
  And the system identifies Gaug as a matching chord

Scenario: Identify chord regardless of note order
  Given the Chord Finder system has a maintained major chord formula
  When the user submits E G C
  Then the system validates the submitted notes
  And the system identifies C as a matching chord

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

Scenario: Identify chord when input has extra spaces
  Given the Chord Finder system has a maintained major chord formula
  When the user submits " C E G "
  Then the system validates the submitted notes
  And the system identifies C as a matching chord

Scenario: Identify chord when input uses lowercase notes
  Given the Chord Finder system has a maintained major chord formula
  When the user submits c e g
  Then the system validates the submitted notes
  And the system identifies C as a matching chord

Scenario: Reject fewer than three notes
  Given the user is using the Chord Finder system
  When the user submits C E
  Then the system rejects the submitted notes
  And the system displays an error message that exactly three notes are required

Scenario: Reject more than three notes
  Given the user is using the Chord Finder system
  When the user submits C E G B
  Then the system rejects the submitted notes
  And the system displays an error message that exactly three notes are required

Scenario: Reject invalid note spelling
  Given the user is using the Chord Finder system
  When the user submits C H G
  Then the system rejects the submitted notes
  And the system displays an invalid note message

Scenario: Reject unsupported accidental spelling
  Given the user is using the Chord Finder system
  When the user submits C## E G
  Then the system rejects the submitted notes
  And the system displays an invalid note message

Scenario: Reject blank note input
  Given the user is using the Chord Finder system
  When the user submits blank note input
  Then the system rejects the submitted notes
  And the system displays a message that notes are required

Scenario: Display no matching chord message
  Given the Chord Finder system has maintained chord formulas
  When the user submits C D E
  Then the system validates the submitted notes
  And the system displays a no matching chord message

Scenario: Display no match for duplicate submitted notes
  Given the user is using the Chord Finder system
  When the user submits C C E
  Then the system rejects the submitted notes
  And the system displays a message that three different notes are required

Scenario: Display no match when formula catalog is empty
  Given the Chord Finder system has no maintained chord formulas
  When the user submits C E G
  Then the system validates the submitted notes
  And the system displays a no matching chord message
  
  

Scenario: Define a new chord formula
  Given the administrator is using the Maintain Chord Formula menu
  When the administrator defines a chord formula with valid formula information
  Then the system stores the chord formula
  And the system displays the updated formula catalog

Scenario: Edit an existing chord formula
  Given the Chord Finder system has a maintained chord formula
  And the administrator is using the Maintain Chord Formula menu
  When the administrator edits the chord formula with valid formula information
  Then the system updates the chord formula
  And the system displays the updated formula catalog

Scenario: Delete an existing chord formula
  Given the Chord Finder system has a maintained chord formula
  And the administrator is using the Maintain Chord Formula menu
  When the administrator deletes the chord formula
  Then the system removes the chord formula
  And the system displays the updated formula catalog

Scenario: View maintained chord formulas
  Given the Chord Finder system has maintained chord formulas
  And the administrator is using the Maintain Chord Formula menu
  When the administrator views the chord formulas
  Then the system displays the maintained chord formulas

Scenario: View empty chord formula catalog
  Given the Chord Finder system has no maintained chord formulas
  And the administrator is using the Maintain Chord Formula menu
  When the administrator views the chord formulas
  Then the system displays a message that no chord formulas are maintained

Scenario: Reject invalid formula information
  Given the administrator is using the Maintain Chord Formula menu
  When the administrator defines a chord formula with invalid formula information
  Then the system rejects the chord formula
  And the system displays an invalid formula message

Scenario: Reject duplicate formula name
  Given the Chord Finder system already has a maintained major chord formula
  And the administrator is using the Maintain Chord Formula menu
  When the administrator defines another formula named major
  Then the system rejects the chord formula
  And the system displays a duplicate formula name message

Scenario: Reject duplicate formula pattern
  Given the Chord Finder system already has a maintained major chord formula
  And the administrator is using the Maintain Chord Formula menu
  When the administrator defines another formula with the same interval pattern as major
  Then the system rejects the chord formula
  And the system displays a duplicate formula pattern message

Scenario: Display formula not found when editing
  Given the Chord Finder system does not have a formula named suspended
  And the administrator is using the Maintain Chord Formula menu
  When the administrator attempts to edit the suspended formula
  Then the system displays a formula not found message

Scenario: Display formula not found when deleting
  Given the Chord Finder system does not have a formula named suspended
  And the administrator is using the Maintain Chord Formula menu
  When the administrator attempts to delete the suspended formula
  Then the system displays a formula not found message

Scenario: Use newly defined formula in future chord searches
  Given the administrator defines a valid suspended chord formula
  When the user submits C F G
  Then the system validates the submitted notes
  And the system identifies Csus4 as a matching chord

Scenario: Use edited formula in future chord searches
  Given the Chord Finder system has a maintained chord formula
  When the administrator edits the chord formula with valid formula information
  And the user submits C F G
  Then the system identifies a chord using the edited formula

Scenario: Do not use deleted formula in future chord searches
  Given the Chord Finder system has a maintained suspended chord formula
  And the administrator deletes the suspended chord formula
  When the user submits C F G
  Then the system validates the submitted notes
  And the system does not identify Csus4 as a matching chord

Scenario: Cancel formula definition
  Given the administrator is defining a new chord formula
  When the administrator cancels the formula definition
  Then the system does not store the chord formula
  And the system returns to the Maintain Chord Formula menu

Scenario: Cancel formula edit
  Given the Chord Finder system has a maintained chord formula
  And the administrator is editing the chord formula
  When the administrator cancels the formula edit
  Then the system does not change the chord formula
  And the system returns to the Maintain Chord Formula menu

Scenario: Cancel formula deletion
  Given the Chord Finder system has a maintained chord formula
  And the administrator is deleting the chord formula
  When the administrator cancels the formula deletion
  Then the system does not remove the chord formula
  And the system returns to the Maintain Chord Formula menu  
  
  