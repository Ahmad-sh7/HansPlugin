package se.ch.HAnS.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class RemoveFeature extends AnAction {
    public void update(@NotNull AnActionEvent event){
        Project myProject = event.getProject();
        event.getPresentation().setEnabledAndVisible(myProject != null);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project myProject =  e.getProject();
        Collection<VirtualFile> javaFiles = FileTypeIndex.getFiles(
                FileTypeManager.getInstance().getFileTypeByExtension("java")
                ,GlobalSearchScope.projectScope(Objects.requireNonNull(myProject)));


        Iterator<VirtualFile> it = javaFiles.iterator();
        PsiManager psiManager = PsiManager.getInstance(myProject);



        while (it.hasNext()) {

            PsiFile openedFile = psiManager.findFile(it.next());

            openedFile.accept(new PsiRecursiveElementVisitor() {
                @Override
                public void visitElement(@NotNull PsiElement element) {

                    super.visitElement(element);
                }
                @Override
                public void visitComment(@NotNull PsiComment comment) {
                    String feuturename =  e.getData(PlatformDataKeys.PSI_ELEMENT).getText();
                    String[] FeaturesSplitted = feuturename.split("\\R");
                    //Messages.showMessageDialog(myProject, FeaturesSplitted[0], "Hi", Messages.getInformationIcon());
                    if (comment.getText().contains(FeaturesSplitted[0])) {
                        int lineNumber = openedFile.getViewProvider().getDocument().getLineNumber(comment.getTextRange().getStartOffset() + 1);
                        System.out.println("Found Update at in " + openedFile.getName() + "  at line number " + (lineNumber + 1));
                    }

                }
            });


        }

    }

}
