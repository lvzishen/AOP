package u.com.compiler.activity.method;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

import u.com.compiler.activity.ActivityClass;
import u.com.compiler.activity.field.Field;

/**
 * 创建日期：2019/10/17 on 10:34
 * 描述:
 * 作者: lvzishen
 */
public class StartMethod {
    private ActivityClass activityClass;
    private String methodName;
    private boolean isStaticMethod = true;
    private List<Field> fieldList = new ArrayList();


    public StartMethod copy(String methodName) {
        return new StartMethod(activityClass, methodName);
    }


    public StartMethod(ActivityClass activityClass, String methodName) {
        this.activityClass = activityClass;
        this.methodName = methodName;
    }

    public StartMethod setIsStaticMethod(boolean isStaticMethod) {
        this.isStaticMethod = isStaticMethod;
        return this;
    }

    public void addAllField(List<Field> list) {
        fieldList.addAll(list);
    }

    public void addField(Field field) {
        fieldList.add(field);
    }


    /**
     * public static void start(Context context, int age, String name, String company, float height) {
     * Intent intent = new Intent(context,MainActivity.class);
     * intent.putExtra("age",age);
     * intent.putExtra("name",name);
     * intent.putExtra("company",company);
     * intent.putExtra("height",height);
     * ActivityBuilder.INSTANCE.startActivity(context,intent);
     * }
     */
    public void build(TypeSpec.Builder typeBuilder) {
        ClassName contextClassName = ClassName.get("android.content", "Context");
        ParameterSpec parameterSpec = ParameterSpec.builder(contextClassName, "context").build();
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName).addModifiers(Modifier.PUBLIC).returns(TypeName.VOID).addParameter(parameterSpec);

        ClassName intent = ClassName.get("android.content", "Intent");
        methodBuilder.addStatement("$T intent = new $T(context,$T.class)", intent, intent, activityClass.typeElement);

        for (Field field : fieldList) {
            //获取参数类型
            TypeName fieldClassName = ClassName.get(field.variableElement.asType());
            //生成参数
            ParameterSpec parameterField = ParameterSpec.builder(fieldClassName, field.name).build();
            methodBuilder.addParameter(parameterField);
            //intent.putExtra("age",age); $S会自动加上双引号,$L放进去什么就是什么
            methodBuilder.addStatement("intent.putExtra($S,$L)", field.name, field.name);
        }

        if (isStaticMethod) {
            methodBuilder.addModifiers(Modifier.STATIC);
        } else {
            methodBuilder.addStatement("fillintent(intent)");
        }

        ClassName activityBuilder = ClassName.get("u.com.runtime", "ActivityBuilder");
        methodBuilder.addStatement("$T.INSTANCE.startActivity(context,intent)", activityBuilder);

        typeBuilder.addMethod(methodBuilder.build());

    }

}
