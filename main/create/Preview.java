package main.create;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.tiles.*;

public class Preview extends JPanel
{
  private final Tile[][][] tiles;
  
  public Preview ()
  {
    try ( InputStream in = Preview.class.getResourceAsStream("Cursor.png") )
    {
      Image img = ImageIO.read(in);
      setCursor( Toolkit . getDefaultToolkit() . createCustomCursor ( img , new Point ( 0 , 39 ) , "Pencil" ));
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
    
    /*try
    {
      setCursor( Toolkit . getDefaultToolkit() . createCustomCursor( new ImageIcon("Cursor.png").getImage(), new Point(0,39), "My cursor" ));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }*/
    
    tiles = new Tile[35][35][2];
    
    Empty t = new Empty();
    for ( int i = 0 ; i < tiles.length ; i ++ )
      for ( int j = 0 ; j < tiles[i].length ; j ++ )
      {
        tiles[i][j][0] = t;
        tiles[i][j][1] = t;
      }
      
    tiles[12][13][1] = new Player();
  }
  
  @Override
  public void paintComponent ( Graphics g )
  {
    g.setColor ( new Color ( 0x2f3831 ) );
    g.fillRect ( 0 , 0 , getWidth() , getHeight () );
    g.setColor ( Color.WHITE );
    if ( tiles != null )
    {
      int width = getWidth();
      int height = getHeight();
      
      
      
      int tileWidth = width / (tiles.length);
      int tileHeight = height / (tiles[0].length);
      
      for ( int i = 0 ; i < tiles . length ; i ++ )
      {
        for ( int j = 0 ; j < tiles[i] . length ; j ++ )
        {
          for ( int k = 0 ; k < tiles[i][j] . length ; k ++ )
          {
            tiles[i][j][k] . draw ( g , i * tileWidth , j * tileHeight , tileWidth , tileHeight );
          }
        }
      }
    }
  }
}