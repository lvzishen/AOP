package u.com.compiler.activity.field;


import javax.annotation.processing.Messager;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

import u.com.annotations.Optional;

/**
 * 创建日期：2019/10/15 on 17:43
 * 描述:
 * 作者: lvzishen
 */
public class OptionalField extends Field {
    public String prefix = "OPTIONAL_";
    private Object defaultValue = null;

    public OptionalField(VariableElement element) {
        super(element);
    }

    public void init() {
        Optional optional = variableElement.getAnnotation(Optional.class);
//        messager.printMessage(Diagnostic.Kind.WARNING, variableElement.asType().getKind().toString());
        if (variableElement.asType().getKind() == TypeKind.BOOLEAN) {
            defaultValue = optional.booleanValue();
        }
        if (variableElement.asType().getKind() == TypeKind.BYTE || variableElement.asType().getKind() == TypeKind.SHORT || variableElement.asType().getKind() == TypeKind.INT || variableElement.asType().getKind() == TypeKind.LONG || variableElement.asType().getKind() == TypeKind.CHAR) {
            defaultValue = optional.intValue();
        }
        if (variableElement.asType().getKind() == TypeKind.FLOAT || variableElement.asType().getKind() == TypeKind.DOUBLE) {
            defaultValue = optional.floatValue();
        }
        if (variableElement.asType().toString().equals("java.lang.String")) {
            defaultValue = optional.stringValue();
        }
    }

    @Override
    public String toString() {
        return defaultValue.toString();
    }
}
