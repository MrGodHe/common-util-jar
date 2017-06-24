package cn.pay.ebank.common.utils;

import cn.pay.ebank.common.exception.CommonException;

/**
 * Created by zhyang on 2015/3/19 0019.
 */
public class NumbericUtils {
	private static final String DOUBLE_REGEX = "(-)?(([0-9]+(\\.[0-9]*)?)|(\\.[0-9]+))((e|E)[0-9]+)?";
	private static final String INTEGER_REGEX = "(-)?\\d+(.0+)?";
	public static final String DOMAIN_REGEX = "(\\[|\\()" + DOUBLE_REGEX + "\\," + DOUBLE_REGEX + "(\\]|\\))";

	public static int parasInt (char chr, int defaultValue) {
		return parasInt (String.valueOf (chr), defaultValue);
	}

	public static int parasInt (String intStr, int defaultValue) {
		if (intStr == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt (intStr);
			} catch (NumberFormatException var3) {
				return defaultValue;
			}
		}
	}

	/**
	 * 判断是否为数字
	 *
	 * @param
	 *
	 * @return
	 */
	public static boolean isNumeric (String s) {
		return isDouble (s);
	}

	/**
	 * 判断是否为
	 *
	 * @param
	 *
	 * @return
	 */
	public static boolean isInt (String s) {
		if (s == null || s.equals ("")) {
			return false;
		}
		return StringUtils.isMatches (s, INTEGER_REGEX);
	}

	/**
	 * 判断是否为
	 *
	 * @param
	 *
	 * @return
	 */
	public static boolean isDouble (String s) {
		if (s == null || s.equals ("")) {
			return false;
		}
		return StringUtils.isMatches (s, DOUBLE_REGEX);
	}

	/**
	 * @param s
	 *
	 * @return
	 * @name toInteger
	 * @author zhyang
	 * @date 2011-7-7
	 * @description 将字符串转为整型
	 */
	public static int toInt (String s) {
		s = s.replaceAll ("\\.0*$", "");
		if (isInt (s)) {
			return Integer.parseInt (s);
		} else {
			return 0;
		}
	}

	public static double toDouble (String s) {
		if (isDouble (s)) {
			return Double.parseDouble (s);
		} else {
			return 0;
		}
	}

	public static String toNumeric (String s) {
		if (isDouble (s)) {
			return s;
		} else {
			s = StringUtils.find (s, DOUBLE_REGEX);
			return s;
		}
	}

	/**
	 * @param num
	 * @param strDomains
	 *
	 * @return
	 * @name isInDomains
	 * @author zhyang
	 * @date 2011-7-7
	 * @description 区间的验证表达式 例如[116,119]||[120,123]||[120,123] 并且判断num是否在区间内
	 */
	public static boolean isInDomains (Number num, String strDomains) {
		strDomains = strDomains.replaceAll ("\\|\\|", "\\|");
		if (StringUtils.isMatches (strDomains, DOMAIN_REGEX + "(\\|" + DOMAIN_REGEX + ")*")) {
			String[] aDomains = strDomains.split ("\\|");
			for (String str : aDomains) {
				if (isInDomain (num, str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param num
	 * @param strDomain
	 *
	 * @return
	 * @name isInDomain
	 * @author zhyang
	 * @date 2011-7-7
	 * @description 区间的验证表达式 例如[116,119)并且判断num是否在区间内
	 */
	public static boolean isInDomain (Number num, String strDomain) {

		if (!isDomainExpression (strDomain)) {
			throw new CommonException ("值域表达式[" + strDomain + "]格式错误！");
		}

		// 截取分析域表达式
		String[] strTemp = strDomain.split (",");
		String strLeftPart = strTemp[0];
		String strRigthPart = strTemp[1];
		// 得到值域的上下限值
		double m_dblLeftValue = Double.valueOf (strLeftPart.substring (1));
		double m_dblRightValue = Double.valueOf (strRigthPart.substring (0, strRigthPart.length () - 1));

		if (m_dblLeftValue > m_dblRightValue) {
			throw new CommonException ("值域表达式错误！右值不能比左值小！");
		}
		// 如果值域的下限为"["，则左边必须为大于等于，小于该值就是错的
		boolean m_blnContainLeft = strLeftPart.startsWith ("[");
		boolean m_blnContainRight = strRigthPart.endsWith ("]");

		double dblValue = num.doubleValue ();

		if (dblValue < m_dblLeftValue) {
			return false;
		} else if (!m_blnContainLeft && dblValue == m_dblLeftValue) {
			return false;
		}

		if (dblValue > m_dblRightValue) {
			return false;
		} else if (!m_blnContainRight && dblValue == m_dblRightValue) {
			return false;
		}
		return true;
	}

	/**
	 * @param strDomainExpression
	 *
	 * @return
	 * @name isDomainExpression
	 * @author zhyang
	 * @date 2012-4-9
	 * @description 判断给定字符串是否为域表达式
	 */
	public static boolean isDomainExpression (String strDomainExpression) {
		// 判断域值表达式的格式是否为[121,232],(-12,23]等
		return strDomainExpression.matches (DOMAIN_REGEX);
	}

	/**
	 * 将long转换为十六进制表示
	 *
	 * @param num
	 *
	 * @return
	 */
	public static String toHex (long num) {
		return Long.toHexString (num).toUpperCase ();
	}

	/**
	 * 将十六进制表示的数字转换为long
	 *
	 * @param hex
	 *
	 * @return
	 */
	public static long parseHex (String hex) {
		return Long.parseLong (hex, 16);
	}

	public static void main (String[] args) {
		System.out.println (NumbericUtils.toHex (250));
		System.out.println (NumbericUtils.parseHex ("abc"));
	}
}
