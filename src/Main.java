import java.util.Scanner;

public class MancalaGame {
    public static void main(String[] args) {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Initialize the Mancala board
        int[] board = {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0}; // Mancala board with 14 pits

        int currentPlayer = 1;  // Player 1 starts

        while (true) {
            displayBoard(board); // Display the current board state
            int move = getMove(currentPlayer); // Get the pit to move from

            makeMove(board, move, currentPlayer); // Make the selected move

            if (isGameOver(board)) {
                displayBoard(board); // Display the final board state
                announceWinner(board); // Announce the winner or a tie
                break;
            }

            currentPlayer = 3 - currentPlayer; // Switch players (1 -> 2, 2 -> 1)
        }

        // Close the scanner
        scanner.close();
    }

    // Display the current board state
    public static void displayBoard(int[] board) {
        // Display the pits for player 2 (top side)
        System.out.println("Player 2 (top)\n");
        for (int i = 12; i >= 7; i--) {
            System.out.print("[" + board[i] + "] ");
        }
        System.out.println("\n[" + board[13] + "]\t\t\t[" + board[6] + "]");
        // Display the pits for player 1 (bottom side)
        for (int i = 0; i <= 5; i++) {
            System.out.print("[" + board[i] + "] ");
        }
        System.out.println("\nPlayer 1 (bottom)\n");
    }

    // Get the pit to move from
    public static int getMove(int currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int pit;
        while (true) {
            System.out.print("Player " + currentPlayer + ", choose a pit (1-6): ");
            pit = scanner.nextInt();
            if (pit >= 1 && pit <= 6) {
                int pitIndex = 6 + (currentPlayer == 1 ? pit - 1 : 12 - pit + 1);
                if (pitIndex >= 0 && pitIndex <= 12 && pitIndex != 6 && pitIndex != 13 && board[pitIndex] != 0) {
                    return pitIndex;
                }
            }
            System.out.println("Invalid pit selection. Try again.");
        }
    }

    // Make the selected move
    public static void makeMove(int[] board, int pitIndex, int currentPlayer) {
        int stones = board[pitIndex];
        board[pitIndex] = 0;
        while (stones > 0) {
            pitIndex = (pitIndex + 1) % 14;
            if ((currentPlayer == 1 && pitIndex == 13) || (currentPlayer == 2 && pitIndex == 6)) {
                pitIndex = (pitIndex + 1) % 14;
            }
            board[pitIndex]++;
            stones--;
        }
        captureStones(board, pitIndex, currentPlayer); // Check for capturing stones
    }

    // Check if the game is over
    public static boolean isGameOver(int[] board) {
        for (int i = 0; i <= 5; i++) {
            if (board[i] != 0) {
                return false;
            }
        }
        for (int i = 7; i <= 12; i++) {
            if (board[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // Announce the winner or a tie
    public static void announceWinner(int[] board) {
        int player1Score = board[6];
        int player2Score = board[13];

        if (player1Score > player2Score) {
            System.out.println("Player 1 wins with " + player1Score + " points!");
        } else if (player2Score > player1Score) {
            System.out.println("Player 2 wins with " + player2Score + " points!");
        } else {
            System.out.println("It's a tie! Both players have " + player1Score + " points!");
        }
    }
}
