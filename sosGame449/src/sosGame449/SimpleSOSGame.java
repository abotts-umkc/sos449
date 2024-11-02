package sosGame449;

public class SimpleSOSGame extends SOSGame {

    private boolean gameWon = false;
    private boolean isPlayerOneWinner = false;

    public SimpleSOSGame(int boardSize) {
        super(boardSize);
    }

    @Override
    public boolean placeLetter(int row, int col, char letter) {
        if (board[row][col] == '\0') {
            board[row][col] = letter;
            System.out.println("Placed letter " + letter + " at (" + row + ", " + col + ")");

            if (checkForSOS(row, col, letter)) {
                gameWon = true; 
                isPlayerOneWinner = isPlayerOneTurn; 
            } else {
                switchPlayer(); 
            }
            return true; 
        } else {
            return false; 
        }
    }

    @Override
    public boolean checkForSOS(int row, int col, char letter) {
        return checkAllDirectionsForSOS(row, col);
    }

    @Override
    public boolean isGameOver() {
        return gameWon || isBoardFull();
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public String getWinner() {
        if (gameWon) {
            return isPlayerOneWinner ? "Player 1" : "Player 2";
        }
        return null; // No winner yet
    }

    private boolean isBoardFull() {
        for (char[] rowArray : board) {
            for (char cell : rowArray) {
                if (cell == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkAllDirectionsForSOS(int row, int col) {
        int[][] directions = {
            {-1,  0}, // Up
            {-1,  1}, // Up Right
            { 0,  1}, // Right
            { 1,  1}, // Down Right
            { 1,  0}, // Down
            { 1, -1}, // Down Left
            { 0, -1}, // Left
            {-1, -1}  // Up Left
        };

        for (int[] dir : directions) {
            if (checkSOSInDirection(row, col, dir[0], dir[1])) {
                return true;
            }
        }

        return false;
    }
}

