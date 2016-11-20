import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameLogic {

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

	/** detects winner of a board and updates winners array */
	public boolean detectWinner(int[][] board, int pieceX, int pieceY, int boardX, int boardY) {
		if (examineBoard(board, pieceX, pieceY, current_player)) {
			displayWinner(current_player, "Board");
			this.winners[boardX][boardY] = current_player;
			return true;
		}
		return false;
	}

	/**
	 * detects winner of the whole game by examining the winners array
	 * 
	 * @param boardX
	 *            coordinate of the board that was recently set into
	 * @param boardY
	 *            coordinate of the board that was recently set into
	 * @param possibleWinner
	 *            not the current player but the other one
	 * @return true --> call resetBoard()
	 */
	public boolean detectOverallWinner(int boardX, int boardY, int possibleWinner) {
		if (examineBoard(this.winners, boardX, boardY, possibleWinner)) {
			displayWinner(possibleWinner, "Game");
			return true;
		} else
			return false;
	}

	/** checks the active state of a board to determine of a piece can be placed into it */
	public boolean checkBoard(int boardX, int boardY) {
		return (activeBoards[boardX][boardY] == ACTIVE);
	}

	/** method to toggle the ACTIVE/INACTIVE state of a board while checking if won */
	public void setActiveStates(int boardX, int boardY) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				activeBoards[i][j] = INACTIVE;
			}

		if (winners[boardX][boardY] == EMPTY) {
			activeBoards[boardX][boardY] = ACTIVE;
		} else {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (winners[i][j] != EMPTY)
						activeBoards[i][j] = INACTIVE;
					else
						activeBoards[i][j] = ACTIVE;
				}
		}
	}

	/** resets winners and activeBoards arrays */
	public void reset() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
				activeBoards[i][j] = ACTIVE;
			}
	}

	/**
	 * Examine a board to find out if player has won; source:
	 * http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
	 *
	 * @param board
	 *            to be examined (XOUltimateBoard/XOBoard)
	 * @param x
	 *            coordinate of field that was set
	 * @param y
	 *            coordinate of field that was set
	 * @param player
	 *            who might win
	 * @return true if player has won the board
	 */
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

	/** displays an alert if a board or the game has been won */
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

	// fields
	private int[][] winners; // winners of the XOBoards
	private int[][] activeBoards; // active state of the XOBoards
	private int current_player;

	public int getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}
}