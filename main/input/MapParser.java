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
}