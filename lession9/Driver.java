package Distcache;

import org.apache.hadoop.util.*;
import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.conf.Configuration;
import java.net.URI;
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
public class Driver extends Configured implements Tool {
 
    public static void main(String[] args) throws Exception {
System.out.println("debug:Input path "+ args[0]);
System.out.println("debug:output path "+ args[1]);

 
        int res = ToolRunner.run(new Configuration(), new Distcache.Driver(), args);

       System.exit(res);
    }
 
    @Override
    public int run(String[] args) throws Exception {
 
        // When implementing tool
        Configuration conf = this.getConf();
 
        // Create job
        Job job = new Job(conf, "Tool Job");
        job.setJarByClass(Driver.class);
 
        DistributedCache.addCacheFile(new URI("/home/hduser/hadoopData/StudentLname.dat"), job.getConfiguration());
        // Setup MapReduce job
        // Do not specify the number of Reducer
        job.setMapperClass(RecordlkpMap.class);
        // job.setReducerClass(WordCountReduce.class);
        // Specify Correct key / value from map/reduce
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
 
        // Input
        FileInputFormat.addInputPath(job, new Path(args[0]));
       job.setInputFormatClass(TextInputFormat.class);
    System.out.println("Input path "+ args[0]);

        // Output
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
       job.setOutputFormatClass(TextOutputFormat.class);
 
    System.out.println("output path "+ args[1]);
        // Execute job and return status
        return job.waitForCompletion(true) ? 0 : 1;
    }
}

