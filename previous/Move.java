package main.help;

import java.util.ArrayList;
import java.util.HashSet;

import java.awt.Point;

import main.Window;
import main.tiles.*;

public class Move
{
  private final Tile[][] tilesPosition;
  
  public static Tile[][] staticTiles;
  
  private static final ArrayList<Move> performedMoves = new ArrayList<>();
  
  public final boolean canExist;
  public boolean isWinPath = false;
  
  public static ArrayList<Point> ends;
  
  private final Window w;
  
  private final int playerX,playerY;
  
  private Move right;
  private Move left;
  private Move up;
  private Move down;
  
  private boolean win = true;
  
  public static void setStaticTiles ( Tile [][] staticTiles )
  {
    Move . staticTiles = staticTiles;
    performedMoves . clear ();
  }
  
  /**
   * 0 - left
   * 1 - up
   * 2 - right
   * 3 - down
   * other - nowhere
   */
  public Move ( Tile[][] tiles , int direction , int playerX , int playerY , Window w )
  {
    tilesPosition = tiles;
    this . w = w;
    int x = playerX;
    int y = playerY;
    int canMove = -1;
    switch ( direction )
    {
      case 1:
        if ( playerY - 1 < 0 ) break;
        canMove = canMove(playerX,playerY-1);
        if ( canMove == -1 ) break;
        if ( canMove == 0 && playerY - 2 >= 0 && canMove(playerX,playerY-2) != 1 ) break;
      
        playerY --;
      
        if ( canMove == 0 )
        {
          tiles[playerX][playerY-1]=tiles[playerX][playerY];
        }
        
      
        tiles[playerX][playerY] = tiles[playerX][playerY+1];
        tiles[playerX][playerY+1] = new Empty();
        break;
      
      case 3:
        if ( playerY + 1 >= tiles[0].length ) break;
        canMove = canMove(playerX,playerY+1);
        if ( canMove == -1 ) break;
        if ( canMove == 0 && playerY + 2 < tiles[0].length && canMove(playerX,playerY+2) != 1 ) break;
        
        playerY ++;
      
        if ( canMove == 0 )
        {
          tiles[playerX][playerY+1] = tiles[playerX][playerY];
        }
        
      
        tiles[playerX][playerY] = tiles[playerX][playerY-1];
        tiles[playerX][playerY-1] = new Empty();
        break;
      
      case 2:
        if ( playerX + 1 >= tiles.length ) break;
        canMove = canMove(playerX+1,playerY);
        if ( canMove == -1 ) break;
        if ( canMove == 0 && playerX + 2 < tiles.length && canMove(playerX+2,playerY) != 1 ) break;
        
        playerX ++;
      
        if ( canMove == 0 )
        {
          tiles[playerX+1][playerY]=tiles[playerX][playerY];
        }
        
      
        tiles[playerX][playerY]=tiles[playerX-1][playerY];
        tiles[playerX-1][playerY] = new Empty();
        break;
      
      case 0:
        if ( playerX - 1 < 0 ) break;
        canMove = canMove(playerX-1,playerY);
        if ( canMove == -1 ) break;
        if ( canMove == 0 && playerX - 2 >= 0 && canMove(playerX-2,playerY) != 1 ) break;
        
        playerX --;
        if ( canMove == 0 )
        {
          tiles[playerX-1][playerY]=tiles[playerX][playerY];
        }
        
        tiles[playerX][playerY]=tiles[playerX+1][playerY];
        tiles[playerX+1][playerY] = new Empty();
        break;
    }
    
    this . playerX = playerX;
    this . playerY = playerY;
    
    for ( Point p : ends )
    {
      if ( tilesPosition[p.x][p.y] instanceof main.tiles.Box )
        win = win && true;
      else
      {
        win = win && false;
      }
    }
        
    
    isWinPath = win;
    
    boolean contains;
    
    if ( win )
      System.out.println("win");
    
    
    if ( performedMoves . contains ( this ) )
    {
      contains = true;
    }
    else
    {
      contains=false;
      performedMoves.add(this);
    }
    
    canExist = ( ! ( x == playerX && y == playerY ) || direction > 3 ) && ! contains;
    
    System.out.println(canExist);
    
  }
  
  public int canMove ( int x , int y )
  {
    if ( staticTiles[x][y] instanceof main.tiles.Wall )
      return -1;
    else if ( tilesPosition[x][y] instanceof main.tiles.Box )
      return 0;
    return 1;
  }
  
  
  public Move fillMoves ()
  {
    if ( this . win || this . isWinPath )
    {
      return this;
    }
    
    left = new Move ( tilesPosition , 0 , playerX , playerY , w );
    up = new Move ( tilesPosition , 1 , playerX , playerY , w );
    right = new Move ( tilesPosition , 2 , playerX , playerY , w );
    down = new Move ( tilesPosition , 3 , playerX , playerY , w );
    
    while ( ! (left.isWinPath || up.isWinPath || right.isWinPath || down.isWinPath) )
    {
      if ( left.canExist )
        left.fillMoves();
      
      if ( right . canExist )
        right.fillMoves();
      
      if ( up . canExist )
        up.fillMoves();
      
      if ( down.canExist )
        down.fillMoves();
    }
    
    isWinPath = true;
    
    return this;
    
    /*if ( this . win )
    {
      return this;
    }
    
    if ( left == null )
      left = new Move ( tilesPosition , 0 , playerX , playerY , w );
    else if ( left.canExist )
      left.fillMoves();
    
    if ( up == null )
      up = new Move ( tilesPosition , 1 , playerX , playerY , w );
    else if ( up.canExist )
      up.fillMoves();
    
    if ( right == null )
      right = new Move ( tilesPosition , 2 , playerX , playerY , w );
    else if ( right.canExist )
      right.fillMoves();
    
    if ( down == null )
      down = new Move ( tilesPosition , 3 , playerX , playerY , w );
    else if ( down.canExist )
      down.fillMoves();
    
    
    if ( left . isWinPath )
    {
      isWinPath = true;
    }
    
    if ( right . isWinPath )
    {
      isWinPath = true;
    }
    
    if ( up . isWinPath )
    {
      isWinPath = true;
    }
    
    if ( down . isWinPath )
    {
      isWinPath = true;
    }
    
    return this;*/
  }
  
  private boolean arraysMatch ( Tile[][] t )
  {
    boolean match = true;
    for ( int x = 0 ; x < t.length ; x ++ )
    {
      for ( int y = 0 ; y < t[x].length ; y ++ )
      {
        if ( t[x][y] instanceof Player && tilesPosition[x][y] instanceof Player )
          match = match && true;
        else if ( t[x][y] instanceof Box && tilesPosition[x][y] instanceof Box )
          match = match && true;
        else if ( t[x][y] instanceof Empty && tilesPosition[x][y] instanceof Empty )
          match = match && true;
        else
          match = false;
      }
    }
    
    return match;
  }
  
  public boolean equals ( Object obj )
  {
    if ( obj == this )
      return true;
    
    if (! ( obj instanceof Move ) )
    {
      return false;
    }
    
    Move m = (Move) obj;
    
    if ( m.tilesPosition.length != tilesPosition.length || m.tilesPosition[0].length != tilesPosition[0].length )
    {
      return false;
    }
    
    return arraysMatch ( m.tilesPosition );
  }
}