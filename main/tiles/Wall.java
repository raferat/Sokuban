package main.tiles;

import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;


public class Wall extends Tile
{
  public Wall ()
  {
    try ( InputStream in = Player.class.getResourceAsStream("Wall.png") )
    {
      img = ImageIO.read(in);
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
  
  
  @Override
  public void draw ( Graphics g , int x , int y , int width , int height )
  {
    if ( img == null )
    {
      g.setColor ( Color.BLUE );
      g . fillRect ( x , y , width , height );
    }
    else
      drawScaled ( g , x , y , width , height );
  }
}