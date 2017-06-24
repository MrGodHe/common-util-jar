package cn.pay.ebank.common.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拷贝时，拷贝源 <br/>
 * eg.CopyFrom({"fiedda})<br/>
 * Created by zhyang on 2015/2/27 0027.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFrom {
	String[] value () default {};
}
