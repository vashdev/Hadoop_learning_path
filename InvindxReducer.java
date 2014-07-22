package invindx;
import java.io.IOException;
import java.util.StringTokenizer;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
  public class InvindxReducer      
       extends Reducer<Text,Text,Text,Text> {
Text result =new Text();
    public void reduce(Text key, Iterable<Text> values, 
                       Context context
                       ) throws IOException, InterruptedException {

// Get word  occurances in in individual documents
// mapper emits word filename for every word
/*
from mapper 1
word1 file1:offset
word1 file1:offset
word2 file1:offset
word3 file1:offset
from mapper 2
word1 file2:offset
word1 file2:offset
word3 file2:offset
word5 file2:offset
REDUCER
word1 file1 file1 file2 withoffsets
word2 file1 
word3 file2
*/

      int sum = 0;
int total_wc=0;
int indv_wc= 0;
String indv_file="";
ArrayList<String> valuelist=new ArrayList<String>();


      for (Text val : values) {
// add code to create a list with these values and sum it up too because Iterable only traverses oneway Once
  valuelist.add(val.toString());
      }
// this is the total number of times word appeared in the input files
     total_wc=valuelist.size();
System.out.println(" total # occurances for "+key+ " "+ Integer.toString(total_wc));
// Now we need to get unique files with offset this word appears in 
HashSet<String> hs = new HashSet<String>(valuelist);
valuelist.clear();
valuelist.addAll(hs);
// append our list to string and write result

String listString = "";

for (String s : valuelist)
{
String[] file_offset = s.split("0\\+");
System.out.println(" Array contents "+file_offset[0]+" "+file_offset[1]+ " ");
    listString += file_offset[1] + "\t" + file_offset[0];
}

System.out.println("un-formated o/p"+"\t"+listString);
String rs= Integer.toString(total_wc)+ "\t"+ listString;

System.out.println("formated o/p"+"\t"+rs);
result.set(rs);
 context.write(key, result);
    }
  }
