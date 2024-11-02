package sosGame449;

public abstract class SOSGame {
    protected int boardSize;
    protected char[][] board;
    protected boolean isPlayerOneTurn;

    public SOSGame(int boardSize) {
        if (boardSize < 3) {
            throw new IllegalArgumentException("Board size must be at least 3.");
        }
        this.boardSize = boardSize;
        this.board = new char[boardSize][boardSize];
        this.isPlayerOneTurn = true; // Initialize to Player One's turn
    }

    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }
    
    public void setPlayerOneTurn(boolean isPlayerOneTurn) {
        this.isPlayerOneTurn = isPlayerOneTurn;
    }

    public void switchPlayer() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char[][] getBoard() {
        return board;
    }
    
    protected boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
    
    protected boolean checkSOSInDirection(int row, int col, int dRow, int dCol) {
        if (board[row][col] != 'S') {
            return false;
        }

        int middleRow = row + dRow;
        int middleCol = col + dCol;
        int endRow = row + 2 * dRow;
        int endCol = col + 2 * dCol;

        if (isWithinBounds(middleRow, middleCol) && isWithinBounds(endRow, endCol)) {
            if (board[middleRow][middleCol] == 'O' && board[endRow][endCol] == 'S') {
                return true;
            }
        }

        return false;
    }

    public abstract boolean placeLetter(int row, int col, char letter);

    public abstract boolean checkForSOS(int row, int col, char letter);

    public abstract boolean isGameOver();
}
