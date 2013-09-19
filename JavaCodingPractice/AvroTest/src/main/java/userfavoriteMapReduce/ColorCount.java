package userfavoriteMapReduce;

import java.io.IOException;

import org.apache.avro.*;
import org.apache.avro.Schema.Type;
import org.apache.avro.mapred.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.*;

import userfavorite.User;


public class ColorCount extends Configured implements Tool {

  public static class ColorCountMapper extends AvroMapper<User, Pair<CharSequence, Integer>> {
    @Override
    public void map(User user, AvroCollector<Pair<CharSequence, Integer>> collector, Reporter reporter)
        throws IOException {
      CharSequence color = user.getColor();
      // We need this check because the User.favorite_color field has type ["string", "null"]
      if (color == null) {
        color = "none";
      }
      collector.collect(new Pair<CharSequence, Integer>(color, 1));
    }
  }

  public static class ColorCountReducer extends AvroReducer<CharSequence, Integer,
                                                            Pair<CharSequence, Integer>> {
    @Override
    public void reduce(CharSequence key, Iterable<Integer> values,
                       AvroCollector<Pair<CharSequence, Integer>> collector,
                       Reporter reporter)
        throws IOException {
      int sum = 0;
      for (Integer value : values) {
        sum += value;
      }
      collector.collect(new Pair<CharSequence, Integer>(key, sum));
    }
  }

  public int run(String[] args) throws Exception {
	  Configuration configuration = getConf();
	  String inputPathStr = configuration.get("input", null);
	  String outputPathStr = configuration.get("output", null);

	  Job job = new Job(configuration);
	  job.setJarByClass(this.getClass());

//	  FileInputFormat.setInputPaths(job, inputPathStr);
//	  FileOutputFormat.setOutputPath(job, new Path(outputPathStr));

	  JobConf conf = new JobConf(getConf(), ColorCount.class);
	  conf.setJobName("colorcount");

	  FileInputFormat.setInputPaths(conf, new Path(inputPathStr));
	  FileOutputFormat.setOutputPath(conf, new Path(outputPathStr));

	  AvroJob.setMapperClass(conf, ColorCountMapper.class);
	  AvroJob.setReducerClass(conf, ColorCountReducer.class);

	  // Note that AvroJob.setInputSchema and AvroJob.setOutputSchema set
	  // relevant config options such as input/output format, map output
	  // classes, and output key class.
	  AvroJob.setInputSchema(conf, User.SCHEMA$);
	  AvroJob.setOutputSchema(conf, Pair.getPairSchema(Schema.create(Type.STRING),
	        Schema.create(Type.INT)));

	  JobClient.runJob(conf);
	  return 0;
  }

  public static void main(String[] args) throws Exception {
	  int res = ToolRunner.run(new Configuration(), new ColorCount(), args);
	  System.exit(res);
  }
}
