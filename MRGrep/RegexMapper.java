package grep;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.apache.hadoop.conf.Configured;

public class RegexMapper

       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
  	private int group;
	private Pattern pattern;


    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
Configuration conf = context.getConfiguration();
String s_ptrn=conf.get("mapred.mapper.regex");
System.out.println("debug: mapred.mapper.regex "+s_ptrn + "\n");
    pattern = Pattern.compile(conf.get("mapred.mapper.regex"));
    group = conf.getInt("mapred.mapper.regex.group", 0);

// get record 
          String text = value.toString();
// use matcher object to perform match on pattern
Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
// Write the output to mapper context 
context.write(new Text(matcher.group(group)), one);
}
    }
  }
