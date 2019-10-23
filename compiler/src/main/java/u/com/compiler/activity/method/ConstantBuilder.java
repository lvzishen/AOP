package u.com.compiler.activity.method;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

import u.com.compiler.activity.ActivityClass;
import u.com.compiler.activity.field.Field;

/**
 * 创建日期：2019/10/17 on 10:34
 * 描述:
 * 作者: lvzishen
 */
public class ConstantBuilder {
    private ActivityClass activityClass;

    public ConstantBuilder(ActivityClass activityClass) {
        this.activityClass = activityClass;
    }

    //生成 以变量name为例->public static final REQUIRED_NAME="name";
    public void build(TypeSpec.Builder typeBuilder) {
        for (Field field : activityClass.fieldSet) {
            typeBuilder.addField(FieldSpec.builder(String.class, field.prefix + field.name.toUpperCase()).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", field.name).build()); //$S在JavaPoet中就和String.format中%s一样,字符串的模板,将指定的字符串替换到$S的地方，需要注意的是替换后的内容，默认自带了双引号，如果不需要双引号包裹，需要使用$L.
        }
    }

}
