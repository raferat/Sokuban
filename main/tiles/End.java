package main.tiles;

import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

public class End extends Tile
{
  private boolean occupied = false;
  
  private Image occupiedImg;
  
  public End ()
  {
    try ( InputStream in = Player.class.getResourceAsStream("End.png") )
    {
      img = ImageIO.read(in);
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
    
    try ( InputStream in = Player.class.getResourceAsStream("End-on-v2.png") )
    {
      occupiedImg = ImageIO.read(in);
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
    
  public boolean isOccupied ()
  {
    return occupied;
  }
  
  public void setOccupied ( boolean b )
  {
    occupied = b;
  }
  
  @Override
  public void draw ( Graphics g , int x , int y , int width , int height )
  {
    if ( occupied && occupiedImg == null )
    {
      g.setColor ( Color.GREEN );
      g . fillRect ( x , y , width , height );
    }
    else if ( occupied )
    {
      drawScaled ( g , occupiedImg , x , y , width , height );
    }
    else if ( img == null )
    {
      g.setColor ( Color.YELLOW );
      g . fillRect ( x , y , width , height );
    }
    else
      drawScaled ( g , x , y , width , height );
  }
}