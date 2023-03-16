package se.ch.HAnS.fileAnnotation;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
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
import java.util.Iterator;
import java.util.Optional;
import com.intellij.util.containers.Stack;
import org.jetbrains.annotations.NotNull;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class MappingService {
    Project myProject = ProjectManager.getInstance().getDefaultProject();

    public MappingService(Project project) {
        this.myProject = project;
    }
    public void ServiceMethod() {

        Collection<VirtualFile> vfs = FilenameIndex.getVirtualFilesByName(".feature-to-file", GlobalSearchScope.allScope(myProject));
        Iterator<VirtualFile> it = vfs.iterator();
        VirtualFile vf = it.next();
        PsiManager psiManager = PsiManager.getInstance(myProject);
        PsiFile featureToFile = psiManager.findFile(vf);

        //Get all name of Files in the current directory
        PsiDirectory psiDirectory = featureToFile.getParent();
        PsiFile[] psiFiles = psiDirectory.getFiles();
        String message = String.valueOf(featureToFile.getText().contains(psiFiles[0].getName()));

        for (PsiFile File : psiFiles) {
            Boolean isInFeatureToFile = featureToFile.getText().contains(File.getName());
            if (isInFeatureToFile) {
                String feature = featureToFile.getText().split("\\R")[1];
                displayNotificationForFile(File, feature);
                // Messages.showMessageDialog(message, "hello", Messages.getInformationIcon());
            }
        }


    }
    //Notify User
    public void displayNotificationForFile(PsiFile file, String feature) {
        ApplicationManager.getApplication().invokeLater(() -> {
            Notification notification = new Notification(
                    "File mapped to feature group",
                    "File Mapped!",
                    "The file " + file.getName() + " is mapped to the feature " + feature,
                    NotificationType.INFORMATION);
            Notifications.Bus.notify(notification, myProject);
        });
    }
}