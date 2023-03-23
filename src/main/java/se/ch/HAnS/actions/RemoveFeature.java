package se.ch.HAnS.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import io.ktor.http.ContentType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
            Document document = PsiDocumentManager.getInstance(myProject).getDocument(openedFile);

            openedFile.accept(new PsiRecursiveElementVisitor() {
                @Override
                public void visitElement(@NotNull PsiElement element) {

                    super.visitElement(element);
                }

                final AtomicReference<PsiComment> reference = new AtomicReference<>();
                PsiComment startcomment = null;
                @Override

                public void visitComment(@NotNull PsiComment comment) {
                    String feuturename =  e.getData(PlatformDataKeys.PSI_ELEMENT).getText();
                    String[] FeaturesSplitted = feuturename.split("\\R");
                    //Messages.showMessageDialog(myProject, FeaturesSplitted[0], "Hi", Messages.getInformationIcon());
                    if (comment.getText().contains(FeaturesSplitted[0])) {
                        int lineNumber = openedFile.getViewProvider().getDocument().getLineNumber(comment.getTextRange().getStartOffset() + 1);
                        System.out.println("Found Update at in " + openedFile.getName() + "  at line number " + (lineNumber + 1));
                        //final AtomicReference<PsiComment> reference = new AtomicReference<>();

                        if (comment.getText().contains("&begin")) {
                             reference.set(comment);
                        } else {
                            if (comment.getText().contains("&end")) {
                                deletePsiElementRange(myProject, reference.get(), comment, document);
                            } else{
                                if (comment.getText().contains("&line")) {
                                    ApplicationManager.getApplication().invokeLater(() -> {
                                        WriteCommandAction.runWriteCommandAction(myProject, () -> {


                                            document.deleteString(document.getLineStartOffset(lineNumber), comment.getTextOffset() + comment.getTextLength() + 1);
                                        });
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }
    }
    public void deletePsiElementRange(Project myProject, PsiElement startElement, PsiElement endElement, Document document) {
        ApplicationManager.getApplication().invokeLater(() -> {
            WriteCommandAction.runWriteCommandAction(myProject, () -> {
                PsiElement current = startElement;
                while (current.getNextSibling() != endElement) {
                    if (current.getNextSibling() == null) {
                        return;
                        // current = current.getParent().findElementAt(current.getTextOffset() + current.getTextLength());
                    } else{
                        current = current.getNextSibling();
                    }
                    Messages.showMessageDialog(myProject, current.toString(), "Hi", Messages.getInformationIcon());
                }
                current = current.getPrevSibling();
                //Messages.showMessageDialog(myProject, startElement.getText(), "Hi", Messages.getInformationIcon());
                //Messages.showMessageDialog(myProject, current.getText(), "Hi", Messages.getInformationIcon());
                //Messages.showMessageDialog(myProject, endElement.getText(), "Hi", Messages.getInformationIcon());
                startElement.getParent().deleteChildRange(startElement, current);
                endElement.delete();
            });
        });
        PsiDocumentManager.getInstance(myProject).doPostponedOperationsAndUnblockDocument(document);
    }
}
