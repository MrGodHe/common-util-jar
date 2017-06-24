/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 18:48 创建
 *
 */
package cn.pay.ebank.common.utils;

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * 摘要密文工具
 *
 * @author zhyang@ebank.pay.cn
 */
public class EncryptionUtils {
	public enum DigestALGEnum {
		SHA,
		MD5;
	}

	public static byte[] digestData(String data, DigestALGEnum alg) {

		byte[] cData = new byte[] {};
		try {
			MessageDigest digest = MessageDigest.getInstance(alg.toString());

			String firstLetter = data.substring(0, 1);
			String lastLetter = data.substring(data.length() - 1);
			String tmpData = firstLetter + "<|" + data + "|>" + lastLetter;

			tmpData = data + tmpData + data;
			digest.update(tmpData.getBytes("UTF8"));

			cData = digest.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cData;
	}

	public static String digestDataStr(String data, DigestALGEnum alg) {
		return new String(digestData(data, alg), Charsets.UTF_8);
	}

	public static char[] encodeWithHex(byte[] data) {
		return Hex.encodeHex (data);
	}

	public static String encodeWithHexStr(String data, DigestALGEnum alg) {
		return new String(encodeWithHex(digestData(data, alg)));
	}

	public static String encodeWithHexStrNoDigest(String data) {
		byte[] pData = new byte[] {};
		try {
			pData = data.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Hex.encodeHex(pData));
	}

	public static String decodeWithHexStrNoDigest(String data) {
		char[] cData;
		byte[] pData = new byte[] {};
		try {
			cData = data.toCharArray();
			pData = Hex.decodeHex(cData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(pData, Charsets.UTF_8);
	}
}
