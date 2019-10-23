package u.com.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建日期：2019/10/15 on 11:59
 * 描述:
 * 作者: lvzishen
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Required {

}
