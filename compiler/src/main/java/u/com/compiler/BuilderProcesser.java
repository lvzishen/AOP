package u.com.compiler;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import u.com.annotations.Builder;
import u.com.annotations.Optional;
import u.com.annotations.Required;
import u.com.compiler.activity.ActivityClass;
import u.com.compiler.activity.field.Field;
import u.com.compiler.activity.field.OptionalField;


/**
 * 创建日期：2019/10/15 on 14:51
 * 描述:
 * 作者: lvzishen
 */

public class BuilderProcesser extends AbstractProcessor {
    private Elements elements;
    private Messager messager;
    private Filer filer;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 添加要处理的注解类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = new HashSet();
        annotationSet.add(Builder.class.getCanonicalName());
        annotationSet.add(Required.class.getCanonicalName());
        annotationSet.add(Optional.class.getCanonicalName());
        return annotationSet;
    }

    /**
     * 初始化工具类，init只会调用一次，确保工具类单例
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        //  工具辅助类
        elements = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        super.init(processingEnvironment);
    }
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        HashMap<Element, ActivityClass> hashMap = new HashMap<>();
        //获取被当前注解标注的类集合
        Set<? extends Element> builderSet = env.getElementsAnnotatedWith(Builder.class);
        for (Element element : builderSet) {
            ElementKind elementKind = element.getKind();
            if (elementKind.isClass()) {
                TypeElement typeElement = (TypeElement) element;
                if (typeElement.getSuperclass().toString().equals("android.app.Activity")) {
                    ActivityClass activityClass = new ActivityClass(elements, typeElement);
                    hashMap.put(element, activityClass);
                    messager.printMessage(Diagnostic.Kind.WARNING, activityClass.toString());
                } else {
                    messager.printMessage(Diagnostic.Kind.WARNING, "typeElement is not Activity,name is" + typeElement.getSimpleName());
                }
            }
        }

        Set<? extends Element> requiredSet = env.getElementsAnnotatedWith(Required.class);
        for (Element element : requiredSet) {
            ElementKind elementKind = element.getKind();
            if (elementKind.isField()) {
                VariableElement fieldElement = (VariableElement) element;
                //返回封装此元素（非严格意义上）的最里层元素。 属性被封装在类里，返回的是这个属性element对应的类element
                ActivityClass activityClass = hashMap.get(fieldElement.getEnclosingElement());
                if (activityClass != null) {
                    Field field = new Field(fieldElement);
                    activityClass.addField(field);
                    messager.printMessage(Diagnostic.Kind.WARNING, field.toString());
                } else {
                    messager.printMessage(Diagnostic.Kind.WARNING, "Field " + fieldElement.getSimpleName() + " is not anntotion in Activity " + fieldElement.getEnclosingElement().getSimpleName());
                }

            }
        }

        Set<? extends Element> optionalSet = env.getElementsAnnotatedWith(Optional.class);
        for (Element element : optionalSet) {
            ElementKind elementKind = element.getKind();
            if (elementKind.isField()) {
                VariableElement fieldElement = (VariableElement) element;
                //返回封装此元素（非严格意义上）的最里层元素。 属性被封装在类里，返回的是这个属性element对应的类element
                ActivityClass activityClass = hashMap.get(fieldElement.getEnclosingElement());
                if (activityClass != null) {
                    OptionalField optionalField = new OptionalField(fieldElement);
                    optionalField.init();
                    activityClass.addField(optionalField);
                    messager.printMessage(Diagnostic.Kind.WARNING, optionalField.toString());
                } else {
                    messager.printMessage(Diagnostic.Kind.WARNING, "Field " + fieldElement.getSimpleName() + " is not anntotion in Activity " + fieldElement.getEnclosingElement().getSimpleName());
                }

            }
        }

        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
//            Element element = (Element) entry.getKey();
            ActivityClass activityClass = (ActivityClass) entry.getValue();
            activityClass.activityClassBuilder.build(filer);
        }
        return true;
    }

}
