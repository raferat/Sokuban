import javax.tools.*;

import java.util.jar.*;
import java.util.zip.ZipEntry;

import java.util.Arrays;
import java.util.ArrayList;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Compile
{
  private static boolean javaFilter ( File dir )
  {
    return dir.getName().endsWith(".java") || dir.isDirectory();
  }
  
  private static boolean invertedJavaFilter ( File dir )
  {
    return (! dir.getName().endsWith(".java") ) || dir.isDirectory();
  }
  
  
  public static ArrayList<File> getFilesIn ( File dir )
  {
    ArrayList <File> files = new ArrayList<>();
    for ( File f : dir.listFiles(Compile::javaFilter) )
      if ( f.isDirectory() )
        files.addAll ( getFilesIn ( f ) );
      else
        files.add(f);
      
    return files;
  }
  
  private static void jar ( File f , String mainClass )
  {
    Manifest mf = new Manifest ();
    Attributes attrs = mf.getMainAttributes ();
    
    attrs . put ( Attributes.Name.MAIN_CLASS , mainClass );
    attrs . put ( Attributes.Name.MANIFEST_VERSION , "0.118.999.881.999.197.253" );
    
    try ( JarOutputStream jos = new JarOutputStream ( new FileOutputStream ( f ) , mf ) )
    {
      zipFile ( new File ( "main" ) , "main" , jos );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }
  }
  
  private static void zipFile ( File fileToZip , String fileName , JarOutputStream jarOut ) throws IOException
  {
    if (fileToZip.isHidden()) 
    {
      return;
    }
    
    if (fileToZip.isDirectory()) 
    {
      if (fileName.endsWith("/")) 
      {
        jarOut.putNextEntry(new ZipEntry(fileName));
        jarOut.closeEntry();
      }
      else 
      {
        jarOut.putNextEntry(new ZipEntry(fileName + "/"));
        jarOut.closeEntry();
      }
      
      File[] children = fileToZip.listFiles(Compile::invertedJavaFilter);
      for (File childFile : children) 
      {
        zipFile ( childFile , fileName + "/" + childFile.getName() , jarOut );
      }
      
      return;
    }
    
    FileInputStream fis = new FileInputStream(fileToZip);
    
    ZipEntry zipEntry = new ZipEntry(fileName);
    
    jarOut.putNextEntry(zipEntry);
    
    byte[] bytes = new byte[2048];
    int length;
    while ( ( length = fis.read(bytes) ) >= 0 ) 
    {
      jarOut.write ( bytes , 0 , length );
    }
    
    fis.close();
  }
  
  private static void compile ( String file )
  {
    File f = new File(file);
    if ( ! f.exists() )
    {
      System.out.println("File: " + file + " does not exist." );
      return;
    }
    ArrayList<File> files1 = getFilesIn(f);
    files1.remove(files1.size()-1);
    
    System.out.println(Arrays.toString(files1.toArray(new File[files1.size()])));
    
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

    Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(files1);
    compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
  }
  
  private static void compile ()
  {
    compile(".");
  }
  

  public static void main ( String [] args )
  {
    if ( args.length < 1 ) 
    {
      compile();
      return;
    }
    
    switch ( args[0] )
    {
      case "jar":
        jar ( new File ( "Sokoban.jar" ) , "main.Main" );
        break;
      default:
        compile ( args[0] );
        break;
    }
  }
  
}