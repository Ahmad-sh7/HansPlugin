package se.ch.HAnS.codeAnnotation.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import se.ch.HAnS.codeAnnotation.CodeAnnotationLanguage;

public class CodeAnnotationElementType extends IElementType {
    public CodeAnnotationElementType(@NotNull @NonNls String debugName) {
        super(debugName, CodeAnnotationLanguage.INSTANCE);
    }
}