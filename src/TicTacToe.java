import java.util.Scanner;

public class TicTacToe {

    private static final int ROW = 3; // rows of board
    private static final int COL = 3; // columns of board
    private static String[][] board = new String[ROW][COL]; // board array

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); // input scanner
        String player; // player variable; only switches between "X" and "O"
        boolean playAgain = true; // ends game loop
        int totalGames = 0; // total number of games played
        int roundCounter; // round counter (for initiating win/tie check)
        int xScore = 0; // rounds won by x
        int oScore = 0; // rounds won by o

        // array that stores move coords; functions like regular (x, y) style coordinates.
        // index 0 is the row, index 1 is the column
        int[] moveCoords = new int[2];

        // game loop
        do {
            TicTacToe.clearBoard(); // clear board
            roundCounter = 0; // reset round counter
            totalGames++; // add one to game counter

            do { // round loop
                roundCounter++; // add one to round counter
                player = "X"; // set player to X
                TicTacToe.movePrompt(player); // start round + prompt for X move

                // get X move; asks user for a number 1-3, then subtracts one to convert it to an array index
                do {
                    moveCoords[0] = SafeInput.getRangedInt(in, "Row", 1, 3) - 1;
                    moveCoords[1] = SafeInput.getRangedInt(in, "Column", 1, 3) - 1;

                    if (!isValidMove(moveCoords[0], moveCoords[1])) {
                        // error message uses the values stored in moveCoords and adds one, so they make sense to the user
                        System.out.println("ERROR: Space at (Row " + (moveCoords[0] + 1) + ", Column " + (moveCoords[1] + 1) + ") is not available.");
                    }

                } while (!isValidMove(moveCoords[0], moveCoords[1])); // loops while the move is not allowed

                System.out.println(); // skips line
                placeMove(player, moveCoords); // puts1 X move in the board

                // if this is at least the 3rd round, check for a tie or win
                if (roundCounter >= 3) {

                    if (TicTacToe.isTie()) { // if they tied, displays game board & scoreboard
                        break; // end round
                    }

                    if (TicTacToe.isWin(player)) { // if they won, displays game board & scoreboard
                        xScore++; // +1 to player O's score
                        break; // end round
                    }
                }

                player = "O"; // set the player to O
                TicTacToe.movePrompt(player); // display board & move prompt

                // get O move; asks user for a number 1-3, then subtracts one to convert it to an array index
                do {
                    moveCoords[0] = SafeInput.getRangedInt(in, "Row", 1, 3) - 1;
                    moveCoords[1] = SafeInput.getRangedInt(in, "Column", 1, 3) - 1;

                    if (!isValidMove(moveCoords[0], moveCoords[1])) {
                        // error message uses the values stored in moveCoords and adds one, so they make sense to the user
                        System.out.println("ERROR: Space at (" + (moveCoords[0] + 1) + ", " + (moveCoords[1] + 1) + ") is not available.");
                    }

                } while (!isValidMove(moveCoords[0], moveCoords[1])); // loops while the move is not allowed

                System.out.println(); // skips line
                placeMove(player, moveCoords); // puts O move in the board

                // if this is at least the 3rd round, check for a tie or win
                if (roundCounter >= 3) {

                    if (TicTacToe.isTie()) { // if they tied, displays game board & scoreboard
                        break; // end round
                    }

                    if (TicTacToe.isWin(player)) { // if they won, displays game board & scoreboard
                        oScore++; // +1 to player O's score
                        break; // end round
                    }
                }

                // loop round if no one won or tied
            } while (!TicTacToe.isTie() && !TicTacToe.isWin("X") && !TicTacToe.isWin("O"));

            // show scoreboard
            System.out.printf("\n\n%24s══════════════════\n", " ");
            System.out.printf("%30sSCORE\n", "");
            System.out.printf("%24s══════════════════\n", " ");
            System.out.printf("%31sX: " + xScore + "\n", "");
            System.out.printf("%31sO: " + oScore + "\n", "");
            System.out.printf("%26sTOTAL GAMES: " + totalGames + "\n", " ");
            System.out.printf("%24s══════════════════\n", " ");

            // ask user if they want to play again
            if (!SafeInput.getYNConfirm(in, "\nPlay again?")) {
                playAgain = false;
            }
        } while (playAgain);
    }

    /**
     * Sets all board elements to a space
     */
    private static void clearBoard() {
        // for every row:
        for (int i = 0; i < ROW; i++) {

            // go to the space at index j and make it " "
            for (int j = 0; j < COL; j++) {
                board[i][j] = " ";
            }
        }
    }

    /**
     * Displays game board (does not include move prompt)
     */
    private static void display() {

        // header time :)
        SafeInput.prettyHeader("TIC TAC TOE");

        // column numbers
        System.out.printf("%13s1%18s2%18s3\n", "", "", "");

        // top line
        System.out.print("   ");
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }

        // play grid
        for (int i = 0; i < ROW; i++) {

            // includes blank line above and below actual play spaces
            System.out.printf("\n   **%18s*%18s*%18s**", "", "", "");
            System.out.printf("\n   **%18s*%18s*%18s**", "", "", "");
            System.out.printf("\n%d  **%8s%s%9s*%8s%s%9s*%8s%s%9s**", i + 1, "", board[i][0], "", "", board [i][1], "", "", board[i][2], "");
            System.out.printf("\n   **%18s*%18s*%18s**", "", "", "");
            System.out.printf("\n   **%18s*%18s*%18s**\n", "", "", "");

            // separator line
            System.out.print("   ");
            for (int k = 0; k < 60; k++) {
                System.out.print("*");
            }
        }
    }

    /**
     * Displays the board using the display() method, along with a prompt for the player's move
     *
     * @param player player whose turn it is
     */
    private static void movePrompt(String player) {
        TicTacToe.display();

        // move prompt
        System.out.printf("\n\n%23s═══════════════════\n", "");
        System.out.printf("%25s%s\n", "", "Player " + player + "'s move");
        System.out.printf("%23s═══════════════════\n", "");
    }

    /**
     * Checks if the player's desired move is valid
     *
     * @param row player's desired row
     * @param col player's desired column
     * @return true if the player can move at the specified row and column
     */
    private static boolean isValidMove(int row, int col) {

        // for each row in the board...
        for (int i = 0; i < ROW; i++) {

            // check if it's the right row
            if (i == row) {

                // if so, go through each space in the row (i.e. go through each column)
                for (int j = 0; j < COL; j++) {

                    // if it's the right column AND it's empty, return true
                    if (j == col && board[i][j].equals(" ")) {
                        return true;
                    }
                }
            }
        }
        return false; // otherwise return false
    }

    /**
     * Places the player's desired move onto the board
     *
     * @param player player making the move
     * @param coords space on the board to place the move
     */
    private static void placeMove(String player, int[] coords) {

        for (int i = 0; i < ROW; i++) {

            // if it's the right row
            if (i == coords[0]) {

                // go through each column
                for (int j = 0; j < COL; j++) {

                    // if it's the right row and column, set that space to X or O
                    if (j == coords[1]) {
                        board[i][j] = player;
                    }
                }
            }
        }
    }

    /**
     * Checks whether a specified player won.
     * If they did, displays the board along with a message saying they won.
     *
     * @param player player to check for win (X or O)
     * @return true if the player has won
     */
    private static boolean isWin(String player) {

        // run through every possible win for the player
        if (TicTacToe.isRowWin(player) || TicTacToe.isColWin(player) || TicTacToe.isDiagonalWin(player)){

            TicTacToe.display(); // display board
            System.out.printf("\n\n%26s%s", "", "Player " + player + " won!"); // print win message

            return true; // return true
        }
        return false; // otherwise return false
    }

    /** Checks whether a specified player got 3 in a row (as opposed to 3 in a column or 3 across a diagonal).
     *
     * @param player player to check for row win (X or O)
     * @return true if the player got a row win
     */
    private static boolean isRowWin(String player) {

        // go through each row
        for (int i = 0; i < ROW; i++) {

            // go through each column
            for (int j = 0; j < COL; j++) {

                // if the board at that spot matches the player, add one to the counter
                if (!board[i][j].equals(player)) {
                    break;
                }

                // if j = 2 (it's in the last space of the row) and it made it to this part, they won
                if (j == 2) {
                    return true;
                }
            }
        }
        return false; // if it didn't already return true, return false
    }

    /**
     * Checks whether a specified player got 3 in a column (as opposed to 3 in a row or 3 across a diagonal).
     *
     * @param player player to check for column win (X or O)
     * @return true if the player got a column win
     */
    private static boolean isColWin(String player) {

        // go through each column first
        for (int j = 0; j < COL; j++) {

            // check the space
            for (int i = 0; i < ROW; i++) {

                // as soon as it hits a space that does NOT have X or O (depends on player), break
                if (!board[i][j].equals(player)) {
                    break;
                }

                // if i = 2 (it's in the last space of the column) and it made it to this part, they won
                if (i == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a specified player got 3 across a diagonal (as opposed to 3 in a row or 3 in a column).
     *
     * @param player player to check for diagonal win (X or O)
     * @return true if the player got a diagonal win
     */
    private static boolean isDiagonalWin(String player) {
        boolean diagWin = false;

        // check if they have the middle space, then corner pairs
        if (board[1][1].equals(player)) {

            if (board[0][0].equals(player) && board [2][2].equals(player)) {
                diagWin = true; // if they got top left & bottom right, they win
            }

            if (board[0][2].equals(player) && board[2][0].equals(player)) {
                diagWin = true; // if they got top right & bottom left, they win
            }
        }
        return diagWin;
    }

    /** Checks whether the players have tied
     *
     * @return true if there are no possible wins on the current board
     */
    private static boolean isTie() {

        int counter = 0; // counts any board spaces that are not empty
        boolean foundX; // true if X is present within a win vector
        boolean foundO; // true if O is present within a win vector

        // check if all spaces are filled
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {

                // go through each space; if that space is not empty, add 1 to the counter
                if (!board[i][j].equals(" ")) {
                    counter++;
                }
            }
        }

        // if there are 9 filled spaces, they already tied so it just returns true.
        if (counter != 9) { // otherwise, it has to go through every win vector

            // check row win vectors
            for (int i = 0; i < ROW; i++) {
                foundX = false; // reset booleans
                foundO = false;

                // go through spaces
                for (int j = 0; j < COL; j++) {

                    // check if the spaces have an X and if they have an O
                    if (board[i][j].equals("X")) {
                        foundX = true; // found an X
                    } else if (board[i][j].equals("O")) {
                        foundO = true; // found an O
                    }
                }

                // at the end of each row, if the row does not have both an X and an O in it, break and return false
                if (!(foundX && foundO)) {
                    return false;
                }
            }

            // after it passes the row check, check each column win vector
            for (int j = 0; j < COL; j++) {
                foundX = false; // reset booleans
                foundO = false;

                // go through spaces
                for (int i = 0; i < ROW; i++) {

                    // check if the spaces have an X and if they have an O
                    if (board[i][j].equals("X")) {
                        foundX = true; // found an X
                    } else if (board[i][j].equals("O")) {
                        foundO = true; // found an O
                    }
                }

                // at the end of each column, if the column does not have both an X and an O in it, break and return false
                if (!(foundX && foundO)) {
                    return false;
                }
            }

            /*
            makes new arrays for the 2 different diagonals to make iteration simpler
            diag1 goes top left to bottom right, and diag2 goes top right to bottom left
            the arrays store the values currently in the board for each diagonal
            so each element in diag1 and diag2 will store some combination of "X", "O", and " "
             */
            String[] diag1 = new String[]{board[0][0], board[1][1], board[2][2]};
            String[] diag2 = new String[]{board[0][2], board[1][1], board[2][0]};

            foundX = false; // reset booleans
            foundO = false;

            // check diagonal 1
            // because of the way the diagonal arrays work, it just iterates through 3
            for (int i = 0; i < 3; i++) {

                // check if the spaces have an X and if they have an O
                if (diag1[i].equals("X")) {
                    foundX = true;
                } else if (diag1[i].equals("O")) {
                    foundO = true;
                }
            }

            // if both X and O are not present in diagonal 1, break & return false
            if (!(foundX && foundO)) {
                return false;
            }

            foundX = false; // reset booleans
            foundO = false;

            // check diagonal 2
            // because of the way the diagonal arrays work, it just iterates through 3
            for (int i = 0; i < 3; i++) {

                // check if the spaces have an X and if they have an O
                if (diag2[i].equals("X")) {
                    foundX = true;
                } else if (diag2[i].equals("O")) {
                    foundO = true;
                }
            }

            // if both X and O are not present in diagonal 2, break & return false
            if (!(foundX && foundO)) {
                return false;
            }
        }

        // if the tie check has made it this far, that means they did indeed tie.
        display(); // display board
        System.out.printf("\n\n%28s%s", "", "You tied!"); // print tie message
        return true;
    }
}
