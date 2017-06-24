package cn.pay.ebank.common.utils;


import cn.pay.ebank.common.exception.CommonException;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zhyang on 2014/12/9 0009.
 */
public abstract class StringUtils {

	/**
	 * 空的 {@link String} 数组
	 */
	public static final String[] EMPTY_STRINGS = {};
	/**
	 * 空字符串。
	 */
	public static final String EMPTY_STRING = "";
	private static final Pattern MAIL_REGEX = Pattern.compile (".+@.+\\.[a-zA-Z]{2,}");
	public static final String DATE_REGEX = "(?:[0-9]{1,4}(?<!^0?0?0?0))-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29)))(-(((0?[0-9])|(1[0-9])|(2[0-3]))-([0-5]?[0-9])((-([0-5]?[0-9]))?)))?$";
	public static final String IP_REGEX = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";


	/**
	 * 检查字符串中是否包含指定的字符。如果字符串为<code>null</code>，将返回<code>false</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 *
	 * @param str 要扫描的字符串
	 * @param searchChar 要查找的字符
	 * @return 如果找到，则返回<code>true</code>
	 */
	public static boolean contains(String str, char searchChar) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}

		return str.indexOf(searchChar) >= 0;
	}

	/**
	 * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 *
	 * @param str 要扫描的字符串
	 * @param searchStr 要查找的字符串
	 * @return 如果找到，则返回<code>true</code>
	 */
	public static boolean contains(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return false;
		}

		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * <p/>
	 * <p/>
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str 要检查的字符串
	 * @return 如果为空白, 则返回<code>true</code>
	 */
	public static boolean isBlank (String str) {
		int length;

		if ((str == null) || ((length = str.length ()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace (str.charAt (i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * <p/>
	 * <p/>
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str 要检查的字符串
	 * @return 如果为空白, 则返回<code>true</code>
	 */
	public static boolean isNotBlank (String str) {
		int length;

		if ((str == null) || ((length = str.length ()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace (str.charAt (i))) {
				return true;
			}
		}

		return false;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 比较函数。 */
	/*                                                                              */
	/* 以下方法用来比较两个字符串是否相同。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 比较两个字符串（大小写敏感）。
	 * <p/>
	 * <p/>
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 *
	 * @param str1 要比较的字符串1
	 * @param str2 要比较的字符串2
	 * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
	 */
	public static boolean equals (String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}
		return str1.equals (str2);
	}

	/**
	 * 比较两个字符串（大小写不敏感）。
	 * <p/>
	 * <p/>
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @param str1 要比较的字符串1
	 * @param str2 要比较的字符串2
	 * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
	 */
	public static boolean equalsIgnoreCase (String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase (str2);
	}


	/**
	 * Check whether the given String has actual text.
	 * More specifically, returns {@code true} if the string not {@code null},
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 *
	 * @param str the String to check (may be {@code null})
	 * @return {@code true} if the String is not {@code null}, its length is
	 * greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText (String str) {
		return hasText ((CharSequence) str);
	}

	/**
	 * Check that the given String is neither {@code null} nor of length 0.
	 * Note: Will return {@code true} for a String that purely consists of whitespace.
	 *
	 * @param str the String to check (may be {@code null})
	 * @return {@code true} if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength (String str) {
		return hasLength ((CharSequence) str);
	}


	/**
	 * 左侧填充字符串
	 *
	 * @param str     待填充字符串
	 * @param len     填充后的位数
	 * @param padding 填充字符
	 */
	public static String lpad (String str, int len, char padding) {
		if (str.length () < len) {
			StringBuilder sb = new StringBuilder (len);
			int toPadLen = len - str.length ();
			for (int i = 1; i <= toPadLen; i++) {
				sb.append (padding);
			}
			sb.append (str);
			return sb.toString ();
		} else {
			return str;
		}
	}

	/**
	 * 右侧填充字符串
	 *
	 * @param str     待填充字符串
	 * @param len     填充后的位数
	 * @param padding 填充字符
	 */
	public static String rpad (String str, int len, char padding) {
		if (str.length () < len) {
			StringBuilder sb = new StringBuilder (len);
			sb.append (str);

			int toPadLen = len - str.length ();
			for (int i = 1; i <= toPadLen; i++) {
				sb.append (padding);
			}
			return sb.toString ();
		} else {
			return str;
		}
	}

	/**
	 * 获取字符串长度
	 *
	 * @param str
	 * @return
	 */
	public static int length (String str) {
		if (isEmpty (str)) {
			return 0;
		} else {
			return str.length ();
		}
	}

	/**
	 * 验证字符串长度,并返回结果<br>
	 * <pre>
	 * StringValidator.validateLength("abcde",10) = "abcde"
	 * StringValidator.validateLength("abcde",2) = "ab"
	 * StringValidator.validateLength("",2) = ""
	 * StringValidator.validateLength(null,2) = null
	 * </pre>
	 *
	 * @param str 输入字符串
	 * @param len 验证长度
	 * @return 如果输入大于验证长度, 则截取该长度子串返回；否则返回原输入
	 */
	public static String validateLength (String str, int len) {
		if (isNotBlank (str) && str.length () > len) {
			return substring (str, 0, len);
		}
		return str;
	}

	/**
	 * 判断字符串是否在给定字符串中 区分大小写
	 *
	 * @param str
	 * @param arrays
	 * @return
	 */
	public static boolean inArray (String str, String... arrays) {
		if (ArrayUtils.isEmpty (arrays)) {
			return false;
		} else {
			for (String tmp : arrays) {
				if (equals (str, tmp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否在给定字符串中 忽略大小写
	 *
	 * @param str
	 * @param arrays
	 * @return
	 */
	public static boolean inArrayIgnoreCase (String str, String... arrays) {
		if (ArrayUtils.isEmpty (arrays)) {
			return false;
		} else {
			for (String tmp : arrays) {
				if (equalsIgnoreCase (str, tmp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 省略字符。
	 *
	 * @param source 要省略的字符。
	 * @param len    字符长度。
	 * @param delim  代替省略字符的字符串。
	 * @return 省略后的字符。
	 */
	public static String strTruncate (String source, int len, String delim) {
		if (source == null) {
			return null;
		}
		int start, stop, byteLen;
		int alen = source.getBytes ().length;
		if (len > 0) {
			if (alen <= len) {
				return source;
			}
			start = stop = byteLen = 0;
			while (byteLen <= len) {
				if (source.substring (stop, stop + 1).getBytes ().length == 1) {
					byteLen += 1;
				} else {
					byteLen += 2;
				}
				stop++;
			}
			if (alen > len) {
				StringBuilder sb = new StringBuilder (source.substring (start, stop - 1));
				sb.append (delim);
			}
			return source.substring (start, stop - 1);
		}
		return source;
	}

	/**
	 * 将一个字符串指定位置的字符变为大写。
	 *
	 * @param str   字符串。
	 * @param index 指定位置,该数必须大于等于1,小于等于字符串长度。
	 * @return 改变后的字符串。
	 * @throws IndexOutOfBoundsException 如果 index 大于 <code>str.length()</code>。
	 */
	public static String toUpperCase (String str, int index) {
		StringBuilder sb = new StringBuilder (str);
		char s = Character.toUpperCase (sb.charAt (index - 1));
		sb.setCharAt (index - 1, s);
		return sb.toString ();
	}

	/**
	 * 将一个字符串指定位置的字符变为小写。
	 *
	 * @param str   字符串。
	 * @param index 指定位置,该数必须大于等于1,小于等于字符串长度。
	 * @return 改变后的字符串。
	 * @throws IndexOutOfBoundsException 如果 index 大于 <code>str.length()</code>。
	 */
	public static String toLowerCase (String str, int index) {
		StringBuilder sb = new StringBuilder (str);
		char s = Character.toLowerCase (sb.charAt (index - 1));
		sb.setCharAt (index - 1, s);
		return sb.toString ();
	}

	/**
	 * 将参数转换为 {@link String} 。 如果 param 为 null 则返回 null 。如果参数为 {@link String}
	 * 类型则直接转换为 {@link String} ，否则调用参数的 {@link Object#toString()} 方法转换为
	 * {@link String} 。
	 *
	 * @param param 要转换的参数。
	 * @return 转换后的参数。
	 */
	public static String toString (Object param) {
		return toString (param, null);
	}

	/**
	 * 将参数转换为 {@link String} 。 如果 param 为 null 则返回 null 。如果参数为 {@link String}
	 * 类型则直接转换为 {@link String} ，否则调用参数的 {@link Object#toString()} 方法转换为
	 * {@link String} 。
	 *
	 * @param param         要转换的参数。
	 * @param defaultString 如果param为<code>null</code>的默认返回结果。
	 * @return 转换后的参数。
	 */
	public static String toString (Object param, String defaultString) {
		if (param == null) {
			return defaultString;
		}
		return param.toString ();
	}

	/**
	 * 判断string是否为空。
	 *
	 * @param string 要判断的String。
	 * @return 如果String为 null 或者 {@link String#trim()} 为 "" ，则返回 true。
	 */
	public static boolean isEmpty (String string) {
		return (string == null) || ("".equals (string.trim ()));
	}

	/**
	 * 判断string是否不为空。
	 *
	 * @param string 要判断的String。
	 * @return 如果String不为 null 且 {@link String#trim()} 不为 "" ，则返回 true。
	 */
	public static boolean isNotEmpty (String string) {
		return (string != null) && (!"".equals (string.trim ()));
	}

	/**
	 * 检查 string 是否为空，如果为空则使用 defaultValue 作为返回值。
	 *
	 * @param string       要检查的 String。
	 * @param defaultValue 如果 string 为空时的默认值。
	 * @return string 不为空返回 string ，否则返回 defaultValue。
	 */
	public static String checkEmptyString (String string, String defaultValue) {
		if (isEmpty (string)) {
			return defaultValue;
		}
		return string;
	}

	/**
	 * 判断 CharSequence 是否有内容。
	 *
	 * @param charSequence 要判断的CharSequence。
	 * @return 当 charSequence 不为 null 与 {@link CharSequence#length()} > 0 时返回
	 * true。
	 */
	public static boolean hasLength (CharSequence charSequence) {
		return (charSequence != null && charSequence.length () > 0);
	}

	/**
	 * 判断 charSequence 是否有 text。
	 *
	 * @param charSequence 要判断的CharSequence。
	 * @return 当 charSequence 不为 null 与 {@link CharSequence#length()} > 0 并且该
	 * charSequence 的至少一个字符在 {@link Character#isWhitespace(char)} 返回 false 时返回
	 * true。
	 */
	public static boolean hasText (CharSequence charSequence) {
		if (!hasLength (charSequence)) {
			return false;
		}
		int strLen = charSequence.length ();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace (charSequence.charAt (i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex   - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数，如果
	 * string 为 null 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 endIndex 大于 string
	 *                                  的长度时。
	 */
	public static int occurrence (String string, String s, int beginIndex, int endIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return occurrence0 (string, s, beginIndex, endIndex);
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾出现的次数，如果 string 为 null 或者
	 * s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 时。
	 */
	public static int occurrence (String string, String s, int beginIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return occurrence0 (string, s, beginIndex, string.length ());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中出现的次数。
	 *
	 * @param string 一个字符串。
	 * @param s      需要检测在 string 中出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中出现的次数，如果 string 为 null 或者 s 为 空字符串 则返回 0，如果
	 * string 为 空 或者 s 为 空字符串 则返回 0。
	 */
	public static int occurrence (String string, String s) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return occurrence0 (string, s, 0, string.length ());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex   - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束出现的次数。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *                                  endIndex 大于 string 的长度时。
	 */
	private static int occurrence0 (String string, String s, int beginIndex, int endIndex) {
		if (endIndex == 0 && beginIndex == 0) {
			return 0;
		}
		Assert.isTrue (beginIndex >= 0 && beginIndex < endIndex);
		Assert.isTrue (endIndex <= string.length ());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf (s, beginIndex);
			if (beginIndex == -1 || beginIndex >= endIndex) {
				break;
			}
			i++;
			beginIndex = beginIndex + s.length ();
		}
		return i;
	}

	/**
	 * 得到字符串 s 在 字符串 string 中第一次开始连续出现的次数。
	 *
	 * @param string 一个字符串。
	 * @param s      需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空 则返回 0。
	 */
	public static int consecutive (String string, String s) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return consecutive0 (string, s, 0, string.length ());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数，如果 string 为 空
	 * 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 时。
	 */
	public static int consecutive (String string, String s, int beginIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return consecutive0 (string, s, beginIndex, string.length ());
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束第一次开始连续出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex   - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到末尾第一次开始连续出现的次数，如果 string 为 空
	 * 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *                                  endIndex 大于 string 的长度时。
	 */
	public static int consecutive (String string, String s, int beginIndex, int endIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return consecutive0 (string, s, beginIndex, endIndex);
	}

	/**
	 * 得到字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1 处结束第一次开始连续出现的次数。
	 *
	 * @param string     一个字符串。
	 * @param s          需要检测在 string 中第一次开始连续出现次数的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex   - 结束处的索引（不包括） 。
	 * @return 字符串 s 在 字符串 string 中从 beginIndex 处开始到 endIndex - 1
	 * 处结束第一次开始连续出现的次数。
	 * @throws IllegalArgumentException 当 beginIndex 小于 0 或者 大于等于endIndex 或者
	 *                                  endIndex 大于 string 的长度时。
	 */
	private static int consecutive0 (String string, String s, int beginIndex, int endIndex) {
		if (endIndex == 0 && beginIndex == 0) {
			return 0;
		}
		Assert.isTrue (beginIndex >= 0 && beginIndex < endIndex);
		Assert.isTrue (endIndex <= string.length ());
		int i = 0;
		while (true) {
			beginIndex = string.indexOf (s, beginIndex);
			if (beginIndex == -1 || beginIndex >= endIndex) {
				break;
			} else if ((beginIndex != endIndex - 1) && (s.charAt (0) != string.charAt (beginIndex + 1))) {
				// 如果 s 的第一个字符与下次比较的第一个字符都不匹配，则为不连续
				i++;
				break;
			}
			i++;
			beginIndex = beginIndex + s.length ();
		}
		return i;
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前第一次开始连续出现的次数。
	 *
	 * @param string 一个字符串。
	 * @param s      需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @return 字符串 s 在 字符串 string 中从后往前第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串
	 * 则返回 0。
	 */
	public static int lastConsecutive (String string, String s) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return lastConsecutive0 (string, s, string.length (), 0);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 0 处结束第一次开始连续出现的次数。
	 *
	 * @param string         一个字符串。
	 * @param s              需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 0
	 * 处结束第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度时。
	 */
	public static int lastConsecutive (String string, String s, int lastBeginIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return lastConsecutive0 (string, s, lastBeginIndex, 0);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数。
	 *
	 * @param string         一个字符串。
	 * @param s              需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @param lastEndIndex   - 从后往前结束处的索引（包括） 。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数，如果 string 为 空 或者 s 为 空字符串 则返回 0。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度 或者 小于等于
	 *                                  lastEndIndex 或者 lastEndIndex 小于0 时。
	 */
	public static int lastConsecutive (String string, String s, int lastBeginIndex, int lastEndIndex) {
		if (string == null || isEmpty (s)) {
			return 0;
		}
		return lastConsecutive0 (string, s, lastBeginIndex, lastEndIndex);
	}

	/**
	 * 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数。
	 *
	 * @param string         一个字符串。
	 * @param s              需要检测在 string 中从后往前第一次开始连续出现次数的字符串。
	 * @param lastBeginIndex 从后往前开始处的索引（不包括）。
	 * @param lastEndIndex   - 从后往前结束处的索引（包括） 。
	 * @return 字符串 s 在 字符串 string 中从后往前 lastBeginIndex - 1 处开始到 lastEndIndex
	 * 处结束第一次开始连续出现的次数。
	 * @throws IllegalArgumentException 当 lastBeginIndex 大于 string 的长度 或者 小于等于
	 *                                  lastEndIndex 或者 lastEndIndex 小于0 时。
	 */
	private static int lastConsecutive0 (String string, String s, int lastBeginIndex, int lastEndIndex) {
		if (lastBeginIndex == 0 && lastEndIndex == 0) {
			return 0;
		}
		Assert.isTrue (lastBeginIndex <= string.length () && lastBeginIndex > lastEndIndex);
		Assert.isTrue (lastEndIndex >= 0);
		int i = 0;
		StringBuilder builder = new StringBuilder (string);
		builder.delete (0, lastEndIndex);
		char sEndChar = s.charAt (s.length () - 1);
		while (true) {
			lastBeginIndex = builder.lastIndexOf (s, --lastBeginIndex);
			if (lastBeginIndex == -1) {
				break;
			} else if (lastBeginIndex == 0) {
				i++;
				break;
			} else if (sEndChar != builder.charAt (lastBeginIndex - 1)) {
				// 如果 s 的最后个字符与下次比较的反向第一个字符都不匹配，则为不连续
				i++;
				break;
			}
			i++;
			builder.delete (lastBeginIndex - s.length (), lastBeginIndex);
		}
		return i;
	}

	/**
	 * 得到字符串 s 在 string 中出现第 count 次时的索引。
	 * <p/>
	 * 如果 string 为 null 返回 -1 。如果 s 在 string 中出现次数不到 count 次，则返回 -1。
	 *
	 * @param string 任意字符串。
	 * @param s      任意字符串。
	 * @param count  指定 s 在 string 中出现的次数。
	 * @return 字符串 s 在 string 中出现第 count 次时的索引。
	 * @throws IllegalArgumentException 如果 count 小于等于 0。
	 */
	public static int indexOf (String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue (count > 0);
		int index = -s.length ();
		for (int i = 0; i < count; i++) {
			index = string.indexOf (s, index + s.length ());
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	/**
	 * 得到字符串 s 在 string 中从后往前出现第 count 次时的索引。
	 * <p/>
	 * 如果 string 为 null 返回 -1 。如果 s 在 string 中出现次数不到 count 次，则返回 -1。
	 *
	 * @param string 任意字符串。
	 * @param s      任意字符串。
	 * @param count  指定 s 在 string 中出现的次数。
	 * @return 字符串 s 在 string 中出现第 count 次时的索引。
	 * @throws IllegalArgumentException 如果 count 小于等于 0。
	 */
	public static int lastIndexOf (String string, String s, int count) {
		if (string == null) {
			return -1;
		}
		Assert.isTrue (count > 0);
		int index = string.length ();
		for (int i = 0; i < count; i++) {
			index = string.lastIndexOf (s, index - 1);
			if (index == -1) {
				return -1;
			}
		}
		return index;
	}

	/**
	 * 返回一个新的字符串，它是此字符串的一个子字符串。该子字符串始于指定索引处的字符，一直到此字符串末尾。
	 * <p/>
	 * 该方法与 {@link String#substring(int)} 不同的是，该方法生成的 {@link String} 内部的 char[]
	 * 与原字符串无关。
	 *
	 * @param string     要执行取子的字符串。
	 * @param beginIndex beginIndex 开始处的索引（包括）。
	 * @return 指定的子字符串，如果 string 为 null 则返回 null 。
	 * @throws IndexOutOfBoundsException 如果 beginIndex 为负或大于此 String 对象的长度。
	 * @see String#substring(int)
	 */
	public static String substring (String string, int beginIndex) {
		if (string == null) {
			return null;
		}
		return string.substring (beginIndex);
	}

	/**
	 * 返回一个新字符串，它是此字符串的一个子字符串。该子字符串从指定的 beginIndex 处开始，一直到索引 endIndex - 1
	 * 处的字符。因此，该子字符串的长度为 endIndex-beginIndex。
	 * <p/>
	 * 该方法与 {@link String#substring(int, int)} 不同的是，该方法生成的 {@link String} 内部的
	 * char[] 与原字符串无关。
	 *
	 * @param string     要执行取子的字符串。
	 * @param beginIndex 开始处的索引（包括）。
	 * @param endIndex   结束处的索引（不包括）。
	 * @return 指定的子字符串，如果 string 为 null 则返回 null 。
	 * @throws IndexOutOfBoundsException 如果 beginIndex 为负，或 endIndex 大于此 String
	 *                                   对象的长度，或 beginIndex 大于 endIndex。
	 * @see String#substring(int, int)
	 */
	public static String substring (String string, int beginIndex, int endIndex) {
		if (string == null) {
			return null;
		}
		return new String (string.substring (beginIndex, endIndex));
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link java.util.Set}。
	 * <p/>
	 * 该方法调用 <code>stringSplitToSet(value, regex, true)</code> 完成。
	 * <p/>
	 * 该方法返回的 Set 为 {@link java.util.LinkedHashSet} 。
	 *
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串 Set ，根据给定正则表达式的匹配来拆分 value ，从而生成此 Set 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see java.util.LinkedHashSet
	 * @see #stringSplitToSet(String, String, boolean)
	 */
	public static Set<String> stringSplitToSet (String value, String regex) {
		return stringSplitToSet (value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link java.util.Set}。
	 * <p/>
	 * 该方法返回的 Set 为 {@link java.util.LinkedHashSet} 。
	 *
	 * @param value             需要被拆分的字符串。
	 * @param regex             定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串 Set ，根据给定正则表达式的匹配来拆分 value ，从而生成此 Set 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see java.util.LinkedHashSet
	 */
	public static Set<String> stringSplitToSet (String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new LinkedHashSet<String> (6);
		}
		Set<String> set = new LinkedHashSet<String> ();
		stringSplitToCollection (value, regex, set, ignoreEmptyTokens);
		return set;
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link java.util.List}。
	 * <p/>
	 * 该方法调用 <code>stringSplitToList(value, regex, true)</code> 完成。
	 * <p/>
	 * 该方法返回的 List 为 {@link java.util.ArrayList} 。
	 *
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串 List ，根据给定正则表达式的匹配来拆分 value ，从而生成此 List 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see java.util.ArrayList
	 * @see #stringSplitToList(String, String, boolean)
	 */
	public static List<String> stringSplitToList (String value, String regex) {
		return stringSplitToList (value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 {@link java.util.List}。
	 * <p/>
	 * 该方法返回的 List 为 {@link java.util.ArrayList} 。
	 *
	 * @param value             需要被拆分的字符串。
	 * @param regex             定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串 List ，根据给定正则表达式的匹配来拆分 value ，从而生成此 List 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 * @see java.util.ArrayList
	 */
	public static List<String> stringSplitToList (String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return new ArrayList<String> (5);
		}
		List<String> list = new ArrayList<String> ();
		stringSplitToCollection (value, regex, list, ignoreEmptyTokens);
		return list;
	}

	private static void stringSplitToCollection (String value, String regex, Collection<String> set, boolean ignoreEmptyTokens) {
		String[] split = value.split (regex);
		for (String aSplit : split) {
			if (!ignoreEmptyTokens || aSplit.length () > 0) {
				set.add (aSplit);
			}
		}
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 String[]。
	 * <p/>
	 * 该方法调用 <code>stringSplitToArray(value, regex, true)</code> 完成。
	 *
	 * @param value 需要被拆分的字符串。
	 * @param regex 定界正则表达式 。
	 * @return 字符串数组 ，根据给定正则表达式的匹配来拆分 value ，从而生成此 数组 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 */
	public static String[] stringSplitToArray (String value, String regex) {
		return stringSplitToArray (value, regex, true);
	}

	/**
	 * 根据给定的正则表达式的匹配来拆分 value 为一个 String[]。
	 *
	 * @param value             需要被拆分的字符串。
	 * @param regex             定界正则表达式 。
	 * @param ignoreEmptyTokens 如果为 true 则忽略 长度为零 的字符串 。
	 * @return 字符串数组 ，根据给定正则表达式的匹配来拆分 value ，从而生成此 数组 。
	 * @throws java.util.regex.PatternSyntaxException 如果 regex 表示的正则表达式无效。
	 */
	public static String[] stringSplitToArray (String value, String regex, boolean ignoreEmptyTokens) {
		if (value == null) {
			return EMPTY_STRINGS;
		}
		List<String> list = stringSplitToList (value, regex, ignoreEmptyTokens);
		if (list.isEmpty ()) {
			return EMPTY_STRINGS;
		}
		return list.toArray (new String[list.size ()]);
	}

	/**
	 * @return
	 * @name parseLine
	 * @author zhyang
	 * @date 2011-6-26
	 * @description 以逗号分隔字符串
	 */
	public static List<String> parseLine (String str) {
		return parseLine (str, ",");
	}

	/**
	 * @param delim
	 * @return
	 * @name parseLine
	 * @author zhyang
	 * @date 2011-6-26
	 * @description 按照delim分隔字符串
	 */
	public static List<String> parseLine (String str, String delim) {
		return parseLine (str, delim, false);
	}

	/**
	 * @param delim   字符串分隔
	 * @param blnTrim 是否去掉空格
	 * @return
	 * @name parseLine
	 * @author zhyang
	 * @date 2011-6-26
	 * @description 以delim分隔字符串，如果blnTrim为true，对分隔的字符串trim；否则不trim
	 */
	public static List<String> parseLine (String str, String delim, boolean blnTrim) {
		List<String> alStr = CollectionUtils.newArrayList ();
		String[] st = str.split (delim);
		for (int i = 0; i < st.length; ++i) {
			if (st[i] == null) {
				st[i] = "";
			}
			if (blnTrim) {
				alStr.add (st[i].trim ());
			} else {
				alStr.add (st[i]);
			}
		}
		return alStr;
	}

	/**
	 * @param str
	 * @param trimStr
	 * @return
	 * @name trimLeft
	 * @author zhyang
	 * @date 2010-11-25
	 * @description 去除首端字符串
	 */
	public static String trimLeft (String str, String trimStr) {
		return trimLeft (str, trimStr, true);
	}

	public static String trimLeft (String str, String trimStr, boolean blnTrimAll) {
		if (str == null || trimStr == null) {
			return str;
		}

		if (str.startsWith (trimStr)) {
			if (blnTrimAll) {
				str = str.substring (trimStr.length ());
				str = trimLeft (str, trimStr, blnTrimAll);
			} else {
				str = str.substring (trimStr.length ());
			}
		}
		return str;
	}

	/**
	 * @param str
	 * @param trimStr
	 * @return
	 * @name trimStringRight
	 * @author zhyang
	 * @date 2010-11-25
	 * @description 去除末端字符串
	 */
	public static String trimRight (String str, String trimStr) {
		return trimRight (str, trimStr, true);
	}

	public static String trimRight (String str, String trimStr, boolean blnTrimAll) {
		if (str == null || trimStr == null) {
			return str;
		}

		if (str.endsWith (trimStr)) {
			if (blnTrimAll) {
				str = str.substring (0, str.length () - trimStr.length ());
				return trimRight (str, trimStr, blnTrimAll);
			} else {
				return str.substring (0, str.length () - trimStr.length ());
			}
		}
		return str;
	}

	/**
	 * @name isIP
	 * @author zhyang
	 * @date 2010-12-8
	 * @description 判断字符串是否是IP
	 */
	public static boolean isIP (String str) {
		if (str == null || str.equals ("")) {
			return false;
		}
		return isMatches (str, IP_REGEX);
	}


	/**
	 * @param str
	 * @param matcher
	 * @return
	 * @name isMatches
	 * @author zhyang
	 * @date 2011-6-20
	 * @description 判断字符串是否匹配正则表达式
	 */
	public static boolean isMatches (String str, String matcher) {
		return isMatches (str, matcher, false);
	}

	public static boolean isMatches (String str, String matcher, boolean blnIgnoreCase) {
		if (isEmpty (str)) {
			return false;
		}
		String strValue = null == str ? "" : str;
		if (blnIgnoreCase) {
			strValue = str.toUpperCase ();
		}
		Pattern p = Pattern.compile (matcher);
		Matcher m = p.matcher (strValue);
		return m.matches ();
	}

	/**
	 * @return
	 * @name findString
	 * @author zhyang
	 * @date 2011-7-7
	 * @description 根据正则表达是，查找满足正则表达式的字符串
	 */
	public static String find (String str, String findStr) {
		Pattern p = Pattern.compile (findStr);
		Matcher m = p.matcher (str);
		if (m.find ()) {
			return m.group ();
		} else {
			return "";
		}
	}

	/**
	 * @return
	 * @name getByteLength
	 * @author zhyang
	 * @date 2011-7-6
	 * @description 返回字符串字节长度
	 */
	public static int getByteLength (String str) {
		if (null == str) {
			return 0;
		} else {
			return str.getBytes ().length;
		}
	}


	/**
	 * @param length
	 * @return
	 * @name Lpad
	 * @author zhyang
	 * @date 2011-7-8
	 * @description 左填充字符串
	 */
	public static String lpad (String str, char c, int length) {
		StringBuilder sb = new StringBuilder ();
		for (int i = length - str.length (); i > 0; i--) {
			sb.append (c);
		}
		sb.append (str);
		return sb.toString ();
	}

	/**
	 * @param length
	 * @return
	 * @name Rpad
	 * @author zhyang
	 * @date 2011-7-8
	 * @description 右填充字符串
	 */
	public static String rpad (String str, char c, int length) {
		StringBuilder sb = new StringBuilder ();
		sb.append (str);
		for (int i = length - str.length (); i > 0; i--) {
			sb.append (c);
		}
		return sb.toString ();
	}

	/**
	 * 判断是否是一个中文汉字
	 *
	 * @param c 字符
	 * @return true表示是中文汉字，false表示是英文字母
	 * 使用了JAVA不支持的编码格式
	 */
	public static boolean isChineseChar (char c) throws CommonException {
		// 如果字节数大于1，是汉字
		// 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
		try {
			return String.valueOf (c).getBytes ("GBK").length > 1;
		} catch (Exception e) {
			throw new CommonException (e);
		}

	}
	
	/**
	 * 按字节截取字符串
	 *
	 * @param str 原始字符串
	 * @return 截取后的字符串
	 */
	public static String substrByByte(String str, int intStart, int intEnd) {
		final String DEFAULT_CHAR_SET = "GBK";
		// 原始字符不为null，也不是空字符串
		if (str != null && !"".equals(str)) {
			// 将原始字符串转换为GBK编码格式
			String strValue = str;
			try {
				strValue = new String(str.getBytes(DEFAULT_CHAR_SET), DEFAULT_CHAR_SET);
				String strSubStrTemp = "";
				int intPos;
				int intPosI;
				if (intStart > 0) {
					intPos = (intStart + 1) / 2;
					intPosI = intPos;
					strSubStrTemp = strValue.substring(0, intPos);
					while (strSubStrTemp.getBytes().length < intStart) {
						intPosI = (intPosI + 1) / 2;
						intPos += intPosI;
						strSubStrTemp = strValue.substring(0, intPos);
					}
				}
				if (!"".equals(strSubStrTemp)) {
					strValue = strValue.substring(strSubStrTemp.length());
				}
				intEnd = intEnd - intStart;
				if (intEnd < strValue.getBytes(DEFAULT_CHAR_SET).length) {
					intPos = (intEnd + 1) / 2;
					intPosI = intPos;
					strSubStrTemp = strValue.substring(0, intPos);
					while (strSubStrTemp.getBytes(DEFAULT_CHAR_SET).length < intEnd) {
						intPosI = (intPosI + 1) / 2;
						intPos += intPosI;
						strSubStrTemp = strValue.substring(0, intPos);
					}
					
					strValue = strSubStrTemp.substring(0,
					                                   (intPos > strSubStrTemp.length() ? strSubStrTemp.length() : intPos));
					while (strValue.getBytes(DEFAULT_CHAR_SET).length > intEnd) {
						strValue = strValue.substring(0, strValue.length() - 1);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return strValue;
		}
		return str;
	}

	public static String cat (String... strs) {
		StringBuilder sbString = new StringBuilder ();
		for (String str : strs) {
			sbString.append (str);
		}
		return sbString.toString ();
	}

	public static boolean toBoolean (String str) {
		if (null == str) {
			return false;
		}
		return "1".equals (str) || str.startsWith ("t") || str.startsWith ("T") || str.startsWith ("y") || str.startsWith ("Y");
	}

	public static boolean isDate (String str) {
		if (isEmpty (str)) {
			return false;
		}
		str = str.replaceAll ("[年月日时分秒\\\\/\\s:]", "-");
		str = str.replaceAll ("(-)$", "");


		str.trim ();
		if (isMatches (str, DATE_REGEX)) {
			return true;
		}
		String strDateString = DateUtils.formatDateString (str);
		return null != strDateString;
	}


	/*
	 * ==========================================================================
	 * ==
	 */
	/* 字符串分割函数。 */
	/*                                                                              */
	/* 将字符串按指定分隔符分割。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 将字符串按空白字符分割。
	 * <p/>
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.split(null)       = null
	 * StringUtils.split("")         = []
	 * StringUtils.split("abc def")  = ["abc", "def"]
	 * StringUtils.split("abc  def") = ["abc", "def"]
	 * StringUtils.split(" abc ")    = ["abc"]
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要分割的字符串
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 * <p/>
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.split(null, *)         = null
	 * StringUtils.split("", *)           = []
	 * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
	 * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
	 * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
	 * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要分割的字符串
	 * @param separatorChar 分隔符
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return StringUtils.EMPTY_STRINGS;
		}

		List list = new ArrayList();
		int i = 0;
		int start = 0;
		boolean match = false;

		while (i < length) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}

				start = ++i;
				continue;
			}

			match = true;
			i++;
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 将字符串按指定字符分割。
	 * <p/>
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.split(null, *)                = null
	 * StringUtils.split("", *)                  = []
	 * StringUtils.split("abc def", null)        = ["abc", "def"]
	 * StringUtils.split("abc def", " ")         = ["abc", "def"]
	 * StringUtils.split("abc  def", " ")        = ["abc", "def"]
	 * StringUtils.split(" ab:  cd::ef  ", ":")  = ["ab", "cd", "ef"]
	 * StringUtils.split("abc.def", "")          = ["abc.def"]
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要分割的字符串
	 * @param separatorChars 分隔符
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 * <p/>
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.split(null, *, *)                 = null
	 * StringUtils.split("", *, *)                   = []
	 * StringUtils.split("ab cd ef", null, 0)        = ["ab", "cd", "ef"]
	 * StringUtils.split("  ab   cd ef  ", null, 0)  = ["ab", "cd", "ef"]
	 * StringUtils.split("ab:cd::ef", ":", 0)        = ["ab", "cd", "ef"]
	 * StringUtils.split("ab:cd:ef", ":", 2)         = ["ab", "cdef"]
	 * StringUtils.split("abc.def", "", 2)           = ["abc.def"]
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要分割的字符串
	 * @param separatorChars 分隔符
	 * @param max 返回的数组的最大个数，如果小于等于0，则表示无限制
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, String separatorChars, int max) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return StringUtils.EMPTY_STRINGS;
		}

		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;

		if (separatorChars == null) {
			// null表示使用空白作为分隔符
			while (i < length) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// 优化分隔符长度为1的情形
			char sep = separatorChars.charAt(0);

			while (i < length) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else {
			// 一般情形
			while (i < length) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 去空白（或指定字符）的函数。 */
	/*                                                                              */
	/* 以下方法用来除去一个字串中的空白或指定字符。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trim(String str) {
		return trim(str, null, 0);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trim(null, *)          = null
	 * StringUtils.trim("", *)            = ""
	 * StringUtils.trim("abc", null)      = "abc"
	 * StringUtils.trim("  abc", null)    = "abc"
	 * StringUtils.trim("abc  ", null)    = "abc"
	 * StringUtils.trim(" abc ", null)    = "abc"
	 * StringUtils.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 *
	 * @param str 要处理的字符串
	 * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimStart(null)         = null
	 * StringUtils.trimStart("")           = ""
	 * StringUtils.trimStart("abc")        = "abc"
	 * StringUtils.trimStart("  abc")      = "abc"
	 * StringUtils.trimStart("abc  ")      = "abc  "
	 * StringUtils.trimStart(" abc ")      = "abc "
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	}

	/**
	 * 除去字符串头部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimStart(null, *)          = null
	 * StringUtils.trimStart("", *)            = ""
	 * StringUtils.trimStart("abc", "")        = "abc"
	 * StringUtils.trimStart("abc", null)      = "abc"
	 * StringUtils.trimStart("  abc", null)    = "abc"
	 * StringUtils.trimStart("abc  ", null)    = "abc  "
	 * StringUtils.trimStart(" abc ", null)    = "abc "
	 * StringUtils.trimStart("yxabc  ", "xyz") = "abc  "
	 * </pre>
	 *
	 * @param str 要处理的字符串
	 * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trimStart(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimEnd(null)       = null
	 * StringUtils.trimEnd("")         = ""
	 * StringUtils.trimEnd("abc")      = "abc"
	 * StringUtils.trimEnd("  abc")    = "  abc"
	 * StringUtils.trimEnd("abc  ")    = "abc"
	 * StringUtils.trimEnd(" abc ")    = " abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}

	/**
	 * 除去字符串尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimEnd(null, *)          = null
	 * StringUtils.trimEnd("", *)            = ""
	 * StringUtils.trimEnd("abc", "")        = "abc"
	 * StringUtils.trimEnd("abc", null)      = "abc"
	 * StringUtils.trimEnd("  abc", null)    = "  abc"
	 * StringUtils.trimEnd("abc  ", null)    = "abc"
	 * StringUtils.trimEnd(" abc ", null)    = " abc"
	 * StringUtils.trimEnd("  abcyx", "xyz") = "  abc"
	 * </pre>
	 *
	 * @param str 要处理的字符串
	 * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimToNull(null)          = null
	 * StringUtils.trimToNull("")            = null
	 * StringUtils.trimToNull("     ")       = null
	 * StringUtils.trimToNull("abc")         = "abc"
	 * StringUtils.trimToNull("    abc    ") = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimToNull(String str) {
		return trimToNull(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trim(null, *)          = null
	 * StringUtils.trim("", *)            = null
	 * StringUtils.trim("abc", null)      = "abc"
	 * StringUtils.trim("  abc", null)    = "abc"
	 * StringUtils.trim("abc  ", null)    = "abc"
	 * StringUtils.trim(" abc ", null)    = "abc"
	 * StringUtils.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimToNull(String str, String stripChars) {
		String result = trim(str, stripChars);

		if ((result == null) || (result.length() == 0)) {
			return null;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，则返回空字符串<code>""</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trimToEmpty(null)          = ""
	 * StringUtils.trimToEmpty("")            = ""
	 * StringUtils.trimToEmpty("     ")       = ""
	 * StringUtils.trimToEmpty("abc")         = "abc"
	 * StringUtils.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimToEmpty(String str) {
		return trimToEmpty(str, null);
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，则返回空字符串<code>""</code>。
	 * <p/>
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trim(null, *)          = ""
	 * StringUtils.trim("", *)            = ""
	 * StringUtils.trim("abc", null)      = "abc"
	 * StringUtils.trim("  abc", null)    = "abc"
	 * StringUtils.trim("abc  ", null)    = "abc"
	 * StringUtils.trim(" abc ", null)    = "abc"
	 * StringUtils.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 * <code>null</code>
	 */
	public static String trimToEmpty(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null) {
			return EMPTY_STRING;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.trim(null, *)          = null
	 * StringUtils.trim("", *)            = ""
	 * StringUtils.trim("abc", null)      = "abc"
	 * StringUtils.trim("  abc", null)    = "abc"
	 * StringUtils.trim("abc  ", null)    = "abc"
	 * StringUtils.trim(" abc ", null)    = "abc"
	 * StringUtils.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 *
	 * @param str 要处理的字符串
	 * @param stripChars 要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @param mode <code>-1</code>表示trimStart，<code>0</code>表示trim全部，
	 * <code>1</code>表示trimEnd
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}
/*
	 * ==========================================================================
	 * ==
	 */
	/* 搜索并取子串函数。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 取得第一个出现的分隔子串之前的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringBefore(null, *)      = null
	 * StringUtils.substringBefore("", *)        = ""
	 * StringUtils.substringBefore("abc", "a")   = ""
	 * StringUtils.substringBefore("abcba", "b") = "a"
	 * StringUtils.substringBefore("abc", "c")   = "ab"
	 * StringUtils.substringBefore("abc", "d")   = "abc"
	 * StringUtils.substringBefore("abc", "")    = ""
	 * StringUtils.substringBefore("abc", null)  = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param separator 要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringBefore(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)) {
			return str;
		}

		if (separator.length() == 0) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 取得第一个出现的分隔子串之后的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringAfter(null, *)      = null
	 * StringUtils.substringAfter("", *)        = ""
	 * StringUtils.substringAfter(*, null)      = ""
	 * StringUtils.substringAfter("abc", "a")   = "bc"
	 * StringUtils.substringAfter("abcba", "b") = "cba"
	 * StringUtils.substringAfter("abc", "c")   = ""
	 * StringUtils.substringAfter("abc", "d")   = ""
	 * StringUtils.substringAfter("abc", "")    = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param separator 要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (separator == null) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 取得最后一个的分隔子串之前的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringBeforeLast(null, *)      = null
	 * StringUtils.substringBeforeLast("", *)        = ""
	 * StringUtils.substringBeforeLast("abcba", "b") = "abc"
	 * StringUtils.substringBeforeLast("abc", "c")   = "ab"
	 * StringUtils.substringBeforeLast("a", "a")     = ""
	 * StringUtils.substringBeforeLast("a", "z")     = "a"
	 * StringUtils.substringBeforeLast("a", null)    = "a"
	 * StringUtils.substringBeforeLast("a", "")      = "a"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param separator 要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringBeforeLast(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 取得最后一个的分隔子串之后的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringAfterLast(null, *)      = null
	 * StringUtils.substringAfterLast("", *)        = ""
	 * StringUtils.substringAfterLast(*, "")        = ""
	 * StringUtils.substringAfterLast(*, null)      = ""
	 * StringUtils.substringAfterLast("abc", "a")   = "bc"
	 * StringUtils.substringAfterLast("abcba", "b") = "a"
	 * StringUtils.substringAfterLast("abc", "c")   = ""
	 * StringUtils.substringAfterLast("a", "a")     = ""
	 * StringUtils.substringAfterLast("a", "z")     = ""
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param separator 要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringAfterLast(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if ((separator == null) || (separator.length() == 0)) {
			return EMPTY_STRING;
		}

		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == (str.length() - separator.length()))) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 取得指定分隔符的前两次出现之间的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringBetween(null, *)            = null
	 * StringUtils.substringBetween("", "")             = ""
	 * StringUtils.substringBetween("", "tag")          = null
	 * StringUtils.substringBetween("tagabctag", null)  = null
	 * StringUtils.substringBetween("tagabctag", "")    = ""
	 * StringUtils.substringBetween("tagabctag", "tag") = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param tag 要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag, 0);
	}

	/**
	 * 取得两个分隔符之间的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringBetween(null, *, *)          = null
	 * StringUtils.substringBetween("", "", "")          = ""
	 * StringUtils.substringBetween("", "", "tag")       = null
	 * StringUtils.substringBetween("", "tag", "tag")    = null
	 * StringUtils.substringBetween("yabcz", null, null) = null
	 * StringUtils.substringBetween("yabcz", "", "")     = ""
	 * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
	 * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param open 要搜索的分隔子串1
	 * @param close 要搜索的分隔子串2
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String open, String close) {
		return substringBetween(str, open, close, 0);
	}

	/**
	 * 取得两个分隔符之间的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.substringBetween(null, *, *)          = null
	 * StringUtils.substringBetween("", "", "")          = ""
	 * StringUtils.substringBetween("", "", "tag")       = null
	 * StringUtils.substringBetween("", "tag", "tag")    = null
	 * StringUtils.substringBetween("yabcz", null, null) = null
	 * StringUtils.substringBetween("yabcz", "", "")     = ""
	 * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
	 * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 字符串
	 * @param open 要搜索的分隔子串1
	 * @param close 要搜索的分隔子串2
	 * @param fromIndex 从指定index处搜索
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String open, String close, int fromIndex) {
		if ((str == null) || (open == null) || (close == null)) {
			return null;
		}

		int start = str.indexOf(open, fromIndex);

		if (start != -1) {
			int end = str.indexOf(close, start + open.length());

			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}

		return null;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 删除字符。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 删除所有在<code>Character.isWhitespace(char)</code>中所定义的空白。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 *
	 * @param str 要处理的字符串
	 * @return 去空白后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String deleteWhitespace(String str) {
		if (str == null) {
			return null;
		}

		int sz = str.length();
		StringBuffer buffer = new StringBuffer(sz);

		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				buffer.append(str.charAt(i));
			}
		}

		return buffer.toString();
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* 替换子串。 */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * 替换指定的子串，只替换第一个出现的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>，如果指定子串为<code>null</code>
	 * ，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.replaceOnce(null, *, *)        = null
	 * StringUtils.replaceOnce("", *, *)          = ""
	 * StringUtils.replaceOnce("aba", null, null) = "aba"
	 * StringUtils.replaceOnce("aba", null, null) = "aba"
	 * StringUtils.replaceOnce("aba", "a", null)  = "aba"
	 * StringUtils.replaceOnce("aba", "a", "")    = "ba"
	 * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param text 要扫描的字符串
	 * @param repl 要搜索的子串
	 * @param with 替换字符串
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	/**
	 * 替换指定的子串，替换所有出现的子串。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>，如果指定子串为<code>null</code>
	 * ，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.replace(null, *, *)        = null
	 * StringUtils.replace("", *, *)          = ""
	 * StringUtils.replace("aba", null, null) = "aba"
	 * StringUtils.replace("aba", null, null) = "aba"
	 * StringUtils.replace("aba", "a", null)  = "aba"
	 * StringUtils.replace("aba", "a", "")    = "b"
	 * StringUtils.replace("aba", "a", "z")   = "zbz"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param text 要扫描的字符串
	 * @param repl 要搜索的子串
	 * @param with 替换字符串
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * 替换指定的子串，替换指定的次数。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>，如果指定子串为<code>null</code>
	 * ，则返回原字符串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.replace(null, *, *, *)         = null
	 * StringUtils.replace("", *, *, *)           = ""
	 * StringUtils.replace("abaa", null, null, 1) = "abaa"
	 * StringUtils.replace("abaa", null, null, 1) = "abaa"
	 * StringUtils.replace("abaa", "a", null, 1)  = "abaa"
	 * StringUtils.replace("abaa", "a", "", 1)    = "baa"
	 * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param text 要扫描的字符串
	 * @param repl 要搜索的子串
	 * @param with 替换字符串
	 * @param max maximum number of values to replace, or <code>-1</code> if no
	 * maximum
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replace(String text, String repl, String with, int max) {
		if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0;
		int end = 0;

		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}

		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 将字符串中所有指定的字符，替换成另一个。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.replaceChars(null, *, *)        = null
	 * StringUtils.replaceChars("", *, *)          = ""
	 * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
	 * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要扫描的字符串
	 * @param searchChar 要搜索的字符
	 * @param replaceChar 替换字符
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replaceChars(String str, char searchChar, char replaceChar) {
		if (str == null) {
			return null;
		}

		return str.replace(searchChar, replaceChar);
	}

	/**
	 * 将字符串中所有指定的字符，替换成另一个。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>。如果搜索字符串为<code>null</code>
	 * 或空，则返回原字符串。
	 * </p>
	 * <p/>
	 * <p>
	 * 例如：
	 * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
	 * 。
	 * </p>
	 * <p/>
	 * <p>
	 * 通常搜索字符串和替换字符串是等长的，如果搜索字符串比替换字符串长，则多余的字符将被删除。 如果搜索字符串比替换字符串短，则缺少的字符将被忽略。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.replaceChars(null, *, *)           = null
	 * StringUtils.replaceChars("", *, *)             = ""
	 * StringUtils.replaceChars("abc", null, *)       = "abc"
	 * StringUtils.replaceChars("abc", "", *)         = "abc"
	 * StringUtils.replaceChars("abc", "b", null)     = "ac"
	 * StringUtils.replaceChars("abc", "b", "")       = "ac"
	 * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
	 * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
	 * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要扫描的字符串
	 * @param searchChars 要搜索的字符串
	 * @param replaceChars 替换字符串
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replaceChars(String str, String searchChars, String replaceChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
			return str;
		}

		char[] chars = str.toCharArray();
		int len = chars.length;
		boolean modified = false;

		for (int i = 0, isize = searchChars.length(); i < isize; i++) {
			char searchChar = searchChars.charAt(i);

			if ((replaceChars == null) || (i >= replaceChars.length())) {
				// 删除
				int pos = 0;

				for (int j = 0; j < len; j++) {
					if (chars[j] != searchChar) {
						chars[pos++] = chars[j];
					} else {
						modified = true;
					}
				}

				len = pos;
			} else {
				// 替换
				for (int j = 0; j < len; j++) {
					if (chars[j] == searchChar) {
						chars[j] = replaceChars.charAt(i);
						modified = true;
					}
				}
			}
		}

		if (!modified) {
			return str;
		}

		return new String(chars, 0, len);
	}

	/**
	 * 取得长度为指定字符数的最右边的子串。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.right(null, *)    = null
	 * StringUtils.right(*, -ve)     = ""
	 * StringUtils.right("", *)      = ""
	 * StringUtils.right("abc", 0)   = ""
	 * StringUtils.right("abc", 2)   = "bc"
	 * StringUtils.right("abc", 4)   = "abc"
	 * </pre>
	 *
	 * @param str 字符串
	 * @param len 最右子串的长度
	 * @return 子串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0) {
			return EMPTY_STRING;
		}

		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * 将指定的子串用另一指定子串覆盖。
	 * <p/>
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 负的索引值将被看作<code>0</code>
	 * ，越界的索引值将被设置成字符串的长度相同的值。
	 * <p/>
	 *
	 * <pre>
	 * StringUtils.overlay(null, *, *, *)            = null
	 * StringUtils.overlay("", "abc", 0, 0)          = "abc"
	 * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
	 * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
	 * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
	 * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
	 * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
	 * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
	 * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
	 * </pre>
	 * <p/>
	 * </p>
	 *
	 * @param str 要扫描的字符串
	 * @param overlay 用来覆盖的字符串
	 * @param start 起始索引
	 * @param end 结束索引
	 * @return 被覆盖后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}

		if (overlay == null) {
			overlay = EMPTY_STRING;
		}

		int len = str.length();

		if (start < 0) {
			start = 0;
		}

		if (start > len) {
			start = len;
		}

		if (end < 0) {
			end = 0;
		}

		if (end > len) {
			end = len;
		}

		if (start > end) {
			int temp = start;

			start = end;
			end = temp;
		}

		return new StringBuffer((len + start) - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay)
				.append(str.substring(end)).toString();
	}
}
