package Distcache;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.conf.Configuration;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class RecordlkpMap

       extends Mapper<Object, Text, IntWritable, Text>{
    
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
             HashMap map = new HashMap();
             Text  rec =new Text();
       String[] splitted = value.toString().split("\\s+");
  
       System.out.println("Cheking source input "+splitted[0]);
           
          int id = Integer.parseInt(splitted[0]);
          String val= splitted[1];

       // lets assume we are adding one file , read and populate map
	Configuration conf = context.getConfiguration();
Path[] cacheFiles =  DistributedCache.getLocalCacheFiles(conf);
System.out.println("File path"+ cacheFiles[0].toString());
try{
FileInputStream fileInStreamObj = new FileInputStream(cacheFiles[0].toString());
InputStream inStreamObject = ((InputStream) fileInStreamObj);
Scanner sc = new Scanner( inStreamObject );
while ( sc.hasNextLine()) {
String input = sc.nextLine();
// discard empty lines 
if(input.trim().length()>0){
System.out.println("Print eachline"+input);
           String[] splitted2 = input.split("\\s+");
              System.out.println("Print eSplit array "+ splitted2[0]+","+splitted2[1]);
                int id2 = Integer.parseInt(splitted2[0]);
          String val2= splitted2[1];
    map.put(id2,val2);
}
		}
}
catch (FileNotFoundException e){
System.out.println(e);
}
if(map.containsKey(id)){
 rec.set(new String(val+","+ map.get(id)));
}

        context.write(new IntWritable(id), rec);


}    }
