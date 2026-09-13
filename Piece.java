import java.util.*;

abstract public class Piece{
  public String name = "";
  public String color = "";
  public int row = 0;
  public int col = 0;
  abstract public ArrayList<Move> getMoves(Piece[][]board);
}


