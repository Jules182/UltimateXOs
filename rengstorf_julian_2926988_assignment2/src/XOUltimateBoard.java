
// an implementation of the XO board and the game logic
// imports necessary for this class
import javafx.scene.layout.Pane;

// class definition for drawing a game board
class XOUltimateBoard extends Pane {

	// constructor for the class
	public XOUltimateBoard() {
		game = new GameLogic();
		// initialise the boards
		boards = new XOBoard[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				boards[i][j] = new XOBoard(game); // render the XO Boards
				getChildren().add(boards[i][j]);
			}
	}

	// we have to override resizing behaviour to make our view appear properly
	@Override
	public void resize(double width, double height) {
		// call the superclass method first
		super.resize(width, height);
		// figure out the width and height of a cell
		cell_width = width / 3.0;
		cell_height = height / 3.0;

		// we need to reset the sizes and positions of all XOPieces that were placed
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boards[i][j].relocate(i * cell_width, j * cell_height);
				boards[i][j].resize(cell_width, cell_height);
			}
		}
	}

	// public method for resetting the game
	public void resetGame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game.resetActive();
				game.resetWinners();
				game.setCurrent_player(-1);
				repaintFields();
				this.getChildren().remove(boards[i][j]);
				boards[i][j] = new XOBoard(game);
				getChildren().add(boards[i][j]);
			}
		}
	}

	// public method that tries to place a piece
	public void placePiece(final double x, final double y) {
		// translate the x, y coordinates into cell indexes
		int indexx = (int) (x / cell_width);
		int indexy = (int) (y / cell_height);
		// save current player, cause it will be switched in placePiece
		int possibleWinner = game.getCurrent_player();
		// translate height and width values and pass to
		// place piece in the correct XOBoard in the right place
		if (game.checkBoard(indexx, indexy)) // check if board is active
			boards[indexx][indexy].placePiece(x, y, indexx, indexy);
		repaintFields();
		if (game.detectOverallWinner(indexx, indexy, possibleWinner))
			resetGame();
	}
	/**	display active field in green/red depending on current player */
	public void repaintFields() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (game.checkBoard(i, j)) {
					boards[i][j].repaint(game.getCurrent_player());
				} else {
					boards[i][j].repaint(-1);
				}
			}
		}
	}

	// private fields of the class
	private GameLogic game;
	private XOBoard[][] boards; // array that holds all the render pieces
	private double cell_width, cell_height; // width and height of a cell
}