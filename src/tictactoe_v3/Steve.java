/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;
import static tictactoe_v3.TicTacToeGame.NO_PLAYER;
/**
 *
 * @author drose
 */
public class Steve extends AIPlayer{
    
    public Steve(TicTacToeGame game, int player){
        super(game, player);
    }
    /** Get next best move for computer. Return int[2] of {row, col} */
    @Override
    int[] GetMove() {
        int [][] boardState = game.GetBoardState();
        int [] move = new int[] {this.myPlayer, -1, -1};
        boolean played = false;
        for (int row=0; row<this.game.GAME_SIZE; row++){
            for (int col=0; col<game.GAME_SIZE; col++){
                if (played == false && boardState[row][col] == NO_PLAYER){
                    move[1] = row; move[2] = col;
                    played = true;
                }
            }
        }
        return move;
    }
}
