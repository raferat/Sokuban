package main.create;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.tiles.*;

import main.create.Create.Tool;

public class Preview extends JPanel
{
  private final Tile[][][] tiles;
  
  static final Empty e          = new Empty ();
  static final main.tiles.Box b = new main.tiles.Box ();
  static final Player p         = new Player ();
  static final End d            = new End ();
  static final Wall w           = new Wall ();
    
  
  public Preview ( Create te )
  {
    /*try ( InputStream in = Preview.class.getResourceAsStream("Cursor.png") )
    {
      Image img = ImageIO.read(in);
      setCursor( Toolkit . getDefaultToolkit() . createCustomCursor ( img , new Point ( 0 , 39 ) , "Pencil" ));
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }*/
    
    tiles = new Tile[35][35][2];
    
    Empty t = new Empty();
    for ( int i = 0 ; i < tiles.length ; i ++ )
    {
      for ( int j = 0 ; j < tiles[i].length ; j ++ )
      {
        tiles[i][j][0] = t;
        tiles[i][j][1] = t;
      }
    }
    tiles[12][13][1] = new Player();
    
    addMouseListener(new MouseAdapter () 
    {
      public void mouseClicked ( MouseEvent e )
      {
        clicked ( e , te.getTool() );
      }
    });
  }
  
  private void clicked ( MouseEvent me , Tool t )
  {
    int mouseX = me.getX();
    int mouseY = me.getY();
    
    /*int x = (int)( (tiles.length*1d/getWidth()*1d) * mouseX );
    int y = (int)( (tiles[x].length*1d/getHeight()*1d) * mouseY );*/
    
    int tileWidth = getWidth() / (tiles.length);
    int tileHeight = getHeight() / (tiles[0].length);
      
    int x = (int) Math.round( ( mouseX * 1d / getWidth  () ) * tiles.length );
    int y = (int) Math.round( ( mouseY * 1d / getHeight () ) * tiles[0].length );
    
    switch ( t )
    {
      case Eraser:
        tiles[x][y][0] = e;
        tiles[x][y][1] = e;
        break;
      case Wall:
        tiles[x][y][0] = w;
        break;
      case Player:
        tiles[x][y][1] = p;
        break;
      case Box:
        tiles[x][y][1] = b;
        break;
      case End:
        tiles[x][y][0] = d;
        break;
    }
    //tiles[x][y][0] = new Wall ();
    repaint();
    
    //System.out.printf ( "X: %d, Y: %d%n" , x , y );
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