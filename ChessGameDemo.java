import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
//while gameover is false
class ChessGameDemo extends JFrame implements MouseListener, MouseMotionListener {

  JLayeredPane layeredPane;
  JPanel chessBoard;
  JLabel chessPiece;
  int xAdjustment;
  int yAdjustment;
  int pieceR = 0;
  int pieceC = 0;
  int playernum = 1;
  int kingrow = 0;
  int kingcol = 0;
  Piece[][] board;
  Piece WhiteKing;
  ArrayList<Move> movelist;
  ArrayList<Move> masterlist;

  public ChessGameDemo(){
  
  System.out.println("It is white's turn. "); 
  masterlist = new ArrayList <Move>();
  movelist = new ArrayList <Move>();
  Dimension boardSize = new Dimension(600, 600);
  //2D array
  board = new Piece[8][8];
  for(int j = 0; j < 8; j++){
    board[6][j] = new Pawn(6,j,"white");
  }
  for(int j = 0; j < 8; j++){
    board[1][j] = new Pawn(1,j,"black");
  }
  board[7][1] = new Knight(7,1,"white");
  board[7][6] = new Knight(7,6,"white");
  board[0][6] = new Knight(0,6,"black");
  board[0][1] = new Knight(0,1,"black");

  board[7][0] = new Rook(7,0,"white");
  board[7][7] = new Rook(7,7,"white");
  board[0][7] = new Rook(0,7,"black");
  board[0][0] = new Rook(0,0,"black");

  board[7][2] = new Bishop(7,2,"white");
  board[7][5] = new Bishop(7,5,"white");
  board[0][2] = new Bishop(0,2,"black");
  board[0][5] = new Bishop(0,5,"black");

  board[7][3] = new Queen(7,3,"white");
  board[0][3] = new Queen(0,3,"black");

  board[7][4] = new King(7,4,"white");
  board[0][4] = new King(0,4,"black");

  WhiteKing = board[7][4];

  //  Use a Layered Pane for this this application
 layeredPane = new JLayeredPane();
  getContentPane().add(layeredPane);
  layeredPane.setPreferredSize(boardSize);
  layeredPane.addMouseListener(this);
  layeredPane.addMouseMotionListener(this);
 
  //Add a chess board to the Layered Pane 
 
  chessBoard = new JPanel();
  layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
  chessBoard.setLayout( new GridLayout(8, 8) );
  chessBoard.setPreferredSize( boardSize );
  chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
 
  for (int i = 0; i < 64; i++) {
  JPanel square = new JPanel( new BorderLayout() );
  chessBoard.add( square );
 
  int row = (i / 8) % 2;
  if (row == 0)
  square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
  else
  square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
  }
  //placeholder to create piece and panel
  JLabel piece = new JLabel(new ImageIcon("bKnight.png"));
  JPanel panel = (JPanel)chessBoard.getComponent(0);
  //Add in all the pieces to make board
  for(int i = 0; i < 8; i++){
    for(int j = 0; j < 8; j++){
      if (board[i][j] != null){
        String piecename = board[i][j].name;
        int position = (i*8) + j;
        piece = new JLabel(new ImageIcon(piecename + ".png"));
        panel = (JPanel)chessBoard.getComponent(position);
        panel.add(piece);
        }
    }
  }


  //piece = new JLabel(new ImageIcon("bKing.png"));
  //panel = (JPanel)chessBoard.getComponent(15);
  //panel.add(piece);

 
  }
  public void mousePressed(MouseEvent e){
  masterlist = null;
  chessPiece = null;
  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
  
  for(int j = 0; j < 8; j ++){
    for(int i = 0; i < 8; i ++){
      JPanel reset = (JPanel)chessBoard.getComponent((i*8) + j);
      if(i % 2 == j % 2){
      reset.setBackground(Color.blue);
      }
      else{
      reset.setBackground(Color.white);
      }
    }
  }
  if (c instanceof JPanel) 
  return;
 
  Point parentLocation = c.getParent().getLocation();
  xAdjustment = parentLocation.x - e.getX();
  yAdjustment = parentLocation.y - e.getY();
  chessPiece = (JLabel)c;
  chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
  chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
  layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);

  int Xcomp = e.getX() + xAdjustment;
  int Ycomp = e.getY() + yAdjustment;
  int ComponentNum = (Xcomp / 75) + 8 * (Ycomp / 75);


