package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.IOException;

import main.screens.*;

public class Window extends JFrame 
{
  private int state = 0;
  private File map;
  
  private static MainScreen ms;
  public GameScreen gs;
  
  public Window ()
  {
    super("Sokoban");
    //setSize ( 1600 , (int)(1600 * (9d/16d)) );
    setSize(new Dimension ( 850 , (int)(850 * (9d/16d)) ));
    setMinimumSize ( new Dimension ( 850 , (int)(850 * (9d/16d)) ) );
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    addWindowListener ( new WindowAdapter ()
    {
      @Override
      public void windowClosing ( WindowEvent _e )
      {
        openRickroll();
      }
    });
    
    ms = new MainScreen ( this );
    gs = new GameScreen ( this );
    stateChange();    
  }
  
  public static void openRickroll ()
  {
    if (! ms.penalty() ) return;
    if ( Desktop.isDesktopSupported () && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) )
    {
      try
      {
        Desktop.getDesktop().browse ( new java.net.URI ("https://www.youtube.com/watch?v=dQw4w9WgXcQ") );
      }
      catch ( java.net.URISyntaxException | java.io.IOException e )
      {
        e.printStackTrace();
      }
      catch ( UnsupportedOperationException e )
      {
        e.printStackTrace();
      }
    }
    else if ( isLinux() )
    {
      try
      {
        if ( Runtime.getRuntime().exec(new String[] { "which", "xdg-open" }).getInputStream().read() != -1) 
        {
          Runtime.getRuntime().exec(new String[] { "xdg-open", "https://www.youtube.com/watch?v=dQw4w9WgXcQ" });
        }
      }
      catch ( IOException e )
      {
        e.printStackTrace();
      }
    }
  }
  
  private static boolean isLinux ()
  {
    String OS = System.getProperty ("os.name");
    return OS.indexOf("Linux") >= 0;
  }
  
  private void stateChange ()
  {
    switch ( state )
    {
      case 0:
        remove ( gs );
        add ( ms );
        ms.setSize(getContentPane().getSize());
        pack();
        break;
      
      case 1:
        remove ( ms );
        gs.setFile(map);
        add ( gs );
        gs.setSize(getContentPane().getSize());
        break;
    }
    
    //pack();
    repaint();
  }
  
  public void win ()
  {
    state = 0;
    stateChange();
  }
  
  public void play ( File f )
  {
    map = f;
    state = 1;
    stateChange();
  }
}