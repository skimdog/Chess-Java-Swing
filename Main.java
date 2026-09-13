import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
 

class Main{
  public static void main(String[] args) {
  JFrame frame = new ChessGameDemo();
  frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE );
  frame.pack();
  frame.setResizable(true);
  frame.setLocationRelativeTo( null );
  frame.setVisible(true);
 }
}
