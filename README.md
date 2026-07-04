# chordFinder

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

## Description

ChordFinder is a Java-based music theory application that identifies possible triad chord names from three notes entered by a user. The system validates supported note spellings, maps notes to pitch positions, compares the submitted notes against maintained chord formulas, and returns all matching chord interpretations.

The application was designed using an object-oriented approach that focuses on modeling the main concepts in the Chord Finder domain as software objects. The design centers around the ChordFinderSystem, which coordinates note validation, chord identification, and formula maintenance. The main domain classes are ChordFinderSystem, ChordFormula, Chord, and Note.

ChordFinder also includes an administrator workflow for maintaining chord formulas. An administrator can define, edit, delete, and view chord formulas. Future chord searches use the current available formula set, which allows the system to be extended without redesigning the core chord identification behavior.

The project includes both JUnit unit tests and Cucumber BDD tests. The JUnit tests verify lower-level class behavior, while the Cucumber tests verify use-case behavior from the user and administrator perspective.

## Design Process

I used an object-oriented design approach by first understanding the Chord Finder problem domain before writing code. I identified the major behaviors the system needed to support: a Chord Finder User can identify chords from submitted notes, and an Administrator can maintain chord formulas.

The development process started with noun analysis and domain modeling. I reviewed the requirements and extracted important nouns such as ChordFinderSystem, ChordFormula, Chord, Note, chord name, pitch position, chord quality, administrator, and chord finder user. I then validated each noun by asking whether it represented a meaningful object with state and behavior.

The final domain model focused on four main classes: ChordFinderSystem, ChordFormula, Chord, and Note. Supporting ideas such as pitch position, sharp-oriented name, flat-oriented name, chord quality, and display suffix were treated as attributes rather than separate classes.

Each class was designed with one main responsibility. Note handles spelling, pitch position, validation, and interval distance. ChordFormula stores the formula pattern and determines whether notes match that pattern. Chord represents an identified chord name using a root note and formula. ChordFinderSystem coordinates the application by validating notes, identifying chords, and maintaining formulas.

Overall, the project moved from requirements analysis, to use case modeling, to domain modeling, to Java implementation, and then to automated testing with JUnit and Cucumber BDD.

## Table of Contents

