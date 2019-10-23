package u.com.compiler.activity.field;


import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * 创建日期：2019/10/15 on 17:34
 * 描述:
 * 作者: lvzishen
 */
public class Field implements Comparable<Field> {
    public String name;
    public boolean isPrivate;
    //是否是基本数据类型
    public boolean isPrinmitive;
    public String prefix = "REQUIRED_";
    public VariableElement variableElement;

    public Field(VariableElement element) {
        this.variableElement = element;
        name = element.getSimpleName().toString();
        isPrivate = element.getModifiers().contains(Modifier.PRIVATE);
        isPrinmitive = element.asType().getKind().isPrimitive();
    }

    public void asJavaTypeName() {
        variableElement.asType().toString();
    }

    @Override
    public String toString() {
        return name + "$" + isPrivate + "$" + isPrinmitive;
    }

    @Override
    public int compareTo(Field other) {
        return name.compareTo(other.name);
    }
}
