package main.help;

public class IllegalMoveException extends RuntimeException
{
  public IllegalMoveException ( String text )
  {
    super ( text );
  }
}