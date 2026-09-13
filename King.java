import java.util.*;
public class King extends Piece{

  public King(int row, int col, String color){
    this.row = row;
    this.col = col;
    this.color = color;
    this.name = color.charAt(0) + "King";

  }
  public ArrayList<Move> getMoves(Piece[][]board){
    ArrayList<Move> moves = new ArrayList<Move>();
    for(int i = -1; i < 2; i++){
      for(int j = -1; j < 2; j++){

        if (row + i < 8 & row + i > -1){
          if(col + j < 8 & col + j > -1){
            if (board[row + i][col + j] != null){
              if (board[row + i][col + j].color != color){
                moves.add(new Move(this, row + i, col + j));
              }
            }
            else{
              moves.add(new Move(this, row + i, col + j));
            }
        }
        }
    }
    }
    

  for(Move m : moves){
  }
  return moves;
  }
 
}
