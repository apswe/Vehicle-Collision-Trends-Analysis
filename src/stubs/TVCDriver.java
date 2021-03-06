package stubs;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

/* 
 * Author: Akhilesh A Borgaonkar
 * UB ID: 0997553
 * Course: CPSC-651-DL-Big Data Systems & Analysis-2016
 * Title: Analyzing trends in Vehicle Collisions
 *
 * The following is the code for the driver class:
 */
public class TVCDriver {

  public static void main(String[] args) throws Exception {

    /*
     * The expected command-line arguments are the paths containing
     * input and output data. Terminate the job if the number of
     * command-line arguments is not exactly 2.
     */
    if (args.length != 2) {
      System.out.printf(
          "Usage: TVC <input dir> <output dir>\n");
      System.exit(-1);
    }

    /*
     * Instantiate a Job object for your job's configuration.  
     */
    Job job = new Job();
    
    /*
     * Specify the jar file that contains your driver, mapper, and reducer.
     * Hadoop will transfer this jar file to nodes in your cluster running
     * mapper and reducer tasks.
     */
    job.setJarByClass(TVCDriver.class);
    
    /*
     * Specify an easily-decipherable name for the job.
     * This job name will appear in reports and logs.
     * Set number of reducers to 5 as 5 partitions are required for 5 years. Each partition for a year.
     */

    job.setJobName("TVCjob");
    job.setNumReduceTasks(5);

    /*
     * Specify the paths to the input and output data based on the
     * command-line arguments.
     */
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    /*
     * Specify the mapper, combiner, partitioner and reducer classes.
     */
    job.setMapperClass(TVCMapper.class);
    job.setCombinerClass(TVCReducer.class);
    job.setPartitionerClass(TVCPartitioner.class);
    job.setReducerClass(TVCReducer.class);

    /*
     * 
     * In text format files,each field is delimited by comma and each record is a line delineated by a 
     * by a line terminator.
     * 
     * When you use other input formats, you must call the 
     * SetInputFormatClass method. When you use other 
     * output formats, you must call the setOutputFormatClass method.
     */
      
    /*
     * For the word count application, the mapper's output keys and
     * values have the same data types as the reducer's output keys 
     * and values: Text and IntWritable.
     * 
     * When they are not the same data types, you must call the 
     * setMapOutputKeyClass and setMapOutputValueClass 
     * methods.
     */

    /*
     * Specify the job's output key and value classes.
     */
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    /*
     * Start the MapReduce job and wait for it to finish.
     * If it finishes successfully, return 0. If not, return 1.
     */
    boolean success = job.waitForCompletion(true);
    System.exit(success ? 0 : 1);
  }
}
