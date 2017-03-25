package tictactoe_v3;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author drose
 */
public class GameBoard extends JFrame {
    public static JTextField textField;
    public TicTacToeGame game;
    public GameCell[][] cells;

    //Defauly constructor: creates game with blank board
    public GameBoard(TicTacToeGame game, int firstPlayer){
        //Set local attributes
        this.game = game;
        cells = new GameCell[game.GAME_SIZE][game.GAME_SIZE];
        textField = new JTextField("Player " + game.GetPlayerString(firstPlayer) + " goes frst.");
        
        //Build and assemble swint components
        Container pane = getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.GAME_SIZE, game.GAME_SIZE));        
        pane.add(textField, "North");
        pane.add(panel, "Center");
        
        //Instantiate CellObserver
        CellObserver observer = new CellObserver(this.game);
              
        //Build cells and add them to the board along with the CellObserver
        for (int row=0; row<game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                cells[row][col] = new GameCell(game, row, col);
                cells[row][col].addActionListener(observer);
                panel.add(cells[row][col]);               
            }
        }
    }
    //Initial state constructor: creates game with board in predefined state passed in as "state" param
    public GameBoard(TicTacToeGame game, int firstPlayer,int [][] state){
        //Set local attributes
        this.game = game;
        cells = new GameCell[game.GAME_SIZE][game.GAME_SIZE];
        textField = new JTextField("Player " + game.GetPlayerString(firstPlayer) + " goes frst.");
        
        //Build and assemble swint components
        Container pane = getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.GAME_SIZE, game.GAME_SIZE));        
        pane.add(textField, "North");
        pane.add(panel, "Center");
        
        //Instantiate CellObserver
        CellObserver observer = new CellObserver(this.game);
        
        for (int row=0; row<game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                cells[row][col] = new GameCell(game, row, col, state[row][col]);
                cells[row][col].addActionListener(observer);
                if((cells[row][col].GetPlayer() != 0) && (cells[row][col].GetPlayer() ==  state[row][col])){
                    cells[row][col].SetPlayer(state[row][col]);
                }
                panel.add(cells[row][col]);               
            }
        }
    }
    //Sets text box seen above baord
    public void SetBoardText(String text){
        textField.setText(text);
    }
    //Disables all board buttons
    public void DisableBoard(){
        System.out.println("Disable Buttons called");
        for (int row=0; row<game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                cells[row][col].setEnabled(false);
            }
        }
    }
    //Gets the current state of the board as a 2D int array
    public int[][] GetState(){
        int [][] state = new int[game.GAME_SIZE][game.GAME_SIZE];
        for (int row=0; row<game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                state[row][col] = cells[row][col].GetPlayer();               
            }
        }
        return state;
    }        
}
