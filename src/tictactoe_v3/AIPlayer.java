package tictactoe_v3;
import static tictactoe_v3.TicTacToeGame.PLAYER_X;
import static tictactoe_v3.TicTacToeGame.PLAYER_O;

public abstract class AIPlayer {
    public TicTacToeGame game;
    protected  int myPlayer, oppPlayer;
    /** Constructor with reference to game board */
    public AIPlayer(TicTacToeGame game, int player) {
        this.game = game;
        this.myPlayer = player;
        this.oppPlayer = (myPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;

    }
    /** Abstract method to get next move. Return int[2] of {row, col} */
    abstract int[] GetMove();  // to be implemented by subclasses
}