package u.com.compiler.activity.method;

import com.squareup.javapoet.TypeSpec;


import java.util.ArrayList;
import java.util.List;

import u.com.compiler.activity.ActivityClass;
import u.com.compiler.activity.ActivityClassBuilder;
import u.com.compiler.activity.field.Field;
import u.com.compiler.activity.field.OptionalField;

/**
 * 创建日期：2019/10/17 on 10:34
 * 描述:
 * 作者: lvzishen
 */
public class StartMethodBuilder {
    private ActivityClass activityClass;

    public StartMethodBuilder(ActivityClass activityClass) {
        this.activityClass = activityClass;
    }

    //生成 以变量name为例->public static final REQUIRED_NAME="name";
    public void build(TypeSpec.Builder typeBuilder) {
        StartMethod startMethod = new StartMethod(activityClass, ActivityClassBuilder.METHOD_NAME);
        //filter
        List<Field> requiredFields = new ArrayList<>();
        List<Field> optionalFields = new ArrayList<>();
        for (Field field : activityClass.fieldSet) {
            if (field instanceof OptionalField) {
                optionalFields.add(field);
            } else {
                requiredFields.add(field);
            }
        }
        //先添加必传参数
        startMethod.addAllField(requiredFields);
        StartMethod startMethodNoOptional = startMethod.copy(ActivityClassBuilder.METHOD_NAME_NO_OPTIONAL);

        //非必传参数
        startMethod.addAllField(optionalFields);
        startMethod.build(typeBuilder);

        if (!optionalFields.isEmpty()) {
            startMethodNoOptional.build(typeBuilder);
        }

    }
}
