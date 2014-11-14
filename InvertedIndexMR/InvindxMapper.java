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

/* Class to read a text docuemnt as input and emit <word filepath>
*/

public class InvindxMapper     

       extends Mapper<Object, Text, Text, Text>{
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

 Text word =new Text();
Text path ;

FileSplit split = (FileSplit) context.getInputSplit();
String fName=split.getPath().getName();
// handle value to create key

String rec = value.toString();
StringTokenizer tokenizer = new StringTokenizer(rec);
	while(tokenizer.hasMoreTokens()) {
						String token = tokenizer.nextToken();
						context.write(new Text(token.toLowerCase()), new Text(fName));
      					}

    }
  }
