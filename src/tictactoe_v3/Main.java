/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;
import static tictactoe_v3.TicTacToeGame.PLAYER_X;
import static tictactoe_v3.TicTacToeGame.PLAYER_O;

/**
 *
 * @author drose
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame(5, PLAYER_X);
        while (game.IsGameOver() == false){
            if (game.GetCurrentPlayer() == PLAYER_O)
                game.MakeAIMove();
        }

        System.out.println("Out of while loop");
        if(game.HasPlayerWon(PLAYER_X)){
            game.DisableBoard();
            game.SetBoardText("Player X Wins!");
        }else if (game.HasPlayerWon(PLAYER_O)){
            game.DisableBoard();
            game.SetBoardText("Player O Wins!");
        }else if (game.IsCatsGame()){
            game.DisableBoard();
            game.SetBoardText("Draw");
        }else{
            game.DisableBoard();
            game.SetBoardText("Good Job, you broke it");//Impossible case
        }
    }
    
}
