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
        NetworkManager nm = new NetworkManager("localhost", 1337);
        boolean iGoFirst = nm.DoIGoFirst();
        int frankPlayer = (iGoFirst) ? 1 : 2;
        int oppPlayer = (iGoFirst) ? 2 : 1;
        TicTacToeGame game = new TicTacToeGame(5, frankPlayer);
        nm.networkThread.start();
        
        while (game.IsGameOver() == false){
            if (game.GetCurrentPlayer() == frankPlayer){
                int [] moveArr = game.MakeAIMove();
                nm.SendMove(moveArr[0], moveArr[1]);                
            }
            else{
                int oppMove = nm.GetOppMove(game.turn);
                game.PlayMove(oppPlayer, (oppMove / 5), (oppMove % 5));
            }
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
