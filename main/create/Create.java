package main.create;

import main.*;
import main.tiles.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.IOException;

public class Create extends JDialog
{
  public static enum Tool
  {
    Eraser (),
    Wall (),
    Player (),
    Box (),
    End ()
  }
  
  private Tool currentTool = Tool.Eraser;
  
  public Create ( main.Window window )
  {
    super ( window , "Map creator" , true );
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setSize(1000,1000);
    add ( new Preview ( this ) , BorderLayout.CENTER );
    
    JToolBar toolbar = new JToolBar ();
    try ( InputStream in = Create.class.getResourceAsStream("Eraser.png") )
    {
      Image img = ImageIO.read(in);
      toolbar.add(new AbstractAction("",new ImageIcon(img))
      {
        public void actionPerformed ( ActionEvent e )
        {
          System.out.println("Empty");
        }
      });
    
    }
    catch ( IOException ex )
    {
      ex.printStackTrace();
    }
    
    toolbar.add(new AbstractAction("",Preview.b.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        setTool ( Tool.Box );
      }
    });
    
    toolbar.add(new AbstractAction("",Preview.p.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        setTool ( Tool.Player );
      }
    });
    
    toolbar.add(new AbstractAction("",Preview.d.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        setTool ( Tool.End );
      }
    });
    
    toolbar.add(new AbstractAction("",Preview.w.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        setTool ( Tool.Wall );
      }
    });
    
    add(toolbar,BorderLayout.SOUTH);
  }
  
  public Tool getTool ()
  {
    return currentTool;
  }
  
  private void setTool ( Tool t )
  {
    currentTool = t;
    System.out.println ( t );
  }
}