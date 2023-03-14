import java.util.Scanner;

public class TicTacToe {

    private static final int ROW = 3; // rows of board
    private static final int COL = 3; // columns of board
    private static String[][] board = new String[ROW][COL]; // board array

    public static void main(String[] args) {
        boolean playAgain = true; // ends game loop
        boolean loopRound = true; // ends round loop
        Scanner in = new Scanner(System.in); // input scanner
        String player; // player variable; only switches between "X" and "O"
        int numMovesX = 0; // counts number of moves made by X
        int numMovesO = 0; // counts number of moves made by O

        // stores move coords
        int xRow; // row of x move
        int xCol; // column of x move
        int oRow; // row of o move
        int oCol; // column of o move

        // game loop
        do {
            TicTacToe.clearBoard(); // clear board

            do { // round loop

                player = "X"; // set player to X
                TicTacToe.movePrompt(player); // start round + prompt for X move

                // get X move; asks user for a number 1-3, then subtracts one to convert it to an array index
                do {
                    xRow = SafeInput.getRangedInt(in, "Row", 1, 3) - 1;
                    xCol = SafeInput.getRangedInt(in, "Column", 1, 3) - 1;

                } while (!isValidMove(xRow, xCol)); // loops while the move is not allowed

                System.out.println(); // skips line

                // put X move in the board
                for (int i = 0; i < ROW; i++) {

                    // if it's the right row
                    if (i == xRow) {

                        // go through each column
                        for (int j = 0; j < COL; j++) {

                            // if it's the right column, set that space to X or O
                            if (j == xCol) {
                                board[i][j] = player;
                                numMovesX++; // add one to X move counter
                            }
                        }
                    }
                }

                // if X or O has made at least 3 moves, check for tie or X win
                if (numMovesX >= 3 || numMovesO >= 3) {

                    if (TicTacToe.isTie()) { // check for tie
                        loopRound = false;
                        break; // end round
                    }

                    if (TicTacToe.isWin(player)) {
                        loopRound = false;
                        break; // end round
                    }
                }

                player = "O"; // set the player to O
                TicTacToe.movePrompt(player); // display board

                // get O move; asks user for a number 1-3, then subtracts one to convert it to an array index
                do {
                    oRow = SafeInput.getRangedInt(in, "Row", 1, 3) - 1;
                    oCol = SafeInput.getRangedInt(in, "Column", 1, 3) - 1;

                } while (!isValidMove(oRow, oCol)); // loops while the move is not allowed

                System.out.println(); // skips line

                // put O move in the board
                for (int i = 0; i < ROW; i++) {

                    // if it's the right row
                    if (i == oRow) {

                        // go through each column
                        for (int j = 0; j < COL; j++) {

                            // if it's the right column, set that space to X or O
                            if (j == oCol) {
                                board[i][j] = player;
                                numMovesO++; // add one to O move counter
                            }
                        }
                    }
                }

                // if X or O has made at least 3 moves, check for tie or O win
                if ((numMovesX >= 3 || numMovesO >= 3) && numMovesX == numMovesO) {

                    if (TicTacToe.isTie()) { // check for tie
                        loopRound = false;
                        break; // end round
                    }

                    if (TicTacToe.isWin(player)) {
                        loopRound = false;
                        break; // end round
                    }
                }

                // loop round if no one won
            } while (loopRound);

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

        // top line
        System.out.print("\n   ");
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }

        // play grid
        for (int i = 0; i < ROW; i++) {

            // includes blank line above and below actual play spaces
            System.out.printf("\n   **%18s*%18s*%18s**", "", "", "");
            System.out.printf("\n   **%18s*%18s*%18s**", "", "", "");
            System.out.printf("\n   **%8s%s%9s*%8s%s%9s*%8s%s%9s**", "", board[i][0], "", "", board [i][1], "", "", board[i][2], "");
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
        System.out.printf("\n\n%25s%s\n\n", "", "Player " + player + "'s move");
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
        int counter; // counts how many times it found the player String (i.e. how many times it found an X or an O)

        // go through each row
        for (int i = 0; i < ROW; i++) {
            counter = 0; // reset the counter at the beginning of each row

            // go through each column
            for (int j = 0; j < COL; j++) {

                // if the board at that spot matches the player, add one to the counter
                if (board[i][j].equals(player)) {
                    counter++;
                }
            }

            // check if the counter is 3
            if (counter == 3) {
                return true; // if so, they got a row win
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
        boolean colWin = false;

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
                    colWin = true;
                    break;
                }
            }
        }
        return colWin;
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

        // all spaces filled
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {

                // go through each space; if that space is not empty, add 1 to the counter
                if (!board[i][j].equals(" ")) {
                    counter++;
                }
            }
        }

        // if there are 9 filled spaces, they tied. otherwise:
        if (counter != 9) {

            // check rows
            for (int i = 0; i < ROW; i++) {

                // if the first space is an X, check if either of the spaces ahead are O's
                if (board[i][0].equals("X")) {

                    if (!board[i][1].equals("O") && !board[i][2].equals("O")) {
                        return false; // if it fails any row check, return false
                    }

                    // if the first space is an O, check if either of the spaces ahead are X's
                } else if (board[i][0].equals("O")) {

                    if (!board[i][1].equals("X") && !board[i][2].equals("X")) {
                        return false; // if it fails any row check, return false
                    }
                }
            }

            // after it passes the row check, check the columns
            for (int j = 0; j < COL; j++) {

                // if first space is an X, check whether either of the spaces ahead are O's
                if (board[0][j].equals("X")) {

                    if (!board[1][j].equals("O") && !board[2][j].equals("O")) {
                        return false; // if it fails any column check, return false
                    }
                    // if the first space is an O, check if either of the spaces ahead are X's
                } else if (board[0][j].equals("O")) {

                    if (!board[1][j].equals("X") && !board[2][j].equals("X")) {
                        return false; // if it fails any column check, return false
                    }
                }
            }

            // after it passes both the row check and the column check, check diagonals.
            // if the diagonal from top left to bottom right has a combination of an X in any spot AND an O in any spot
            if ((board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X")) &&
                    (board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O"))) {

                // if the other diagonal has a possible move, there is no tie
                if (!((board[0][2].equals("X") || board[1][1].equals("X") || board[2][0].equals("X")) &&
                        (board[0][2].equals("O") || board[1][1].equals("O") || board[2][0].equals("O")))) {
                    return false;
                }
            } else {
                return false; // if diagonal 1 has no blocks, there is no tie
            }
        }

    display(); // display board
    System.out.printf("\n\n%28s%s", "", "You tied!"); // print tie message

    return true; // if it hasn't returned false yet, that means they tied
    }
}
