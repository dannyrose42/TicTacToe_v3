package tictactoe_v3;
import java.util.*;
import static tictactoe_v3.TicTacToeGame.NO_PLAYER;
/** AIPlayer using Minimax algorithm */
public class Max extends AIPlayer {
   
    private final String MAX = "MAX";
    private final String MIN = "MIN";
    private int [][] tempBoard;
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
    
    /** Constructor with the given game board */
    public Max(TicTacToeGame game, int player) {
       super(game, player);
    }

     /** Get next best move for computer. Return int[2] of {row, col} */
     @Override
     int[] GetMove() {
        tempBoard = game.GetBoardState();
        int[] result = minimax(4, MAX); // depth, max turn
        return new int[] {result[1], result[2]};   // row, col
     }

    /** Recursive minimax at level of depth for either maximizing or minimizing player.
        Return int[3] of {score, row, col}  */
    private int[] minimax(int depth, String seed) {

       // Generate possible next moves in a List of int[2] of {row, col}.
       List<int[]> nextMoves = generateMoves();

       // mySeed is maximizing; while oppSeed is minimizing
       int bestScore = (seed.equals(MAX)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
       int currentScore;
       int bestRow = -1;
       int bestCol = -1;

       if (nextMoves.isEmpty() || depth == 0) {
          // Gameover or depth reached, evaluate score
          bestScore = evaluate();
       } else {
          for (int[] move : nextMoves) {
            // Try this move for the current "player"
            this.tempBoard[move[0]][move[1]] = (seed.equals(MAX)) ? this.myPlayer : this.oppPlayer;
            if (seed.equals(MAX)) {  // mySeed (computer) is maximizing player
                // Try this move for the current "player"
                currentScore = minimax(depth - 1, MIN)[0];
                if (currentScore > bestScore) {
                   bestScore = currentScore;
                   bestRow = move[0];
                   bestCol = move[1];
                }
            } else {  // oppSeed is minimizing player
                currentScore = minimax(depth - 1, MAX)[0];
                if (currentScore < bestScore) {
                   bestScore = currentScore;
                   bestRow = move[0];
                   bestCol = move[1];
                }
            }
             // Undo move
             this.tempBoard[move[0]][move[1]] = NO_PLAYER;
          }
       }
       return new int[] {bestScore, bestRow, bestCol};
    }

    /** Find all valid next moves.
        Return List of moves in int[2] of {row, col} or empty list if gameover */
     private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>(); // allocate List

        // If gameover, i.e., no next move
        if (game.HasPlayerWon(tempBoard, this.myPlayer) || game.HasPlayerWon(tempBoard, this.oppPlayer)) {
           return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < game.GAME_SIZE; ++row) {
           for (int col = 0; col < game.GAME_SIZE; ++col) {
              if (this.tempBoard[row][col] == NO_PLAYER) {
                 nextMoves.add(new int[] {row, col});
              }
           }
        }
        return nextMoves;
     }

    /** The heuristic evaluation function for the current board
        @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
                -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
                0 otherwise   */
    private int evaluate() {
       int score = 0;
       // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
       //Rows
       score += evaluateLine(0, 0,  //row 0 
                             0, 1, 
                             0, 2, 
                             0, 3, 
                             0, 4);  
       
       score += evaluateLine(1, 0,  //row 1
                             1, 1, 
                             1, 2,
                             1, 3,
                             1, 4);  
       
       score += evaluateLine(2, 0,  //row 2
                             2, 1, 
                             2, 2,
                             2, 3,
                             2, 4); 
       
       score += evaluateLine(3, 0,  //row 4
                             3, 1, 
                             3, 2,
                             3, 3,
                             3, 4);  
       
       score += evaluateLine(4, 0,  //row 4
                             4, 1, 
                             4, 2,
                             4, 3,
                             4, 4);  
       
       //Cols
       score += evaluateLine(0, 0,  //col 0  
                             1, 0, 
                             2, 0,
                             3, 0,
                             4, 0); 
       
       score += evaluateLine(0, 1,  //col 1
                             1, 1, 
                             2, 1,
                             3, 1,
                             4, 1);  
       
       score += evaluateLine(0, 2,  //col 2
                             1, 2, 
                             2, 2,
                             3, 2,
                             4, 2);
       
       score += evaluateLine(0, 3,  //col 3
                             1, 3, 
                             2, 3,
                             3, 3,
                             4, 3);
       
       score += evaluateLine(0, 4,  //col 4
                             1, 4, 
                             2, 4,
                             3, 4,
                             4, 4);
       
       //Diagonals
       score += evaluateLine(0, 0,  //Diag 1
                             1, 1, 
                             2, 2,
                             3, 3,
                             4, 4);
       score += evaluateLine(0, 4,  //Diag 2
                             1, 3, 
                             2, 2,
                             3, 1,
                             4, 0);
       return score;
    }

