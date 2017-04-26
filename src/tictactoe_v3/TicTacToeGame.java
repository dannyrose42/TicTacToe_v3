package tictactoe_v3;

import javax.swing.JFrame;

/**
 *
 * @author drose
 */
public class TicTacToeGame {
    public final static int NO_PLAYER = 0;
    public final static int PLAYER_X = 1;
    public final static int PLAYER_O = 2;
    public final int GAME_SIZE;
    public int turn;
    //public int [][] board; 
    
    private Max max;
    private GameBoard board;
    private Steve steve;
    private StartMenu startMenu;
    
    
    private final long[]winningPatterns = {
        0b1_11110000_00000000_00000000,
        0b0_00001111_10000000_00000000,
        0b0_00000000_01111100_00000000,
        0b0_00000000_00000011_11100000,
        0b0_00000000_00000000_00011111,//rows

        0b0_00010000_10000100_00100001,
        0b0_00100001_00001000_01000010,
        0b0_01000010_00010000_10000100,
        0b0_10000100_00100001_00001000,
        0b1_00001000_01000010_00010000,//cols
        
        0b1_00000100_00010000_01000001,
        0b0_00010001_00010001_00010000//diagonals 
    };
    private int currentPlayer;
    
    
    TicTacToeGame(int size){
        this.turn = 0;
        this.GAME_SIZE = size;        
        this.currentPlayer = PLAYER_X;
        this.board = new GameBoard(this, PLAYER_X);
        //Initilize empty board
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
               board.cells[row][col].SetPlayer(NO_PLAYER);
            }
        }
        board.setSize(300, 300);
        board.setTitle("Tic Tac Toe");
        board.setLocation(200, 200);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        board.setVisible(true);    
    }
    public void SetAIPlayer(int player){
        if(max == null){
            max = new Max(this, player);
        }else{
            System.out.println("AI Playera already set");
        }
    }
    public void DisplayBoard(){
        board.setVisible(true);
    }
    
    //Action Methods
    public void PlayMove(int player, int row, int col){
        board.cells[row][col].SetPlayer(player);
        ToggleTurn();
    }
    public int [] MakeAIMove(){
        int [] move = max.GetMove();
        PlayMove(max.myPlayer, move[0], move[1]);
        return move;
    }
    public void ToggleTurn(){
        currentPlayer =  (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
        turn++;
    }
    public void DisableBoard(){
        board.DisableBoard();
    }
    public void SetBoardText(String text){
        board.SetBoardText(text);
    }
    //Data Access methods
    public int GetCurrentPlayer(){
        return this.currentPlayer;
    }    
    public String GetPlayerString(int player){
        String playerString = "";
        switch (player){
            case PLAYER_X:
                playerString = "X";
                break;
            case PLAYER_O:
                playerString = "O";
                break;
            case NO_PLAYER:
                break;
        }
        return playerString; 
    }
    public GameCell GetCell(int row, int col){
        return board.cells[row][col];
    }
    public int[][] GetBoardState(){
        return board.GetState();
    }
    
    
    
    //Game End Detection Methods
    public boolean IsGameOver(){
        boolean result = false;
        if (IsCatsGame() || HasPlayerWon(PLAYER_X) || HasPlayerWon(PLAYER_O))
            result = true;
        return result; 
    }
    public void PrintBoard(){
        for (int row = 0; row < GAME_SIZE; row++) {
            for (int col = 0; col < GAME_SIZE; col++) {
               System.out.print(board.cells[row][col].GetPlayer() + " ");
            }
            System.out.println("");
        }
    }
    public boolean IsCatsGame(){
        boolean result = true;
        for (int row = 0; row < GAME_SIZE; ++row) {
           for (int col = 0; col < GAME_SIZE; ++col) {
              if (board.cells[row][col].GetPlayer() == NO_PLAYER) result = false;
           }
        }
        return result;
    }
    //Returns true if the player has one the curent game on the board
    public boolean HasPlayerWon(int player) {
        boolean result = false;
        int pattern = 0b0_00000000_00000000_00000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < GAME_SIZE; ++row) {
           for (int col = 0; col < GAME_SIZE; ++col) {
              if (board.cells[row][col].GetPlayer() == player) {
                 pattern |= (1 << (row * GAME_SIZE + col));
              }
           }
        }
        for (long winningPattern : winningPatterns) {
           if ((pattern & winningPattern) == winningPattern) {
               result = true;
           }
        }
        return result;
    }
    //Returns true if player has one on the provided board
    public boolean HasPlayerWon(int[][] board, int player) {
        boolean result = false;
        int pattern = 0b0_00000000_00000000_00000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < GAME_SIZE; ++row) {
           for (int col = 0; col < GAME_SIZE; ++col) {
              if (board[row][col] == player) {
                 pattern |= (1 << (row * GAME_SIZE + col));
              }
           }
        }
        for (long winningPattern : winningPatterns) {
           if ((pattern & winningPattern) == winningPattern)result = true;
        }
        return result;
    }
}
