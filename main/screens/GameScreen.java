package main.screens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

import java.io.File;

import main.input.*;
import main.tiles.*;
import main.Window;
import main.help.*;


public class GameScreen extends JPanel
{
  private Tile[][][] tiles;
  
  private ArrayList<Point> ends = new ArrayList<>();
  private final main.Window window;
  
  private int playerX,playerY;
  
  public GameScreen ( main.Window w )
  {
    this . window = w;
    
    window . addKeyListener (new KeyAdapter()
    {
      @Override
      public void keyPressed ( KeyEvent e )
      {
        key(e);
        window.requestFocus();
      }
    });
  }
  
  public Tile[][] getStaticTiles()
  {
    Tile[][] s = new Tile[tiles.length][tiles[0].length];
    for ( int x = 0 ; x < tiles.length ; x ++ )
    {
      for ( int y = 0 ; y < tiles[x].length ; y ++ )
      {
        s[x][y] = tiles[x][y][0];
      }
    }
    
    return s;    
  }
  
  public int canMove ( int x , int y )
  {
    if ( tiles[x][y][0] instanceof Wall )
      return -1;
    else if ( tiles[x][y][1] instanceof main.tiles.Box )
      return 0;
    return 1;
  }
  
  private void key ( KeyEvent e )
  {
    if ( tiles != null )
    {
      int canMove = -1;
      switch ( e.getKeyCode() )
      {
        case KeyEvent . VK_W:
        case KeyEvent . VK_UP:
          if ( playerY - 1 < 0 ) break;
          canMove = canMove(playerX,playerY-1);
          if ( canMove == -1 ) break;
          if ( canMove == 0 && playerY - 2 >= 0 && canMove(playerX,playerY-2) != 1 ) break;
        
          playerY --;
        
          if ( canMove == 0 )
          {
            tiles[playerX][playerY-1][1]=tiles[playerX][playerY][1];
          }
          
        
          tiles[playerX][playerY][1]=tiles[playerX][playerY+1][1];
          tiles[playerX][playerY+1][1] = new Empty();
          break;
        
        case KeyEvent . VK_S:
        case KeyEvent . VK_DOWN:
          if ( playerY + 1 >= tiles[0].length ) break;
          canMove = canMove(playerX,playerY+1);
          if ( canMove == -1 ) break;
          if ( canMove == 0 && playerY + 2 < tiles[0].length && canMove(playerX,playerY+2) != 1 ) break;
          
          playerY ++;
        
          if ( canMove == 0 )
          {
            tiles[playerX][playerY+1][1]=tiles[playerX][playerY][1];
          }
          
        
          tiles[playerX][playerY][1]=tiles[playerX][playerY-1][1];
          tiles[playerX][playerY-1][1] = new Empty();
          break;
        
        case KeyEvent . VK_D:
        case KeyEvent . VK_RIGHT:
          if ( playerX + 1 >= tiles.length ) break;
          canMove = canMove(playerX+1,playerY);
          if ( canMove == -1 ) break;
          if ( canMove == 0 && playerX + 2 < tiles.length && canMove(playerX+2,playerY) != 1 ) break;
          
          playerX ++;
        
          if ( canMove == 0 )
          {
            tiles[playerX+1][playerY][1]=tiles[playerX][playerY][1];
          }
          
        
          tiles[playerX][playerY][1]=tiles[playerX-1][playerY][1];
          tiles[playerX-1][playerY][1] = new Empty();
          break;
        
        case KeyEvent . VK_A:
        case KeyEvent . VK_LEFT:
          if ( playerX - 1 < 0 ) break;
          canMove = canMove(playerX-1,playerY);
          if ( canMove == -1 ) break;
          if ( canMove == 0 && playerX - 2 >= 0 && canMove(playerX-2,playerY) != 1 ) break;
          
          playerX --;
          if ( canMove == 0 )
          {
            tiles[playerX-1][playerY][1]=tiles[playerX][playerY][1];
          }
          
          tiles[playerX][playerY][1]=tiles[playerX+1][playerY][1];
          tiles[playerX+1][playerY][1] = new Empty();
          break;
        
      }
      
      boolean win = true;
      for ( Point p : ends )
        if ( tiles[p.x][p.y][1] instanceof main.tiles.Box )
          ((End)tiles[p.x][p.y][0]).setOccupied(true);
        else
        {
          win = false;
          ((End)tiles[p.x][p.y][0]).setOccupied(false);
        }
      repaint();
      if ( win )
        window.win();
    }
  }
  
  private Tile[][] getMoveableTiles ()
  {
    Tile[][] s = new Tile[tiles.length][tiles[0].length];
    for ( int x = 0 ; x < tiles.length ; x ++ )
    {
      for ( int y = 0 ; y < tiles[x].length ; y ++ )
      {
        s[x][y] = tiles[x][y][1];
      }
    }
    
    return s;    
  }
  
  public void setFile ( File f )
  {
    ends.clear();
    tiles = MapParser.parse(f);
    
    int width = tiles.length;
    int height = tiles[0].length;
    
    Dimension screenSize = Toolkit . getDefaultToolkit () . getScreenSize();
    
    int size = Math.min(screenSize.height,screenSize.width) / 2;
    window.setSize((size/height)*width,(size/width)*height);
    
    findLoop : for ( int i = 0 ; i < width ; i ++ )
    {
      for ( int j = 0 ; j < height ; j ++ )
      {
        if ( tiles[i][j][1] instanceof Player )
        {
          playerX = i;
          playerY = j;
        }
        if ( tiles[i][j][0] instanceof End )
        {
          ends.add(new Point(i,j));
          if ( tiles[i][j][1] instanceof main.tiles.Box )
            ((End)tiles[i][j][0]).setOccupied(true);
        }
      }
    }
    
    /*Move.setStaticTiles(getStaticTiles());
    Move.ends = ends;
    SwingWorker<Move,Object> worker = new SwingWorker<Move,Object>()
    {
      @Override
      public Move doInBackground ()
      {
        Move m = new Move (getMoveableTiles(),5,playerX,playerY,window);
        Move finale = m.fillMoves ();
        
        return finale;
        //return m;
      }
      
      @Override
      protected void done ()
      {
        try
        {
          System.out.println ( "Is winning: " + get().isWinPath );
        }
        catch ( Exception e )
        {
          e.printStackTrace();
        }
      }
    };*/
    
    //worker.execute();
    repaint();
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
      
      int tileWidth = width / tiles.length;
      int tileHeight = height / tiles[0].length;
      
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