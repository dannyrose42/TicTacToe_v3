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
public class GameController implements Runnable{
    private TicTacToeGame game;
    private NetworkManager nm;
    
    private int gameMode;
    private String host;
    private int port;
    
    public GameController(){
        game = new TicTacToeGame(5, PLAYER_O);
        gameMode = 1;
        nm = new NetworkManager("127.0.0.1", 9001);
        System.out.println("NM UP");
    }
    
    @Override
    public void run() {
        boolean first = false;
        int firstPlayer = -1;
        System.out.println("In");
        
        
        
        if (nm.IsConnected())first = nm.GetFirstPlayer();
        String text = (first) ? "First" : "Second";
        game.SetBoardText(text);
        System.out.println("Out");
        
        if (gameMode == 1){
            PlayStandAloneGame();
        }
        else{
            PlayNetworkGame();
        }
    }
    public void PlayStandAloneGame(){
        try{
            while (game.IsGameOver() == false){
                if (game.GetCurrentPlayer() == PLAYER_O){
                    int [] move = game.MakeAIMove();
                }
            }
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
        }catch (Exception e){}
    }
    public void PlayNetworkGame(){
        try{
            while (game.IsGameOver() == false){
                if (game.GetCurrentPlayer() == PLAYER_O){
                    int [] move = game.MakeAIMove();
                }
            }
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
        }catch (Exception e){}
    }
    public void SetGameMode(int mode){
        gameMode = mode;
    }
}
