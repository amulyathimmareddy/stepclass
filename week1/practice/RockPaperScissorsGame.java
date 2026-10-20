package week1.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsGame {

    // Helper class to store details of each round
    static class RoundRecord {
        int roundNumber;
        String playerMove;
        String computerMove;
        String result;

        RoundRecord(int roundNumber, String playerMove, String computerMove, String result) {
            this.roundNumber = roundNumber;
            this.playerMove = playerMove;
            this.computerMove = computerMove;
            this.result = result;
        }
    }

    /**
     * Determines the outcome of a round using standard Rock-Paper-Scissors rules.
     */
    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }

        switch (playerMove.toLowerCase()) {
            case "rock":
                return computerMove.equalsIgnoreCase("scissors") ? "Player Wins" : "Computer Wins";
            case "paper":
                return computerMove.equalsIgnoreCase("rock") ? "Player Wins" : "Computer Wins";
            case "scissors":
                return computerMove.equalsIgnoreCase("paper") ? "Player Wins" : "Computer Wins";
            default:
                return "Invalid Move";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] moves = {"Rock", "Paper", "Scissors"};

        int totalRounds = 5;
        int wins = 0, losses = 0, draws = 0;
        List<RoundRecord> history = new ArrayList<>();

        System.out.println("=== Welcome to the Rock-Paper-Scissors Arcade ===");

        for (int i = 1; i <= totalRounds; i++) {
            System.out.print("\nRound " + i + " - Enter your move (Rock, Paper, Scissors): ");
            String playerMove = scanner.nextLine().trim();

            // Validate user input
            while (!playerMove.equalsIgnoreCase("Rock") &&
                   !playerMove.equalsIgnoreCase("Paper") &&
                   !playerMove.equalsIgnoreCase("Scissors")) {
                System.out.print("Invalid choice! Please enter Rock, Paper, or Scissors: ");
                playerMove = scanner.nextLine().trim();
            }

            // Capitalize properly
            playerMove = playerMove.substring(0, 1).toUpperCase() + playerMove.substring(1).toLowerCase();

            // Random computer move
            String computerMove = moves[random.nextInt(moves.length)];

            // Evaluate result
            String result = playRound(playerMove, computerMove);
            System.out.println("Computer chose: " + computerMove + " => " + result);

            // Update stats
            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            history.add(new RoundRecord(i, playerMove, computerMove, result));
        }

        // Print Summary Table
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("%-8s | %-12s | %-14s | %-15s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("--------------------------------------------------------------");
        for (RoundRecord record : history) {
            System.out.printf("%-8d | %-12s | %-14s | %-15s%n",
                    record.roundNumber, record.playerMove, record.computerMove, record.result);
        }
        System.out.println("--------------------------------------------------------------");

        // Print Statistics
        double winPercentage = ((double) wins / totalRounds) * 100.0;
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPercentage);

        scanner.close();
    }
}