package main.help;

import main.tiles.*;

public class Move
{
  private final int direction;
  
  private final int playerX;
  private final int playerY;
  
  private final int boxX;
  private final int boxY;
  
  /**
   * @param direction in which the player moved:
   *        up
   *        0
   * left 1   2 right
   *        3
   *       down
   *
   * @param x new playerX
   * @param y new playerY
   * @param boxX new boxX or -1 if not moved
   * @param boxY new boxY or -1 if not moved
   */
  public Move ( int direction , int x , int y , int boxX , int boxY )
  {
    this . direction = direction;
    
    this . playerX = x;
    this . playerY = y;
    
    this . boxX = boxX;
    this . boxY = boxY;
  }
  
  public void undo ( Tile[][][] tiles )
  {
    if (! ( tiles[playerX][playerY][1] instanceof Player ) ) throw new IllegalMoveException ("Bad undo operation, X: " + playerX + ", Y: " + playerY + ", does not contain Player.");
    Empty e = new Empty ();
    switch ( direction )
    {
      case 0:
        tiles[playerX][playerY+1][1] = tiles[playerX][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 1:
        tiles[playerX+1][playerY][1] = tiles[playerX][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 2:
        tiles[playerX-1][playerY][1] = tiles[playerX][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 3:
        tiles[playerX][playerY-1][1] = tiles[playerX][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
    }
    
    
    if ( boxX >= 0 && boxY >= 0 )
    {
      if (! ( tiles[boxX][boxY][1] instanceof Box ) ) throw new IllegalMoveException ("Bad undo operation, X: " + boxX + ", Y: " + boxY + ", does not contain Box.");
      switch ( direction )
      {
        case 0:
          tiles[boxX][boxY+1][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 1: 
          tiles[boxX+1][boxY][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 2: 
          tiles[boxX-1][boxY][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 3: 
          tiles[boxX][boxY-1][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
      }
    }
  }
  
  
  @Deprecated
  public void redo ( Tile[][][] tiles )
  {
    if (! ( tiles[playerX][playerY][1] instanceof Player ) ) throw new IllegalMoveException ("Bad undo operation, X: " + playerX + ", Y: " + playerY + ", does not contain Player.");
    Empty e = new Empty ();
    switch ( direction )
    {
      case 0:
        tiles[playerX][playerY][1] = tiles[playerX][playerY-1][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 1:
        tiles[playerX][playerY][1] = tiles[playerX-1][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 2:
        tiles[playerX][playerY][1] = tiles[playerX+1][playerY][1];
        tiles[playerX][playerY][1] = e;
        break;
      case 3:
        tiles[playerX][playerY][1] = tiles[playerX][playerY+1][1];
        tiles[playerX][playerY][1] = e;
        break;
    }
    
    
    if ( boxX >= 0 && boxY >= 0 )
    {
      if (! ( tiles[boxX][boxY][1] instanceof Box ) ) throw new IllegalMoveException ("Bad undo operation, X: " + boxX + ", Y: " + boxY + ", does not contain Box.");
      switch ( direction )
      {
        case 0:
          tiles[boxX][boxY+1][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 1: 
          tiles[boxX+1][boxY][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 2: 
          tiles[boxX-1][boxY][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
        case 3: 
          tiles[boxX][boxY-1][1] = tiles[boxX][boxY][1];
          tiles[boxX][boxY][1] = e;
          break;
      }
    }
  }
  
  public int getPlayerX()
  {
    return playerX;
  }
  
  public int getPlayerY()
  {
    return playerY;
  }
  
  public int getPlayerXBefore()
  {
    switch ( direction )
    {
      case 1:
        return playerX + 1;
        
      case 2:
        return playerX - 1;
        
      default:
        return playerX;
    }
    
  }
  
  public int getPlayerYBefore()
  {
    switch ( direction )
    {
      case 0:
        return playerY + 1;
        
      case 3:
        return playerY - 1;
        
      default:
        return playerY;
    }
    
  }
  
  
}