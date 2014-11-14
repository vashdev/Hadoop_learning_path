package invindx;
 import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
 
    
    public class Writetofile {
    private String path;
    public Writetofile(String fname){
    	this.path=fname;
    }
    public File makefile(String fname) {
    this.path=fname;
    return new File(path);
       
    }
public File nfile() {
    return new File(path);

    }

    public void writeToFile( String textLine ) throws IOException {
    FileWriter write = new FileWriter(path,true);
    write.write(textLine);

    }
    

    } 
