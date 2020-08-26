package Sample;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CharacterCount {

public static class AlphabetWordCountMapper extends Mapper<LongWritable,Text,IntWritable,IntWritable>{

 // here we are declaring a final static local variable of static type

 public static final IntWritable ONE = new IntWritable(1);

  @Override

  public void map(LongWritable key, Text value, Context cont) throws IOException,InterruptedException{

   String line = value.toString();

   for (String word : line.split(" ")) {

       System.out.println("This is a Alphabet count problem");

    cont.write(new IntWritable(word.length()), ONE);

    }

  }

 }

 public static class AlphabetWordCountReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>{

  @Override

  public void reduce(IntWritable key, Iterable<IntWritable> value, Context cont) throws IOException,InterruptedException{

   int count = 0;

   for (IntWritable values : value) {

   count += values.get();

      }

   cont.write(key, new IntWritable(count));

   }

   }

 public static void main(String[] args) throws ClassNotFoundException,IOException,InterruptedException{

  // TODO Auto-generated method stub

  Configuration conf = new Configuration();

  Job job = Job.getInstance(conf, "WordCount");

  job.setMapperClass(AlphabetWordCountMapper.class);

  job.setReducerClass(AlphabetWordCountReducer.class);

  job.setCombinerClass(AlphabetWordCountReducer.class);

  job.setMapOutputKeyClass(IntWritable.class);

  job.setMapOutputValueClass(IntWritable.class);

  job.setOutputKeyClass(IntWritable.class);

  job.setOutputValueClass(IntWritable.class);

  job.setJarByClass(CharacterCount.class);

  FileInputFormat.addInputPath(job, new Path(args[0]));

  FileOutputFormat.setOutputPath(job, new Path(args[1]));

  System.exit(job.waitForCompletion(true) ? 0 : 1);

 }
}