- [chordFinder](#chordfinder)
  - [Description](#description)
  - [Design Process](#design-process)
  - [Table of Contents](#table-of-contents)
  - [Domain Modeling](#domain-modeling)
  - [Use Cases](#use-cases)
    - [Find Chord](#find-chord)
    - [Maintain Chord Formula](#maintain-chord-formula)
  - [UML Class Diagram](#uml-class-diagram)
    - [Classes](#classes)
  - [ChordFinderSystem](#chordfindersystem)
  - [ChordFormula](#chordformula)
  - [Chord](#chord)
  - [Note](#note)
  - [Main](#main)
    - [Relationships](#relationships)
  - [Use Case Diagram](#use-case-diagram)
  - [Sequence Diagram](#sequence-diagram)
  - [State Diagram](#state-diagram)
  - [Structural Mapping](#structural-mapping)
  - [Application Flow](#application-flow)
  - [Design Decisions](#design-decisions)
  - [Object-Oriented Design Principles](#object-oriented-design-principles)
  - [Installation](#installation)
    - [Prerequisites](#prerequisites)
    - [Clone the Project](#clone-the-project)

## Domain Modeling

The domain model was created by identifying the objects that exist in the Chord Finder system universe and validating whether each object has meaningful state and behavior.

The core domain objects are:

ChordFinderSystem  
ChordFormula  
Chord  
Note  

ChordFinderSystem represents the main application. It owns the current set of chord formulas, validates submitted notes, identifies matching chords, and supports administrator formula maintenance.

ChordFormula represents a maintained formula definition. It stores a chord quality name, display suffix, and interval values such as root-to-third and root-to-fifth. The formula determines whether a submitted set of notes matches a chord type.

Chord represents an identified chord. It has a root note, a chord formula, and a generated chord name such as C maj, C min, G aug, or D# aug.

Note represents a submitted or recognized musical note. It stores the note spelling, pitch position, sharp-oriented name, flat-oriented name, and other recognized names. It also supports note validation and distance calculation between pitch positions.

This domain model keeps the system focused on the objects needed to support the required behavior without adding unnecessary classes for simple values or out-of-scope music theory concepts.







![alt text](image.png)









## Use Cases

The primary use cases in the ChordFinder application are Find Chord and Maintain Chord Formula.

![alt text](image-1.png)

### Find Chord

Primary Actor: Chord Finder User

The Chord Finder User enters exactly three notes and submits them for chord identification. The system validates the notes, compares them against the available chord formulas, and displays all matching chord names.

Main flow:

1. User selects Chord Finder User.
2. User enters three notes.
3. User submits notes.
4. System validates notes.
5. System identifies chord matches.
6. System displays all applicable chord names.

Alternative flows:

- If fewer than three notes are entered, the system displays an invalid note count message.
- If more than three notes are entered, the system displays an invalid note count message.
- If a note spelling is invalid, the system displays an invalid note message.
- If valid notes do not match any chord formula, the system displays a no matching chord message.
- If more than one chord interpretation exists, the system displays all matching chord names.

### Maintain Chord Formula

Primary Actor: Administrator

The Administrator maintains the chord formulas used by future chord searches. This use case includes defining, editing, deleting, and viewing chord formulas.

Main flow:

1. Administrator selects Administrator.
2. System requests administrator password.
3. Administrator enters password.
4. System displays the formula maintenance menu.
5. Administrator chooses a maintenance action.
6. System updates the formula set.
7. System confirms the maintenance result.

Supported actions:

- Define Chord Formula
- Edit Chord Formula
- Delete Chord Formula
- View Chord Formulas

## UML Class Diagram

The UML class diagram represents the main classes in the ChordFinder system and how they relate to each other.


### Classes

ChordFinderSystem
-----------------
- chordFormulas : List<ChordFormula>
- submittedNotes : List<Note>
- identifiedChords : List<Chord>
- resultMessage : String

+ identifyChord(submittedNoteText : String) : List<Chord>
+ validateNotes(submittedNoteText : String) : List<Note>
+ defineChordFormula(formula : ChordFormula) : void
+ editChordFormula(qualityName : String, revisedFormula : ChordFormula) : void
+ deleteChordFormula(qualityName : String) : boolean
+ getChordFormulas() : List<ChordFormula>
+ displayResult() : void

ChordFormula
------------
- qualityName : String
- displaySuffix : String
- rootToThird : int
- rootToFifth : int

+ matchesNotes(rootNote : Note, submittedNotes : List<Note>) : boolean
+ updateFormula(qualityName : String, displaySuffix : String, rootToThird : int, rootToFifth : int) : void
+ getQualityName() : String
+ getDisplaySuffix() : String
+ getRootToThird() : int
+ getRootToFifth() : int

Chord
-----
- rootNote : Note
- chordFormula : ChordFormula
- notes : List<Note>
- chordName : String

+ getChordName() : String
+ getRootNote() : Note
+ getChordFormula() : ChordFormula
+ getNotes() : List<Note>

Note
----
- spelling : String
- pitchPosition : int
- sharpName : String
- flatName : String
- otherNames : List<String>

+ from(noteText : String) : Note
+ isValid(noteText : String) : boolean
+ isValid() : boolean
+ distanceTo(otherNote : Note) : int
+ getSpelling() : String
+ getPitchPosition() : int
+ getSharpName() : String
+ getFlatName() : String
+ getOtherNames() : List<String>

Main
----
- ADMIN_PASSWORD : String

+ main(args : String[]) : void
+ runChordFinderUser(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ runAdministratorLogin(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ runAdministratorMenu(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ defineChordFormula(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ editChordFormula(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ deleteChordFormula(scanner : Scanner, chordFinderSystem : ChordFinderSystem) : void
+ viewChordFormulas(chordFinderSystem : ChordFinderSystem) : void
+ readChordFormula(scanner : Scanner) : ChordFormula

### Relationships

Main uses ChordFinderSystem

ChordFinderSystem aggregates many ChordFormula objects

ChordFinderSystem validates three Note objects

ChordFinderSystem identifies zero or more Chord objects

ChordFormula classifies zero or more Chord objects

Chord uses one ChordFormula

Chord has one root Note

Chord aggregates three Note objects

## Use Case Diagram

The use case diagram shows two primary actors: Chord Finder User and Administrator.

The Chord Finder User performs the Find Chord use case. This use case allows the user to enter three notes, submit the notes for identification, and receive matching chord names or a clear result message.

The Administrator performs the Maintain Chord Formula use case. This use case includes Define Chord Formula, Edit Chord Formula, Delete Chord Formula, and View Chord Formulas.

The use case diagram separates normal chord identification behavior from administrative formula maintenance behavior. This makes the system responsibilities clear and keeps user-facing behavior separate from administrator behavior.

## Sequence Diagram

The sequence diagram for Find Chord shows the interaction that occurs when a user submits notes.

The sequence begins when the Chord Finder User enters three notes into the console interface. The Main class sends the submitted note text to ChordFinderSystem. ChordFinderSystem validates the note count and note spellings by creating Note objects. If the notes are invalid, the system returns an error message.

If the notes are valid, ChordFinderSystem checks each possible root note against each available ChordFormula. Each ChordFormula determines whether the submitted notes match its interval pattern. When a formula matches, the system creates a Chord object using the root note and matching formula.

After all formulas and possible roots are checked, ChordFinderSystem returns the list of identified chords. Main displays either all matching chord names or a no matching chord message.

The administrator sequence begins when the Administrator logs in and selects a formula maintenance action. Main sends the selected action to ChordFinderSystem. ChordFinderSystem then defines, edits, deletes, or displays formulas based on the selected action.

## State Diagram

The ChordFinder application moves through a small set of states during execution.

The application begins in the Start state. From there, the system displays the role selection menu. The user can choose Chord Finder User, Administrator, or Exit.

If Chord Finder User is selected, the system enters the Find Chord state. In this state, the user enters notes and submits them for identification. The system then enters Validate Notes. If validation fails, the system displays an error message and returns to Find Chord. If validation succeeds, the system enters Identify Chord. The system then displays either matching chord names or a no matching chord message.

If Administrator is selected, the system enters Administrator Login. If the password is invalid, the system returns to the main menu. If the password is valid, the system enters Maintain Chord Formula. From there, the administrator can define, edit, delete, or view formulas.

The application ends when the user selects Exit from the main menu.

## Structural Mapping

The structural mapping shows how the major ChordFinder objects are connected.

ChordFinderSystem is the central object. It owns the current list of ChordFormula objects. A ChordFinderSystem can have many ChordFormula objects because the administrator can add formulas over time.

ChordFinderSystem identifies zero or more Chord objects from submitted notes. A search may return no matching chords, one matching chord, or multiple matching chords.

A Chord is classified by one ChordFormula. A Chord also has one root Note and is made from exactly three Note objects.

A Note contains its spelling and pitch position information. The pitch position is used to calculate intervals in half steps.

Main is not part of the core domain model, but it acts as the console boundary class that lets users interact with ChordFinderSystem.

Main relationships:

ChordFinderSystem 1 aggregates 1..* ChordFormula  
ChordFinderSystem 1 identifies 0..* Chord  
ChordFormula 1 classifies 0..* Chord  
Chord 1 uses 1 ChordFormula  
Chord 1 has 1 root Note  
Chord 1 aggregates 3 Note  
Main 1 uses 1 ChordFinderSystem  

## Application Flow

The application begins by asking whether the person using the program is a Chord Finder User or an Administrator.

If the person selects Chord Finder User, the system starts the Find Chord workflow. The user enters three notes separated by spaces. The system validates the input, checks the notes against the available formulas, and displays the matching chord names.

If the person selects Administrator, the system asks for the administrator password. After a successful login, the administrator can define, edit, delete, or view chord formulas. Any changes made to the formula list affect future chord searches.

The core application flow is:

1. Start application.
2. Select role.
3. Run Find Chord or Maintain Chord Formula.
4. Validate input.
5. Process the selected behavior.
6. Display result.
7. Return to menu or exit.

## Design Decisions

I designed ChordFinder around the main domain objects instead of putting all behavior into one large procedural class. The goal was to make the code reflect the real problem domain.

I kept ChordFinderSystem as the main coordinating class because the system needs one object responsible for validating notes, using formulas, identifying chords, and maintaining formulas.

I kept ChordFormula as a separate class because formulas are maintained by the administrator and are used by future chord searches. Each formula has its own quality name, display suffix, and interval pattern.

I kept Chord as a separate class because the system identifies chords, not just text names. A chord has a root note, formula, and generated chord name.

I kept Note as a separate class because note validation and pitch position mapping are central to the system. A note is not just a string; it has spelling, pitch position, and alternate names.

I did not create separate classes for pitch position, sharp name, flat name, chord quality, or display suffix because those concepts are simple values that belong inside Note or ChordFormula.

I also did not keep SearchResult as a separate class because the result behavior can be handled by ChordFinderSystem through identified chords and result messages.

## Object-Oriented Design Principles

ChordFinder uses encapsulation by grouping related data and behavior inside individual classes. Note contains note-related data and behavior, ChordFormula contains formula-related data and behavior, Chord contains chord result data, and ChordFinderSystem coordinates system-level behavior.

The design uses aggregation to show that ChordFinderSystem has a collection of ChordFormula objects and that Chord is made from three Note objects. The formulas are maintained by the system, while notes and chords are created during chord identification.

Association is used where objects interact without strong ownership. For example, ChordFormula is associated with Chord because a formula classifies a chord. ChordFinderSystem is associated with Note because it validates submitted notes during chord identification.

The design follows high cohesion because each class has a focused responsibility. Note handles note behavior, ChordFormula handles formula behavior, Chord handles chord result behavior, and ChordFinderSystem handles coordination.

The design also supports low coupling because each class interacts through clear method calls. The system can add or edit formulas without changing the Note or Chord classes.

## Installation

### Prerequisites

Before running the application, make sure the following software is installed:

- Java Development Kit (JDK) 17 or later
- Maven
- Git
- Eclipse, IntelliJ IDEA, VS Code, or another Java-compatible IDE

### Clone the Project

```bash
git clone https://github.com/theReal4m4d3u5/chordFinder.git
cd chordFinder