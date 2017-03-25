package tictactoe_v3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author drose
 */
class CellObserver implements ActionListener {
    public TicTacToeGame game;
    public CellObserver(TicTacToeGame game){
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        for (int row=0; row<game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                if(source == game.GetCell(row, col)){
                    game.PlayMove(1, row, col);
                }   
            }
        }
    }
}