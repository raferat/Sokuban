package main.tiles;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.Icon;

public abstract class Tile implements Drawable
{
  protected Image img = null;
  
  protected void drawScaled ( Graphics g , int x , int y , int width , int height )
  {
    g . drawImage ( img.getScaledInstance(width,height,Image.SCALE_FAST),x,y,null);
  }
  
  protected void drawScaled ( Graphics g , Image img , int x , int y , int width , int height )
  {
    g . drawImage ( img.getScaledInstance(width,height,Image.SCALE_FAST),x,y,null);
  }
  
  public Icon getIcon ()
  {
    return new ImageIcon (img);
  }
}
