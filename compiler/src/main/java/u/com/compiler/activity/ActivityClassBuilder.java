package u.com.compiler.activity;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;


import java.io.IOException;

import javax.annotation.processing.Filer;

import u.com.compiler.activity.method.ConstantBuilder;
import u.com.compiler.activity.method.StartMethodBuilder;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * 创建日期：2019/10/16 on 15:35
 * 描述:
 * 作者: lvzishen
 */
public class ActivityClassBuilder {
    private static final String POSIX = "Builder";
    private ActivityClass activityClass;
    public static final String METHOD_NAME = "start";
    public static final String METHOD_NAME_NO_OPTIONAL = METHOD_NAME + "WithoutOptional";
    public static final String METHOD_NAME_FOR_OPTIONAL = METHOD_NAME + "WithOptional";
    public static final String METHOD_NAME_FOR_OPTIONALS = METHOD_NAME + "WithOptionals";


    public ActivityClassBuilder(ActivityClass activityClass) {
        this.activityClass = activityClass;
    }

    public void build(Filer filer) {
        //抽象类不处理
        if (activityClass.isAbstract) {
            return;
        }
        //TypeSpec.Builder构造器，相当于可以创建出一个class，通过builder可以继续添加field，method等元素
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(activityClass.simpleName + POSIX).addModifiers(PUBLIC, FINAL);
        //新建常量
        ConstantBuilder constantBuilder = new ConstantBuilder(activityClass);
        constantBuilder.build(typeSpecBuilder);
        //新建方法
        StartMethodBuilder startMethodBuilder = new StartMethodBuilder(activityClass);
        startMethodBuilder.build(typeSpecBuilder);
////        typeSpecBuilder.addField(new FieldSpec(new FieldSpec.Builder()))
        writeToJavaFile(filer, typeSpecBuilder.build());
    }

    private void writeToJavaFile(Filer filer, TypeSpec typeSpec) {
        JavaFile file = JavaFile.builder(activityClass.packageName, typeSpec).build();
        try {
            file.writeTo(filer);
        } catch (IOException e) {

        }
    }
}
