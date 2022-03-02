package main.create;

import main.*;
import main.tiles.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Create extends JDialog
{
  public Create ( main.Window window )
  {
    super ( window , "Map creator" , true );
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setSize(1000,1000);
    add ( new Preview () , BorderLayout.CENTER );
    
    Empty e          = new Empty ();
    main.tiles.Box b = new main.tiles.Box ();
    Player p         = new Player ();
    End d            = new End ();
    Wall w           = new Wall ();
    
    JToolBar toolbar = new JToolBar ();
    toolbar.add(new AbstractAction("Clear")
    {
      public void actionPerformed ( ActionEvent e )
      {
        System.out.println("Empty");
      }
    });
    
    toolbar.add(new AbstractAction("",b.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        System.out.println("Box");
      }
    });
    
    toolbar.add(new AbstractAction("",p.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        System.out.println("Player");
      }
    });
    
    toolbar.add(new AbstractAction("",d.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        System.out.println("End");
      }
    });
    
    toolbar.add(new AbstractAction("",w.getIcon())
    {
      public void actionPerformed ( ActionEvent e )
      {
        System.out.println("Wall");
      }
    });
    
    add(toolbar,BorderLayout.SOUTH);
  }
    
}