package main.tiles;

import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

public class Box extends Tile
{
  public Box()
  {
    try ( InputStream in = Player.class.getResourceAsStream("Box.png") )
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
      g . setColor ( Color.ORANGE );
      g . fillRect ( x , y , width , height );
    }
    else
      g.drawImage (img.getScaledInstance(width,height,Image.SCALE_FAST),x,y,null);
  }
}