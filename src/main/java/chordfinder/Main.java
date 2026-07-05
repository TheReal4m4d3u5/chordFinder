package chordfinder;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        ChordFinderSystem chordFinderSystem = new ChordFinderSystem();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Chord Finder System");
            System.out.println("-------------------");
            System.out.println("1. Chord Finder User");
            System.out.println("2. Administrator");
            System.out.println("3. Exit");
            System.out.print("Select role: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    runChordFinderUser(scanner, chordFinderSystem);
                    break;

                case "2":
                    runAdministratorLogin(scanner, chordFinderSystem);
                    break;

                case "3":
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }

        scanner.close();
    }

    private static void runChordFinderUser(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        boolean usingChordFinder = true;

        while (usingChordFinder) {
            System.out.println();
            System.out.println("Find Chord");
            System.out.println("----------");
            System.out.println("Enter exactly three notes separated by spaces.");
            System.out.println("Example: C E G");
            System.out.println("Type back to return to the main menu.");
            System.out.print("Enter notes: ");

            String submittedNotes = scanner.nextLine();

            if (submittedNotes.equalsIgnoreCase("back")) {
                usingChordFinder = false;
                continue;
            }

            try {
                List<Chord> chords = chordFinderSystem.identifyChord(submittedNotes);

                if (chords.isEmpty()) {
                    System.out.println("No matching chord was found.");
                } else {
                    System.out.println("Identified chord names:");
                    for (Chord chord : chords) {
                        System.out.println(chord.getChordName());
                    }
                }

            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void runAdministratorLogin(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.println();
        System.out.println("Administrator Login");
        System.out.println("-------------------");
        System.out.print("Enter administrator password (admin123): ");

        String password = scanner.nextLine();

        if (!password.equals(ADMIN_PASSWORD)) {
            System.out.println("Invalid administrator password.");
            return;
        }

        runAdministratorMenu(scanner, chordFinderSystem);
    }

    private static void runAdministratorMenu(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        boolean maintainingFormulas = true;

        while (maintainingFormulas) {
            System.out.println();
            System.out.println("Maintain Chord Formula");
            System.out.println("----------------------");
            System.out.println("1. Define Chord Formula");
            System.out.println("2. Edit Chord Formula");
            System.out.println("3. Delete Chord Formula");
            System.out.println("4. View Chord Formulas");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select action: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    defineChordFormula(scanner, chordFinderSystem);
                    break;

                case "2":
                    editChordFormula(scanner, chordFinderSystem);
                    break;

                case "3":
                    deleteChordFormula(scanner, chordFinderSystem);
                    break;

                case "4":
                    viewChordFormulas(chordFinderSystem);
                    break;

                case "5":
                    maintainingFormulas = false;
                    break;

                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
    }

    private static void defineChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.println();
        System.out.println("Define Chord Formula");
        System.out.println("--------------------");

        try {
            ChordFormula formula = readChordFormula(scanner);
            chordFinderSystem.addFormula(formula);
            System.out.println("Chord formula was defined.");

        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void editChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.println();
        System.out.println("Edit Chord Formula");
        System.out.println("------------------");

        System.out.print("Enter existing formula quality name to edit: ");
        String existingQualityName = scanner.nextLine();

        try {
            ChordFormula revisedFormula = readChordFormula(scanner);
            chordFinderSystem.editFormula(existingQualityName, revisedFormula);
            System.out.println("Chord formula was edited.");

        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void deleteChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.println();
        System.out.println("Delete Chord Formula");
        System.out.println("--------------------");

        System.out.print("Enter formula quality name to delete: ");
        String qualityName = scanner.nextLine();

        boolean deleted = chordFinderSystem.deleteFormula(qualityName);

        if (deleted) {
            System.out.println("Chord formula was deleted.");
        } else {
            System.out.println("Chord formula was not found.");
        }
    }

    private static void viewChordFormulas(ChordFinderSystem chordFinderSystem) {
        System.out.println();
        System.out.println("Current Chord Formulas");
        System.out.println("----------------------");

        for (ChordFormula formula : chordFinderSystem.getChordFormulas()) {
            System.out.println(
                    formula.getQualityName()
                            + " "
                            + formula.getDisplaySuffix()
                            + " rootToThird="
                            + formula.getRootToThird()
                            + " rootToFifth="
                            + formula.getRootToFifth()
            );
        }
    }

    private static ChordFormula readChordFormula(Scanner scanner) {
        System.out.print("Enter quality name: ");
        String qualityName = scanner.nextLine();

        System.out.print("Enter display suffix: ");
        String displaySuffix = scanner.nextLine();

        System.out.print("Enter root to third interval: ");
        int rootToThird = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter root to fifth interval: ");
        int rootToFifth = Integer.parseInt(scanner.nextLine());

        return new ChordFormula(qualityName, displaySuffix, rootToThird, rootToFifth);
    }
}