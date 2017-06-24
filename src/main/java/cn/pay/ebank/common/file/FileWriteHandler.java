/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-30 14:47 创建
 *
 */
package cn.pay.ebank.common.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhyang@ebank.pay.cn
 */
public class FileWriteHandler {
	private File file;

	//文件写入类
	FileWriter writer;

	public FileWriteHandler (String file) {
		this (new File (file));
	}

	public FileWriteHandler (File file) {
		this.file = file;
	}

	public boolean isExists () {
		return file.exists ();
	}

	public boolean creatNew () {
		return open (false);
	}

	/**
	 * 打开文件
	 */
	private synchronized boolean open (boolean append) {
		try {

			File dir = file.getParentFile ();
			if (!dir.exists ()) {
				dir.mkdirs ();
			}
			writer = new FileWriter (file, append);
			return true;
		} catch (IOException e) {
			e.printStackTrace ();
		}
		return false;
	}

	/**
	 * 追加文件：使用FileWriter
	 *
	 * @param content
	 */
	public synchronized boolean append (String content) {
		if (writer == null) {
			if (!open (true)) {
				return false;
			}
		}
		try {
			writer.write (content);
			return true;
		} catch (IOException e) {
			e.printStackTrace ();
		}
		return false;
	}

	/**
	 * 关闭文件
	 */
	public void close () {
		if (writer != null) {
			try {
				writer.close ();
				writer = null;
			} catch (IOException e) {
				e.printStackTrace ();
			}
		}
	}

	//	/**
	//	 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
	//	 *
	//	 * @param fileName
	//	 * @param content
	//	 */
	//	public void WriteStreamAppend (String strFilePath, String conent) {
	//		BufferedWriter out = null;
	//		try {
	//			out = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (strFilePath, true)));
	//			out.write (conent);
	//		} catch (Exception e) {
	//			e.printStackTrace ();
	//		} finally {
	//			try {
	//				out.close ();
	//			} catch (IOException e) {
	//				e.printStackTrace ();
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * 追加文件：使用FileWriter
	//	 *
	//	 * @param content
	//	 */
	//	public void method2 (String strFilePath, String content) {
	//		try {
	//			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
	//			FileWriter writer = new FileWriter (strFilePath, true);
	//			writer.write (content);
	//			writer.close ();
	//		} catch (IOException e) {
	//			e.printStackTrace ();
	//		}
	//	}
	//
	//	/**
	//	 * 追加文件：使用RandomAccessFile
	//	 *
	//	 * @param content 文件名
	//	 * @param content  追加的内容
	//	 */
	//	public void method3 (String strFilePath, String content) {
	//		try {
	//			// 打开一个随机访问文件流，按读写方式
	//			RandomAccessFile randomFile = new RandomAccessFile (strFilePath, "rw");
	//			// 文件长度，字节数
	//			long fileLength = randomFile.length ();
	//			// 将写文件指针移到文件尾。
	//			randomFile.seek (fileLength);
	//			randomFile.writeBytes (content);
	//			randomFile.close ();
	//		} catch (IOException e) {
	//			e.printStackTrace ();
	//		}
	//	}
}
