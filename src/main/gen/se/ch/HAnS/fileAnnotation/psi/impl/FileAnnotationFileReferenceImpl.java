// This is a generated file. Not intended for manual editing.
package se.ch.HAnS.fileAnnotation.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static se.ch.HAnS.fileAnnotation.psi.FileAnnotationTypes.*;
import se.ch.HAnS.referencing.impl.FeatureAnnotationNamedElementImpl;
import se.ch.HAnS.fileAnnotation.psi.*;

public class FileAnnotationFileReferenceImpl extends FeatureAnnotationNamedElementImpl implements FileAnnotationFileReference {

  public FileAnnotationFileReferenceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull FileAnnotationVisitor visitor) {
    visitor.visitFileReference(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof FileAnnotationVisitor) accept((FileAnnotationVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public FileAnnotationFileName getFileName() {
    return findNotNullChildByClass(FileAnnotationFileName.class);
  }

}
