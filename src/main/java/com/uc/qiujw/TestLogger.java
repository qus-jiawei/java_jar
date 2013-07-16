package com.uc.qiujw;

import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public class TestLogger implements Run {
	public static final Log MYLOG = LogFactory.getLog(TestLogger.class);

	public int run(String[] args) {
		try {
			// org.apache.hadoop.hdfs.DFSClient
			// FileSystem fs = FileSystem.get(new Configuration());
			DFSClient.LOG.warn("warn", new Exception("warn exception"));
			DFSClient.LOG.info("info", new Exception("info exception"));
			DFSClient.LOG.info("error", new Exception("error exception"));

			System.out.println(DFSClient.LOG.getClass().getName());
			Log4JLogger log = (org.apache.commons.logging.impl.Log4JLogger) DFSClient.LOG;
			Logger native_logger = log.getLogger();
			System.out.println(native_logger.getClass().getName());
			Enumeration apps = native_logger.getAllAppenders();
			while (apps.hasMoreElements()) {
				Object app = apps.nextElement();
				System.out.println(app.getClass().getName());
			}
			MYLOG.info("haha");
			Thread.sleep(1000000);
		} catch (Exception e) {

		}
		return 0;
	}

	public String getKey() {
		return "testlogger";
	}

}
