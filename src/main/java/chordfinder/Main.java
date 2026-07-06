package chordfinder;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String ADMIN_PASSWORD = "admin";

    public static void main(String[] args) {
        ChordFinderSystem chordFinderSystem = new ChordFinderSystem();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println();
                System.out.println("Chord Finder");
                System.out.println("1. Find Chord");
                System.out.println("2. Administrator Login");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        runChordFinderUser(scanner, chordFinderSystem);
                        break;
                    case "2":
                        runAdministratorLogin(scanner, chordFinderSystem);
                        break;
                    case "3":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
        }
    }

    public static void runChordFinderUser(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.print("Enter three notes separated by spaces: ");
        String submittedNoteText = scanner.nextLine();

        chordFinderSystem.identifyChord(submittedNoteText);
        chordFinderSystem.displayResult();
    }

    public static void runAdministratorLogin(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.print("Enter administrator password: ");
        String password = scanner.nextLine();

        if (ADMIN_PASSWORD.equals(password)) {
            runAdministratorMenu(scanner, chordFinderSystem);
        } else {
            System.out.println("Invalid password.");
        }
    }

    public static void runAdministratorMenu(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Maintain Chord Formula");
            System.out.println("1. Define Chord Formula");
            System.out.println("2. Edit Chord Formula");
            System.out.println("3. Delete Chord Formula");
            System.out.println("4. View Chord Formulas");
            System.out.println("5. Return");
            System.out.print("Choose option: ");

            String option = scanner.nextLine();

            switch (option) {
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public static void defineChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        ChordFormula formula = readChordFormula(scanner);
        chordFinderSystem.defineChordFormula(formula);
        chordFinderSystem.displayResult();
    }

    public static void editChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.print("Enter formula quality name to edit: ");
        String qualityName = scanner.nextLine();

        ChordFormula revisedFormula = readChordFormula(scanner);
        chordFinderSystem.editChordFormula(qualityName, revisedFormula);
        chordFinderSystem.displayResult();
    }

    public static void deleteChordFormula(Scanner scanner, ChordFinderSystem chordFinderSystem) {
        System.out.print("Enter formula quality name to delete: ");
        String qualityName = scanner.nextLine();

        chordFinderSystem.deleteChordFormula(qualityName);
        chordFinderSystem.displayResult();
    }

    public static void viewChordFormulas(ChordFinderSystem chordFinderSystem) {
        List<ChordFormula> formulas = chordFinderSystem.getChordFormulas();

        for (ChordFormula formula : formulas) {
            System.out.println(
                formula.getQualityName()
                    + " "
                    + formula.getDisplaySuffix()
                    + " third="
                    + formula.getRootToThird()
                    + " fifth="
                    + formula.getRootToFifth()
            );
        }
    }

    public static ChordFormula readChordFormula(Scanner scanner) {
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