  if (board[Ycomp/75][Xcomp/75] != null){
    


    movelist = board[Ycomp/75][Xcomp/75].getMoves(board); 
    if(playernum == 1 && board[Ycomp/75][Xcomp/75].color == "black"){
      movelist = null;
    }
    if(playernum == 0 && board[Ycomp/75][Xcomp/75].color == "white"){
      movelist = null;
    }
     
     
     
    //getting the moves
    pieceR = Ycomp/75;
    pieceC = Xcomp/75;
    if(movelist != null){
      for(int i = 0; i < movelist.size(); i ++){
        int newrow = movelist.get(i).r;
        int newcol = movelist.get(i).c;
        int compnum = (newrow * 8) + newcol;
        JPanel template = (JPanel)chessBoard.getComponent(compnum);
        if(newrow % 2 == newcol % 2){
          template.setBackground(Color.green.darker());
        }
        else{
          template.setBackground(Color.green);
        }
      }
    }

  }
  }
  
 
  //Move the chess piece around
  public ArrayList <Move> whitegetAllMoves(){
    ArrayList <Move> supermasterlist = new ArrayList <Move>();
    for(int i = 0; i < 8; i++){
    for(int j = 0; j < 8; j ++){
      if(board[i][j] != null){
        if(board[i][j].color == "white"){
          movelist = board[i][j].getMoves(board);
          if(movelist.size() > 0){
            for(int m = 0; m < movelist.size(); m ++){
              supermasterlist.add(0,movelist.get(m));
            }
          }
        }
      }
    }
  }
  return supermasterlist;
  }
  public ArrayList <Move> blackgetAllMoves(){
    ArrayList <Move> supermasterlist = new ArrayList <Move>();
    for(int i = 0; i < 8; i++){
    for(int j = 0; j < 8; j ++){
      if(board[i][j] != null){
        if(board[i][j].color == "black"){
          movelist = board[i][j].getMoves(board);
          if(movelist.size() > 0){
            for(int m = 0; m < movelist.size(); m ++){
              supermasterlist.add(0,movelist.get(m));
            }
          }
        }
      }
    }
  }
  return supermasterlist;
  }

  public void checkwhiteking(){
    for(int k = 0; k < masterlist.size(); k ++){
      int possr = masterlist.get(k).r;
      int possc = masterlist.get(k).c;

    }

  }
  public void checkblackking(){

  }


  public void mouseDragged(MouseEvent me) {
  if (chessPiece == null) return;

 chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
  
 }
 
  //Drop the chess piece back onto the chess board
 
  public void mouseReleased(MouseEvent e) {

  if(chessPiece == null) return;

  chessPiece.setVisible(false);
  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
  chessPiece.setVisible(true);
 

  int Xcomp = e.getX();
  int Ycomp = e.getY();
  int ComponentNum = (Xcomp / 75) + 8 * (Ycomp / 75);

  int C = Xcomp / 75;
  int r = Ycomp / 75;

  //movelist = board[pieceR][pieceC].getMoves(board);
  if(movelist != null){
    for(int a = 0; a < movelist.size(); a ++){
      int possrow = movelist.get(a).r;
      int posscol = movelist.get(a).c;
      if(r == possrow && C == posscol){
        board[r][C] = null;
        if (c instanceof JLabel){
          Container parent = c.getParent();
          parent.remove(0);
          parent.add( chessPiece );
          }
        else {
        Container parent = (Container)c;
        parent.add( chessPiece );
        }
        chessPiece.setVisible(true);
        
        board[r][C] = board[pieceR][pieceC];
        board[r][C].row = r;
        board[r][C].col = C;
        board[pieceR][pieceC] = null;
        for(int j = 0; j < 8; j ++){
          for(int i = 0; i < 8; i ++){
            JPanel reset = (JPanel)chessBoard.getComponent((i*8) + j);
            if(i % 2 == j % 2){
            reset.setBackground(Color.blue);
            }
            else{
            reset.setBackground(Color.white);
            }
          }
        }
        if(playernum == 1){
          playernum = 0;
          System.out.println("\n---------------\n");
          System.out.println("It is now black's turn.");
          masterlist = whitegetAllMoves();
        }
        else{
          playernum = 1;
          System.out.println("\n---------------\n");
          System.out.println("It is now white's turn.");
          masterlist = blackgetAllMoves();
        }
        return;
      }
      }
  }





  JPanel goback = (JPanel)chessBoard.getComponent(pieceR * 8 + pieceC);
  chessPiece.setVisible(true);
  goback.add(chessPiece);



  }
  
  public void mouseClicked(MouseEvent e){

  }
  public void mouseMoved(MouseEvent e){

  }
  public void mouseEntered(MouseEvent e){

  }
  public void mouseExited(MouseEvent e){

  }

  
 

 
}

 