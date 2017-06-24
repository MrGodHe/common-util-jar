/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-22 18:42 创建
 *
 */
package cn.pay.ebank.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @author zhyang@ebank.pay.cn
 */
public class FileUtils {

	/**
	 * 获取文件路径
	 *
	 * @param strPath
	 * @return
	 */
	public static String splitPath (String strPath) {
		return new File (strPath).getParent ();
	}

	/**
	 * 获取文件名字，包括后缀
	 *
	 * @param strPath
	 * @return
	 */
	public static String splitName (String strPath) {
		return new File (strPath).getName ();
	}

	/**
	 * 获取文件名字，不包括后缀
	 *
	 * @param strPath
	 * @return
	 */
	public static String splitOnlyName (String strPath) {
		String fileName = splitName (strPath);

		return fileName.substring (0, fileName.lastIndexOf ("."));
	}

	/**
	 * @param strPath
	 * @return
	 * @throws java.io.FileNotFoundException Date
	 * @name getLastModified
	 * @author zhyang
	 * @date 2011-4-22
	 * @description 得到文件的最后修改时间
	 */
	public static Date getLastModified (String strPath) throws FileNotFoundException {
		File f = new File (strPath);
		if (f.exists ()) {
			return new Date (f.lastModified ());
		} else {
			throw new FileNotFoundException ();
		}

	}

	public static boolean mkdir (String path) {
		File file = new File (path);
		if (!file.exists ()) {
			return file.mkdirs ();
		}
		return true;
	}

	/**
	 * @param strFileName
	 * @param strRenameToName
	 * @return boolean
	 * @name rename
	 * @author zhyang
	 * @date 2011-4-22
	 * @description 重命名文件
	 */
	public static boolean rename (String strFileName, String strRenameToName) {
		File f = new File (strFileName);
		if (f.exists ()) {
			if (strRenameToName.indexOf ('/') < 0) {
				strRenameToName = (f.getParent () + "/" + strRenameToName).replace ("//", "/");
			}
			return f.renameTo (new File (strRenameToName));
		}
		return false;
	}

	/**
	 * @param strFilePath 文件路径
	 * @return 如果文件存在，返回true；否则返回false；
	 * @name isExists
	 * @author zhyang
	 * @date 2011-6-20
	 * @description 判断文件是否存在
	 */
	public static boolean isExists (String strFilePath) {
		File f = new File (strFilePath);
		return f.exists ();
	}

	public static boolean isFile (String strFilePath) {
		File f = new File (strFilePath);
		return f.isFile ();
	}
}
