package main.input;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import main.tiles.*;

public class MapParser 
{
  public static Tile[][][] parse ( File in )
  {
    try ( Scanner sc = new Scanner ( in ) )
    {
      int width = sc.nextInt();
      int height = sc.nextInt();
      
      Tile[][][] tiles = new Tile[width][height][2];      
      String line = sc.nextLine();
      
      for ( int i = 0 ; i < height ; i ++ )
      {
        line = sc.nextLine();
        System.out.println(line);
        for ( int j = 0 ; j < width ; j ++ )
        {
          switch ( line.charAt(j) )
          {
            case 'W':
              tiles[j][i][0] = new Wall ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'P':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Player();
              break;
            
            case 'E':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'p':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Player();
              break;
            
            case 'T':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'b':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Box();
              break;
            
            case 'B':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Box();
              break;
          }
        }
      }
      
      return tiles;
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Tile[][][] parse ( String in )
  {
    try ( Scanner sc = new Scanner ( in ) )
    {
      int width = sc.nextInt();
      int height = sc.nextInt();
      
      Tile[][][] tiles = new Tile[width][height][2];      
      String line = sc.nextLine();
      
      for ( int i = 0 ; i < height ; i ++ )
      {
        line = sc.nextLine();
        System.out.println(line);
        for ( int j = 0 ; j < width ; j ++ )
        {
          switch ( line.charAt(j) )
          {
            case 'W':
              tiles[j][i][0] = new Wall ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'P':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Player();
              break;
            
            case 'E':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'p':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Player();
              break;
            
            case 'T':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Empty ();
              break;
            
            case 'b':
              tiles[j][i][0] = new End ();
              tiles[j][i][1] = new Box();
              break;
            
            case 'B':
              tiles[j][i][0] = new Empty ();
              tiles[j][i][1] = new Box();
              break;
          }
        }
      }
      
      return tiles;
    }
  }
  
  public static String toString ( Tile[][][] tiles )
  {
    String result = "" + tiles.length + " " + tiles[0].length + "\n";
    int playerCount = 0;
    int boxCount = 0;
    int endCount = 0;
    
    for ( int y = 0 ; y < tiles[0].length ; y ++ )
    {
      for ( int x = 0 ; x < tiles.length ; x ++ )
      {
        if ( tiles[x][y][0] instanceof Wall && tiles[x][y][1] instanceof Empty )
          result += 'W';
        else if ( tiles[x][y][0] instanceof Empty && tiles[x][y][1] instanceof Empty )
          result += 'E';
        else if ( tiles[x][y][0] instanceof Empty && tiles[x][y][1] instanceof Player )
        {
          result += 'P';
          playerCount ++;
        }
        else if ( tiles[x][y][0] instanceof End && tiles[x][y][1] instanceof Player )
        {
          result += 'p';
          playerCount ++;
          endCount ++;
        }
        else if ( tiles[x][y][0] instanceof End && tiles[x][y][1] instanceof Empty )
        {
          result += 'T';
          endCount ++;
        }
        else if ( tiles[x][y][0] instanceof End && tiles[x][y][1] instanceof Box )
        {
          result += 'b';
          endCount ++;
          boxCount ++;
        }
        else if ( tiles[x][y][0] instanceof Empty && tiles[x][y][1] instanceof Box )
        {
          result += 'B';
          boxCount ++;
        }
        else
          throw new UnsupportedMapParsingException ( "Cannot save: " + tiles[x][y][0] . getClass() .  	getSimpleName() + " and " + tiles[x][y][1] . getClass() . getSimpleName() + " are on top of each other." );
      }
      result += '\n';
    }
    
    if ( playerCount != 1 )
      throw new UnsupportedMapParsingException ( "Cannot save: Number of players is " + playerCount + ", but only one is allowed."  );
    
    if ( boxCount == 0 )
      throw new UnsupportedMapParsingException ( "Cannot save: Number of boxes is 0."  );
    
    if ( endCount == 0 )
      throw new UnsupportedMapParsingException ( "Cannot save: Number of ends is 0."  );
    
    if ( endCount > boxCount )
      throw new UnsupportedMapParsingException ( "Cannot save: There is not enought boxes("+boxCount+") in this level. Needed " + endCount );
    else if ( endCount < boxCount )
      throw new UnsupportedMapParsingException ( "Cannot save: There is too many boxes("+boxCount+") in this level. Needed " + endCount );
    
    
    return result;
  }
}