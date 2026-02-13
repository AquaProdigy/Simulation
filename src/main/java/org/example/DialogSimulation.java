package org.example;

import java.util.Scanner;

public class DialogSimulation {
    private static final String EXIT = "e";
    private static final String PAUSE = "p";
    private static final String NEXT_TURN = "n";
    private static final String START_SIMULATION = "s";
    private static final String FINISH_SIMULATION = "f";
    private static final String CONTINUE_SIMULATION = "c";

    private final Scanner scanner = new Scanner(System.in);
    private final Simulation simulation;

    public DialogSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void run() {
        printMenu();

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            selectAction(input);
        }
    }

    private void printMenu(){
        String menu = "Make a move - '%s'\n".formatted(NEXT_TURN) +
                "Start simulation - '%s'\n".formatted(START_SIMULATION) +
                "Pause simulation - '%s'\n".formatted(PAUSE) +
                "Continue simulation - '%s'\n".formatted(CONTINUE_SIMULATION) +
                "Finish simulation - '%s'\n".formatted(FINISH_SIMULATION) +
                "Exit - '%s'\n\n".formatted(EXIT) +
                "Moves made - %d\n".formatted(simulation.getSteps())
                ;

        System.out.println(menu);
    }

    private void selectAction(String action) {
        switch (action) {
            case PAUSE -> {
                simulation.pauseSimulation();
                System.out.println("Simulation Paused\n");
                printMenu();
            }
            case NEXT_TURN -> {
                simulation.nextTurn();
                System.out.println("Next turn selected\n");
                printMenu();
            }
            case FINISH_SIMULATION -> {
                simulation.finishSimulation();
                System.out.println("Simulation finished\n");
                printMenu();
            }

            case START_SIMULATION -> new Thread(simulation::startSimulation).start();
            case CONTINUE_SIMULATION -> simulation.continueSimulation();
            case EXIT -> System.exit(0);
            default -> System.out.println("Invalid action");
        }
    }
}
