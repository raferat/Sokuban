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
  
  private final JSlider scaleX = new JSlider ( JSlider.HORIZONTAL , 100 , 400 , 100 );
  private final JSlider scaleY = new JSlider ( JSlider.VERTICAL , 100 , 400 , 100 );
  
  private Tool currentTool = Tool.Eraser;
  
  public Create ( main.Window window )
  {
    super ( window , "Map creator" , true );
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setSize(1000,1000);
    Preview preview = new Preview ( this );
    add ( preview , BorderLayout.CENTER );
    
    
    
    JToolBar toolbar = new JToolBar ();
    prepareTools ( toolbar );
    
    
    
    scaleX . setLabelTable ( scaleX . createStandardLabels ( 20 , 100 ) );
    scaleX . setPaintLabels(true);
    
    
    
    scaleY . setLabelTable ( scaleX . createStandardLabels ( 20 , 100 ) );
    scaleY . setPaintLabels(true);
    
    ChangeListener listener = (ChangeEvent e)->
    {
      preview.setScaleX ( scaleX.getValue()*1d / 100d );
      preview.setScaleY ( scaleY.getValue()*1d / 100d );
      preview.repaint();
    };
    
    scaleX . addChangeListener(listener);
    scaleY . addChangeListener(listener);
    
    add(toolbar,BorderLayout.SOUTH);
    add(scaleX,BorderLayout.NORTH);
    add(scaleY,BorderLayout.WEST);
  }
  
  public void setScale ( int x , int y )
  {
    scaleX . setValue ( x );
    scaleY . setValue ( y );
  }
  
  private void prepareTools ( JToolBar toolbar )
  {
    try ( InputStream in = Create.class.getResourceAsStream("Eraser.png") )
    {
      Image img = ImageIO.read(in);
      toolbar.add(new AbstractAction("",new ImageIcon(img))
      {
        public void actionPerformed ( ActionEvent e )
        {
          setTool ( Tool.Eraser );
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
  }
  
  public Tool getTool ()
  {
    return currentTool;
  }
  
  private void setTool ( Tool t )
  {
    currentTool = t;
    //System.out.println ( t );
  }
}