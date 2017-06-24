package cn.pay.ebank.common.beans.conversion;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * {@link Enum}的类型转换器。
 *
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 */
public class EnumTypeConverter extends AbstractTypeConverter<Enum<? extends Enum<?>>> {
	
	@SuppressWarnings("unchecked")
	public Class<Enum<? extends Enum<?>>> getTargetType () {
		Class<?> enumClass = Enum.class;
		return (Class<Enum<? extends Enum<?>>>) enumClass;
	}
	
	public List<Class<?>> getSupportedSourceTypes () {
		return Arrays.asList (CharSequence.class, String[].class, Enum.class);
	}
	
	public Enum<? extends Enum<?>> convert (Object value, Class<? extends Enum<? extends Enum<?>>> toType) {
		try {
			return (Enum<? extends Enum<?>>) enumValue0 (toType, value);
		} catch (Exception e) {
			throw new TypeConversionException (e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Enum<?> enumValue0 (Class<? extends Enum> toClass, Object value) {
		Enum<?> result = null;
		if (value == null) {
			result = null;
		} else if (value instanceof String[]) {
			result = Enum.valueOf (toClass, ((String[]) value)[0]);
		} else if (value instanceof String) {
			try {
				result = Enum.valueOf (toClass, (String) value);
			} catch (Exception e) {
			}

			if (result == null) {
				Method getByCodeMethod = getMethod (toClass, "getByCode", String.class);
				if (getByCodeMethod != null) {
					try {
						return (Enum) getByCodeMethod.invoke (null, (String) value);
					} catch (Exception e) {
						return null;
					}
				}
			}
		} else {
			String strValue = value.toString ();
			try {
				result = Enum.valueOf (toClass, strValue);
			} catch (Exception e) {
			}

			if (result == null) {
				try {
					Method getCodeMethod = getMethod (value.getClass (), "getCode");
					if (getCodeMethod != null) {
						Object getCodeValue = getCodeMethod.invoke (value);
						strValue = getCodeValue != null ? getCodeValue.toString () : strValue;
					}
					Method getByCodeMethod = getMethod (toClass, "getByCode", String.class);
					if (getByCodeMethod != null) {
						result = (Enum) getByCodeMethod.invoke (null, strValue);
					}
				} catch (Throwable e) {
				}
			}
		}
		return result;
	}

	private static Method getMethod (Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		try {
			return clazz.getMethod (methodName, parameterTypes);
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E enumValue (Class<E> toClass, Object o) {
		return (E) enumValue0 (toClass, o);
	}
}
