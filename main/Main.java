package main;

import main.screens.MainScreen;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;

public class Main
{
  public static final Font font = new Font ( "Pecita" , Font.PLAIN , 12 );
  
  public static void main ( String [] args )
  {
    SwingUtilities.invokeLater(()->
    {
      /*try 
      {
        //UIManager.setLookAndFeel ( new FlatDarkLaf () );
        //UIManager.setLookAndFeel (  );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }*/
      
      new Window().setVisible(true);
    });
  }
}