    /** The heuristic evaluation function for the given line of 3 cells
        @Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
                -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
                0 otherwise */
    private int evaluateLine(int row1, int col1, 
                             int row2, int col2, 
                             int row3, int col3,
                             int row4, int col4,
                             int row5, int col5) {
       int score = 0;

       // First cell
       if (this.tempBoard[row1][col1] == this.myPlayer) {
          score = 1;
       } else if (this.tempBoard[row1][col1] == this.oppPlayer) {
          score = -1;
       }

       // Second cell
       if (this.tempBoard[row2][col2] == this.myPlayer) {
          if (score == 1) {   // cell1 is mySeed
             score = 10;
          } else if (score == -1) {  // cell1 is oppSeed
             score =  0;
          } else {  // cell1 is empty
             score = 1;
          }
       } else if (this.tempBoard[row2][col2] == this.oppPlayer) {
          if (score == -1) { // cell1 is oppSeed
             score = -10;
          } else if (score == 1) { // cell1 is mySeed
             score = 0;
          } else {  // cell1 is empty
             score = -1;
          }
       }

       // Third cell
       if (this.tempBoard[row3][col3] == this.myPlayer) {
          if (score > 0) {  // cell1 and/or cell2 is mySeed
             score *= 10;
          } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = 1;
          }
       } else if (this.tempBoard[row3][col3] == this.oppPlayer) {
          if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score *= 10;
          } else if (score > 1) {  // cell1 and/or cell2 is mySeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = -1;
          }
       }
       // Fourth cell
       if (this.tempBoard[row4][col4] == this.myPlayer) {
          if (score > 0) {  // cell1 and/or cell2 is mySeed
             score *= 10;
          } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = 1;
          }
       } else if (this.tempBoard[row4][col4] == this.oppPlayer) {
          if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score *= 10;
          } else if (score > 1) {  // cell1 and/or cell2 is mySeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = -1;
          }
       }
       
       // Fifth cell
       if (this.tempBoard[row5][col5] == this.myPlayer) {
          if (score > 0) {  // cell1 and/or cell2 is mySeed
             score *= 10;
          } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = 1;
          }
       } else if (this.tempBoard[row5][col5] == this.oppPlayer) {
          if (score < 0) {  // cell1 and/or cell2 is oppSeed
             score *= 10;
          } else if (score > 1) {  // cell1 and/or cell2 is mySeed
             score = 0;
          } else {  // cell1 and cell2 are empty
             score = -1;
          }
       }
       return score;
    } 
     /** Returns true if thePlayer wins */
     private boolean hasWon(int player) {
         boolean result = false;
         int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
         for (int row = 0; row < game.GAME_SIZE; row++) {
            for (int col = 0; col < game.GAME_SIZE; col++) {
               if (this.tempBoard[row][col] == player) {
                  pattern |= (1 << (row * game.GAME_SIZE + col));
               }
            }
         }
         for (long winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) result = true;
         }
         return result;
    }
}