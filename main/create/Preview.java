package main.create;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.tiles.*;

import main.create.Create.Tool;

public class Preview extends JPanel
{
  private final Tile[][][] tiles;
  
  private double scaleX = 1d, scaleY = 1d;
  private int offsetX = 1, offsetY = 1;
  
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
    
    MouseAdapter adapter = new MouseAdapter () 
    {
      private boolean dragJustStarted = true;
      private int lastMouseX = -1;
      private int lastMouseY = -1;
      
      /*public void mouseClicked ( MouseEvent e )
      {
        if ( e.getButton() == MouseEvent.BUTTON1 )
        {
          if ( e.getClickCount () == 1 )
            clicked ( e , te.getTool() );
          else
          {
            scaleX = 1d;
            scaleY = 1d;
            te.setScale ( 100 , 100 );
          }
        }
        else if ( e.getButton() == MouseEvent.BUTTON3 )
          clicked ( e , Tool.Eraser );
      }*/
      
      public void mousePressed ( MouseEvent e )
      {
        if ( e.getButton() == MouseEvent.BUTTON1 )
        {
          if ( e.getClickCount () == 1 )
            clicked ( e , te.getTool() );
          else
          {
            scaleX = 1d;
            scaleY = 1d;
            offsetX = 0;
            offsetY = 0;
            te.setScale ( 100 , 100 );
            repaint();
          }
        }
        else if ( SwingUtilities.isMiddleMouseButton(e) )
        {
          lastMouseX = e.getX();
          lastMouseY = e.getY();
        }
      }
      
      public void mouseReleased ( MouseEvent e )
      {
        
      }
      
      public void mouseDragged ( MouseEvent e )
      {
        
        if ( SwingUtilities.isLeftMouseButton(e) )
          clicked ( e , te.getTool() );
        else if ( SwingUtilities.isRightMouseButton(e) )
          clicked ( e , Tool.Eraser );
        else if ( SwingUtilities.isMiddleMouseButton(e) )
        {
          offsetX += ( lastMouseX - e.getX() );
          offsetY += ( lastMouseY - e.getY() );
          lastMouseX = e.getX();
          lastMouseY = e.getY();
          repaint();
        }
        
        /*System.out.print("Dragged ");
        if ( e . getButton() == MouseEvent . BUTTON2 )
        {
          System.out.print("Middle mouse");
        }
        System.out.println();*/
      }
    };
    
    addMouseListener ( adapter );
    
    addMouseMotionListener ( adapter );
  }
  
  private void clicked ( MouseEvent me , Tool t )
  {
    int mouseX = me.getX();
    int mouseY = me.getY();
    
    /*int x = (int)( (tiles.length*1d/getWidth()*1d) * mouseX );
    int y = (int)( (tiles[x].length*1d/getHeight()*1d) * mouseY );*/
    
    int tileWidth = getWidth() / (tiles.length);
    int tileHeight = getHeight() / (tiles[0].length);
    
    int x = 0;
    int y = 0;
    
    loop : for ( x = 0 ; x < tiles.length ; x ++ )
    {
      for ( y = 0 ; y < tiles[x].length ; y ++ )
      {
        if ( x * tileWidth * scaleX - offsetX < mouseX && x * tileWidth * scaleX + tileWidth * scaleX - offsetX > mouseX && y * tileHeight * scaleY - offsetY < mouseY && y * tileHeight * scaleY + tileHeight * scaleY - offsetY > mouseY )
        {
          break loop;
        }
      }
    }
    
    /*int x = (int) ( ( mouseX * 1d / (1d * ( getWidth  () * scaleX ) ) ) * ( 1d * tiles.length ) );
    int y = (int) ( ( mouseY * 1d / (1d * ( getHeight () * scaleY ) ) ) * ( 1d * tiles[0].length ) );*/
    if ( x >= tiles.length || y >= tiles[0].length ) return;
    
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
    repaint();
    
  }
  
  public void setScaleX ( double scaleX )
  {
    this . scaleX = scaleX;
  }
  
  public void setScaleY ( double scaleY )
  {
    this . scaleY = scaleY;
  }
  
  
  @Override
  public void paintComponent ( Graphics g1 )
  {
    BufferedImage img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB );
    Graphics g = img.createGraphics();
    
    
    g.setColor ( new Color ( 0x2f3831 ) );
    g.fillRect ( 0 , 0 , getWidth() , getHeight () );
    g.setColor ( new Color ( 0x6b7f6f ) );
    
    if ( tiles != null )
    {
      int width = getWidth();
      int height = getHeight();
      
      
      
      int tileWidth = width / (tiles.length);
      int tileHeight = height / (tiles[0].length);
      
      for ( int i = 0 ; i < tiles . length ; i ++ )
      {
        g . drawLine ( i * tileWidth  , 0 , i * tileWidth , getHeight () );
      }
    
      for ( int i = 0 ; i < tiles[0] . length ; i ++ )
      {
        g . drawLine ( 0 , i * tileHeight , getWidth() , i * tileHeight );
      }
    
      
      for ( int i = 0 ; i < tiles . length ; i ++ )
      {
        
        g . drawLine ( i * tileWidth  , 0 , i * tileWidth , getHeight () );
        for ( int j = 0 ; j < tiles[i] . length ; j ++ )
        {
           
          for ( int k = 0 ; k < tiles[i][j] . length ; k ++ )
          {
            tiles[i][j][k] . draw ( g , i * tileWidth , j * tileHeight , tileWidth , tileHeight );
          }
        }
      }
    }
    
    g.dispose();
    g1.drawImage ( img.getScaledInstance ( (int) ( getWidth() * scaleX ) , (int) ( getHeight() * scaleY ) , Image.SCALE_REPLICATE ) , - offsetX , - offsetY , null );
  }
}