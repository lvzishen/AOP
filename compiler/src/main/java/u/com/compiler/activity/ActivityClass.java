package u.com.compiler.activity;

import java.util.TreeSet;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import u.com.compiler.activity.field.Field;


/**
 * 创建日期：2019/10/15 on 17:28
 * 描述:
 * 作者: lvzishen
 */
public class ActivityClass {
    public String simpleName;
    public String packageName;
    public boolean isAbstract;
    public TypeElement typeElement;
    public TreeSet<Field> fieldSet = new TreeSet();
    public ActivityClassBuilder activityClassBuilder;

    public ActivityClass(Elements mElementUtils, TypeElement typeElement) {
        this.typeElement = typeElement;
        simpleName = typeElement.getSimpleName().toString();
        packageName = mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        isAbstract = typeElement.getModifiers().contains(Modifier.ABSTRACT);
        activityClassBuilder = new ActivityClassBuilder(this);
    }

    public void addField(Field field) {
        fieldSet.add(field);
    }

    @Override
    public String toString() {
        return simpleName + "$" + packageName + "$" + isAbstract;
    }
}
