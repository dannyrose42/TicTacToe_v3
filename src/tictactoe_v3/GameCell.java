package tictactoe_v3;

/**
 *
 * @author drose
 */
import javax.swing.JButton;
import static tictactoe_v3.TicTacToeGame.NO_PLAYER;
import static tictactoe_v3.TicTacToeGame.PLAYER_X;
import static tictactoe_v3.TicTacToeGame.PLAYER_O;



/**
 *
 * @author drose
 */
public class GameCell extends JButton {
    public int row, col;
    public TicTacToeGame game;
    private int player;
    
    //Default constructor: builds gameCell with player = NO_PLAYER
    public GameCell(TicTacToeGame game, int r, int c){
        this.game = game;
        row = r;
        col = c;
        this.player = NO_PLAYER;
    }
    //Predefined Player Constructor: Builds cell with predefined player owner
    public GameCell(TicTacToeGame game, int r, int c, int player){
        this.game = game;
        row = r;
        col = c;
        this.player = player;
        //if (player != game.)
        this.setEnabled(false);
        this.setText(game.GetPlayerString(player));
    }
    public int GetPlayer(){
        return this.player;
    }
    public void SetPlayer(int player){
        if (this.player == NO_PLAYER){
            switch (player){
            case PLAYER_X:
                this.setText("X");
                this.setEnabled(false);
                this.player = PLAYER_X;
                break; 
            case PLAYER_O:
                this.setText("O");
                this.setEnabled(false);
                this.player = PLAYER_O;
                break;
            }
        }else
            System.out.println("Cell already taken.");
    }
}
