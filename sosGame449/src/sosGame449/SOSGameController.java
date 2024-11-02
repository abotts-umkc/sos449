package sosGame449;

public class SOSGameController {
    private SOSGame model;
    private SOSGameView view;

    public SOSGameController() {
    }

    public void setView(SOSGameView view) {
        this.view = view;
    }

    public void startGame() {
        try {
            String boardSizeStr = view.getBoardSizeInput();
            int boardSize = Integer.parseInt(boardSizeStr);

            if (boardSize < 3) {
                throw new NumberFormatException("Board size must be at least 3.");
            }

            boolean isSimpleGame = view.isSimpleGameSelected();

            if (isSimpleGame) {
                model = new SimpleSOSGame(boardSize);
            } else {
                model = new GeneralSOSGame(boardSize);
            }

            view.showGameScreen(boardSize, model);
            view.updateTurnLabel("Player 1's turn");

        } catch (NumberFormatException ex) {
            view.showErrorMessage("Invalid board size. Please enter a number greater than or equal to 3.");
        }
    }

    public void handleCellClick(int row, int col, char letter) {
        boolean isPlayerOneTurn = model.isPlayerOneTurn(); 

        System.out.println("handleCellClick called: Player " + (isPlayerOneTurn ? "1" : "2") + " is placing " + letter);

        if (model.placeLetter(row, col, letter)) {
            System.out.println("Letter " + letter + " placed successfully at (" + row + ", " + col + ")");
            view.updateBoard(isPlayerOneTurn, row, col, letter);

            if (model instanceof SimpleSOSGame) {
                SimpleSOSGame simpleGame = (SimpleSOSGame) model;
                if (simpleGame.isGameWon()) {
                    String winner = simpleGame.getWinner();
                    view.showWinner(winner + " wins!");
                    System.out.println(winner + " wins!");
                    
                    view.resetToInitialSetup();
                    return; 
                }
            }

            view.updateTurnLabel("Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");
            System.out.println("Updated turn label: Player " + (model.isPlayerOneTurn() ? "1" : "2") + "'s turn");

            if (model.isGameOver()) {
                String message;
                if (model instanceof GeneralSOSGame) {
                    GeneralSOSGame generalGame = (GeneralSOSGame) model;
                    int player1Score = generalGame.getPlayer1Score();
                    int player2Score = generalGame.getPlayer2Score();
                    if (player1Score > player2Score) {
                        message = "Game over! Player 1 wins!";
                    } else if (player2Score > player1Score) {
                        message = "Game over! Player 2 wins!";
                    } else {
                        message = "Game over! It's a tie!";
                    }
                } else if (model instanceof SimpleSOSGame) {
                    message = "Game over! It's a draw!";
                } else {
                    message = "Game over!";
                }
                view.showWinner(message);
                
                view.resetToInitialSetup();
            }
        } else {
            view.showErrorMessage("Invalid move. The cell is already occupied.");
        }
    }
}

