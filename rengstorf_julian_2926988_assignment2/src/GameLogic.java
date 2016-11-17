import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameLogic {

	private int[][] winners; // winners of the XOBoards
	private int[][] activeBoards; // active state of the XOBoards
	private int current_player;

	/**
	 * immer wenn 1 piece gerendert
	 * 
	 * UltimateBoard --> GameLogic --> XOBoard
	 * 
	 */
	public GameLogic() {
		winners = new int[3][3];
		activeBoards = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
				activeBoards[i][j] = ACTIVE;
			}
		 current_player = XPIECE;
	}

	// detects winner of a tile and updates
	public boolean detectWinner(int[][] board, int x, int y) {
		if (examineBoard(board, x, y, current_player)) {
			displayWinner(current_player, "Board");
			return true;
		} else
			return false;
	}

	public boolean detectOverallWinner(int x, int y, int possibleWinner) {
		if (examineBoard(winners, x, y, possibleWinner)) {
			displayWinner(possibleWinner, "Game");
			return true;
		} else
			return false;
	}
	
/** Examine a board to find out if player has won
 * @param board to be examined (XOUltimateBoard/XOBoard)
 * @param x coordinate of field that was set
 * @param y coordinate of field that was set
 * @param player who might win
 * @return true if player has won the board
 */
	// source: http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
	public boolean examineBoard(int[][] board, int x, int y, int player) {

		// check col
		for (int i = 0; i < 3; i++) {
			if (board[x][i] != player)
				break;
			if (i == 2) {
				return true;
			}
		}

		// check row
		for (int i = 0; i < 3; i++) {
			if (board[i][y] != player)
				break;
			if (i == 2) {
				return true;
			}
		}

		// check diag
		if (x == y) {
			// we're on a diagonal
			for (int i = 0; i < 3; i++) {
				if (board[i][i] != player)
					break;
				if (i == 2) {
					return true;
				}
			}
		}

		// check anti diag (thanks rampion)
		if (x + y == 2) {
			for (int i = 0; i < 3; i++) {
				if (board[i][2 - i] != player)
					break;
				if (i == 2) {
					return true;
				}
			}
		}

		return false;

	}

	public void displayWinner(int winner, String winType) {
		String playername = "NO NAME";
		if (winner == XPIECE)
			playername = "Red (X)";
		if (winner == OPIECE)
			playername = "Green (O)";
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(winType + " Winner");
		alert.setHeaderText("You won the " + winType);
		alert.setContentText(
				"Hello Player " + playername + "!\n " + "I have good news for you! You won the " + winType);

		alert.showAndWait();
	}

	// constants for the class
	private final int INACTIVE = 0;
	private final int ACTIVE = 1;

	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;

	public void setActive(int boardx, int boardy) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				activeBoards[i][j] = INACTIVE;
			}
		activeBoards[boardx][boardy] = ACTIVE;
	}

	private void setAllActiveExcept(int boardx, int boardy) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				activeBoards[i][j] = ACTIVE;
			}
		activeBoards[boardx][boardy] = INACTIVE;
	}

	public void resetWinners() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
			}
	}

	public boolean checkBoard(int x, int y) {
		if (winners[x][y] != EMPTY) setAllActiveExcept(x, y);
		if (activeBoards[x][y] == ACTIVE) return true; 
		else return false;
		
		// return ((winners[x][y] == EMPTY) && (activeBoards[x][y] == ACTIVE))
		
	}

	public void updateBoardWinners(int x, int y) {
		this.winners[x][y] = current_player;
	}

	public void resetActive() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				activeBoards[i][j] = ACTIVE;
			}
	}

	public int getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}

}
