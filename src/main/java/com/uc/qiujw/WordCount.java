/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uc.qiujw;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

	private final static String mapSleepKey = "com.uc.qiujw.map.sleep";
	private final static String reduceSleepKey = "com.uc.qiujw.reduce.sleep";

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		@Override
		public void setup(Context context) throws IOException,
				InterruptedException {
			printMem();
			int mapSleep = context.getConfiguration().getInt(mapSleepKey, 10);
			System.out.print(mapSleep);
			Thread.sleep(mapSleep * 1000);
		}

		@Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
		}

		@Override
		public void cleanup(Context context) throws IOException,
				InterruptedException {
			printMem();
			int mapSleep = context.getConfiguration().getInt(mapSleepKey, 10);
			System.out.print(mapSleep);
			Thread.sleep(mapSleep * 1000);
		}

	}

	public static class IntSumReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			printMem();
			int reduceSleep = context.getConfiguration().getInt(reduceSleepKey,
					1);
			System.out.print(reduceSleep);
			Thread.sleep(reduceSleep * 1000);
		}

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}

		@Override
		public void cleanup(Context context) throws IOException,
				InterruptedException {
			printMem();
			int reduceSleep = context.getConfiguration().getInt(reduceSleepKey,
					1);
			System.out.print(reduceSleep);
			Thread.sleep(reduceSleep * 1000);
		}

	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		System.err
				.println("Usage: wordcount  <in> <out> <map_sleep> <reduce_sleep>");

		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		String[] in = otherArgs[0].split(",");
		String out = otherArgs[1];
		for( String inStr:in){
			FileInputFormat.addInputPath(job, new Path(inStr));
		}
		FileOutputFormat.setOutputPath(job, new Path(out));
		int mapSleep = 1, reduceSleep = 1;
		if (otherArgs.length > 2) {
			mapSleep = Integer.valueOf(otherArgs[2]);
		}
		if (otherArgs.length > 3) {
			reduceSleep = Integer.valueOf(otherArgs[3]);
		}
		conf.set(mapSleepKey, Integer.toString(mapSleep));
		conf.set(reduceSleepKey, Integer.toString(reduceSleep));
		FileSystem fs = FileSystem.get(conf);
		Path outPath = new Path(out);
		if ( fs.exists(outPath) ){
			fs.delete(outPath,true);
		}
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static void printMem() {
		Runtime r = Runtime.getRuntime();
		long total = r.totalMemory();
		long free = r.freeMemory();

		System.out.println("total:" + total + " free:" + free);
	}
}
