package com.uc.qiujw;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class TryDfsClient implements Run {

	public int run(String[] args) {
		int time = Integer.valueOf(args[0]);
		FSDataOutputStream out = null;
		try {
			FileSystem fs = FileSystem.get(new Configuration());
			out = fs.append(new Path("/tmp/tryclient"));
			while (true) {
				Thread.sleep(time * 1000);
				out.write("haha".getBytes());
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;

	}

	public String getKey() {

		return "tryclient";
	}

}
