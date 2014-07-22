package grep;

import org.apache.hadoop.util.*;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat; 
public class ToolGrep extends Configured implements Tool {
 
    public static void main(String[] args) throws Exception {
System.out.println("debug:Input path -args 0 "+ args[0]);
 System.out.println("debug:output path -args 1"+ args[1]);
System.out.println("number of Parameter passed "+ args.length);
 
        int res = ToolRunner.run(new Configuration(), new grep.ToolGrep(), args);

       System.exit(res);
    }
 
    @Override
    public int run(String[] args) throws Exception {
 
        // When implementing tool Configuration obj can be used to pass around parameters
        Configuration conf = this.getConf();
 
  // Debug NULLpointerissue for options in mapper - RESOLVED -D should be the first argument to the JAR
String s_ptrn=conf.get("mapred.mapper.regex");
System.out.println("debug: in Tool Class mapred.mapper.regex "+s_ptrn + "\n");
conf.set("DUMMYVAL","100000000000000000000000000000000000000");
// since passing value -D is not working - throwing NPL in mapper and this class we have no option but ot set a value here and read it in mapper
 // conf.set("mapred.mapper.regex",args[2]);
        // Create job
        Job job = new Job(conf, "Grep Job");
        job.setJarByClass(ToolGrep.class);
 
        // Setup MapReduce job
        job.setMapperClass(RegexMapper.class);
        job.setReducerClass(LongSumReducer.class);
 
        // Specify Correct key / value from map/reduce
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
 
        // Input
        FileInputFormat.addInputPath(job, new Path(args[0]));
       job.setInputFormatClass(TextInputFormat.class);
    System.out.println("Input path "+ args[0]);

        // Output
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
       job.setOutputFormatClass(TextOutputFormat.class);
 
    System.out.println("output path "+ args[1]);
   //  to sort we will just make reducer to 1 so frameowrk can sort automatically
    // Specify number of reducers
            job.setNumReduceTasks(1);
        // Execute job and return status
        return job.waitForCompletion(true) ? 0 : 1;
    }
}

