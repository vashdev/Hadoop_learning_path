package invindx;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
public class InvindxMapper     

       extends Mapper<Object, Text, Text, Text>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
/* Weired behaviour is if i declare private Text word ,path =new Text() then path variable is NULL i forloop -reason ? Stupid java lang */
private Text path = new Text();
    private  List<String> filepathlist = new ArrayList<String>();
 
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

FileSplit split = (FileSplit) context.getInputSplit();
// split format is file:0+number(offset)
   String split_frmt = split.toString();
System.out.println(" Map Splitfrmt::  " +split_frmt);
path.set(split_frmt);
// handle value to create key
String[] result = value.toString().split("\\s");

 for (int x=0; x<result.length; x++){
        word.set(result[x]);
        context.write(word, path);
      }

    }
  }
