package se.ch.HAnS.fileAnnotation;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.ui.EditorNotifications;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Optional;

public class MappingService{
    Project myProject = ProjectManager.getInstance().getDefaultProject();

    public MappingService(Project project) {
        this.myProject = project;
    }
    public void ServiceMethod(PsiElement element){
        Collection<VirtualFile> vfs = FilenameIndex.getVirtualFilesByName(".feature-to-folder", GlobalSearchScope.allScope(myProject));
        VirtualFile vf = vfs.iterator().next();
        PsiManager psiManager = PsiManager.getInstance(myProject);
        PsiFile psiFile = psiManager.findFile(vf);

    }
}
