package main.screens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import main.Window;

import main.create.*;

import java.io.File;
import java.io.IOException;

public class MainScreen extends JPanel 
{
  private JCheckBox penalty = new JCheckBox("Panelta",false);

  public MainScreen ( Window w )
  {
    super();
    setLayout ( new GridLayout ( 5 , 1 , 20 , 80 ) );
    
    
    JLabel label = new JLabel ( "Sokoban od M. V." );
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont ( main.Main.font.deriveFont(60f) );
    
    JButton play = new JButton ( "Play" );
    play.addActionListener((ActionEvent e)->
    {
      JFileChooser jfc = new JFileChooser (new File ("./vstupy"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Map files", "txt", "rmap");
      jfc.setFileFilter(filter);
      int returnVal = jfc.showOpenDialog(getParent());
      if ( returnVal == JFileChooser.APPROVE_OPTION )
      {
        File map = jfc . getSelectedFile();
        System.out.println(map.getAbsolutePath());
        w . play ( map );
      }
      
    });
    
    play.setFont(main.Main.font.deriveFont(30f));
    
    JButton exit = new JButton ( "Exit" );
    exit.setFont(main.Main.font.deriveFont(30f));
    exit.addActionListener((ActionEvent _e)->
    {
      if ( penalty . isSelected() )
        Window.openRickroll();
      System.exit(0);
    });
    
    JButton create = new JButton ( "Create" );
    create.setFont(main.Main.font.deriveFont(30f));
    create.addActionListener((ActionEvent _e)->
    {
      new Create(w).setVisible(true);
    });
    
    add(label);
    add(play);
    add(create);
    add(exit);
    add(penalty);
  }
  
  public boolean penalty ()
  {
    return penalty . isSelected();
  }
}