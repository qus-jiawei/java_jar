package com.uc.qiujw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.jobhistory.EventType;
import org.apache.hadoop.mapreduce.jobhistory.HistoryEvent;
import org.apache.hadoop.mapreduce.jobhistory.JobInitedEvent;

import util.EventWriter;

public class MyEventWriter implements Run {
	static public final Object lock = new Object();
	static public int unFlusheEventNumber=0;
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
			FSDataOutputStream out = fs.create(new Path("/tmp/eventwriter.log"
					+ ts), true);
			ew = new EventWriter(out);
			// JobID id, long launchTime, int totalMaps,
			// int totalReduces, String jobStatus, boolean uberized
			JobInitedEvent event = new JobInitedEvent(new JobID(
					"job_1373357024953", 1), System.currentTimeMillis(), 10,
					10, "finish", false);
			if (auto) {
//				Timer timer = new Timer();
//				timer.schedule(new FlushTimerTask(ew), 30000);
			}
			while (true) {
				ew.write(event);
				unFlusheEventNumber++;
				processEventForFlush(event,ew);
//				System.out.println("main flush event at " + time());
				Thread.sleep(split);
				// begin += split;
			}

			// EventWriter
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if (ew != null) {
				try {
					ew.close();
				} catch (Exception e) {
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

	static private boolean isTimerActive = false;
	static private boolean isTimerShutDown = false;
	static private Timer flushTimer = new Timer();
	static private FlushTimerTask flushTimerTask ;
	static private long flushTimeout = 30000l;
	static public void processEventForFlush(HistoryEvent historyEvent,EventWriter ew)
			throws IOException {
		if (EnumSet.of(EventType.MAP_ATTEMPT_FINISHED,
				EventType.MAP_ATTEMPT_FAILED, EventType.MAP_ATTEMPT_KILLED,
				EventType.REDUCE_ATTEMPT_FINISHED,
				EventType.REDUCE_ATTEMPT_FAILED,
				EventType.REDUCE_ATTEMPT_KILLED, EventType.TASK_FINISHED,
				EventType.TASK_FAILED, EventType.JOB_FINISHED,
				EventType.JOB_FAILED, EventType.JOB_KILLED).contains(
				historyEvent.getEventType())) {
			if (!isTimerActive) {
				resetFlushTimer();
				if (!isTimerShutDown) {
					flushTimerTask = new FlushTimerTask(ew);
					flushTimer.schedule(flushTimerTask, flushTimeout);
				}
			}
		}
	}

	static public void resetFlushTimer() throws IOException {
		if (flushTimerTask != null) {
//			IOException exception = flushTimerTask.getException();
			flushTimerTask.stop();
//			if (exception != null) {
//				throw exception;
//			}
			flushTimerTask = null;
		}
		isTimerActive = false;
	}

	static public class FlushTimerTask extends TimerTask {
		private EventWriter ew = null;
		private IOException ioe = null;

		public FlushTimerTask(EventWriter ew) {
			this.ew = ew;
		}

		@Override
		public void run() {
			synchronized (lock) {
				try {
					ew.flush();
					unFlusheEventNumber=0;
					System.out.println("auto flush event at " + time());
				} catch (IOException e) {
					ioe = e;
				}
			}
		}

		public IOException getException() {
			return ioe;
		}
		
		public void stop(){
			this.cancel();
		}
	}

	public String getKey() {
		return "eventwriter";
	}

}
