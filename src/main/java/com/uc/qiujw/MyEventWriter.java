package com.uc.qiujw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.jobhistory.JobInitedEvent;

import util.EventWriter;

public class MyEventWriter implements Run {
	private final Object lock = new Object();

	public int run(String[] args) {
		EventWriter ew = null;
		try {
			int split = Integer.valueOf(args[0]) * 1000;
			boolean auto = false;
			if (args[1].equals("true")) {
				System.out.println("open the auto flush");
				auto = true;
			} else {
				System.out.println("close the auto flush");
			}

			FileSystem fs = FileSystem.get(new Configuration());
			String ts = Long.toString(System.currentTimeMillis());
			FSDataOutputStream out = fs.create(
					new Path("/tmp/eventwriter.log"+ts), true);
			ew = new EventWriter(out);
			// JobID id, long launchTime, int totalMaps,
			// int totalReduces, String jobStatus, boolean uberized
			JobInitedEvent event = new JobInitedEvent(new JobID(
					"job_1373357024953", 1), System.currentTimeMillis(), 10,
					10, "finish", false);
			int begin = 1000;
			// int split = 60000;
			if (auto) {
				Timer timer = new Timer();
				timer.schedule(new FlushTimerTask(ew), 30000);
			}
			while (true) {
				Thread.sleep(begin);
				ew.write(event);
				ew.flush();
				System.out.println("main flush event at " + time());
				begin += split;
			}

			// EventWriter
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if (ew != null){
				try{
					ew.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
		return 0;
	}

	static public SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	static public String time() {
		return sdf.format(new Date());
	}

	private class FlushTimerTask extends TimerTask {
		private EventWriter ew = null;
		private IOException ioe = null;

		FlushTimerTask(EventWriter ew) {
			this.ew = ew;
		}

		@Override
		public void run() {
			synchronized (lock) {
				try {
					ew.flush();
					System.out.println("auto flush event at " + time());
				} catch (IOException e) {
					ioe = e;
				}
			}
		}

		public IOException getException() {
			return ioe;
		}

	}

	public String getKey() {
		return "eventwriter";
	}

}
