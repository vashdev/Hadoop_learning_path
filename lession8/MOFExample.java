package mout;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.IdentityMapper;
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.Arrays;

/**
 * An example of how to use {@link org.apache.hadoop.mapred.lib.MultipleOutputFormat}.
 */
public class MOFExample extends Configured implements Tool {

    /**
     * Create output files based on the output record's key name.
     */
    static class KeyBasedMultipleTextOutputFormat
                 extends MultipleTextOutputFormat<Text, Text> {
        @Override
        protected String generateFileNameForKeyValue(Text key, Text value, String name) {
System.out.println(" Value in filename :"+key+","+value+","+name);
            return key.toString() + "/" + name;

        }
@Override
    protected String getInputFileBasedOutputFileName(JobConf job, String name) {
        String infilename = new Path(job.get("map.input.file")).getName();
        return name + "-" + infilename;
}

 @Override
    protected Text generateActualKey(Text key, Text value) {
        return null;
}


    }


    /**
     * The main job driver.
     */
    public int run(final String[] args) throws Exception {
        String csvInputs = StringUtils.join(Arrays.copyOfRange(args, 0, args.length - 1), ",");
           System.out.println("Inputs "+ csvInputs);
        Path outputDir = new Path(args[args.length - 1]);

        JobConf jobConf = new JobConf(super.getConf());
        jobConf.setJarByClass(MOFExample.class);
        jobConf.setNumReduceTasks(0);
        jobConf.setMapperClass(IdentityMapper.class);

        jobConf.setInputFormat(KeyValueTextInputFormat.class);
        jobConf.setOutputFormat(KeyBasedMultipleTextOutputFormat.class);

        FileInputFormat.setInputPaths(jobConf, csvInputs);
        FileOutputFormat.setOutputPath(jobConf, outputDir);

        return JobClient.runJob(jobConf).isSuccessful() ? 0 : 1;
    }

    /**
     * Main entry point for the utility.
     *
     * @param args arguments
     * @throws Exception when something goes wrong
     */
    public static void main(final String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MOFExample(), args);
        System.exit(res);
    }
}


