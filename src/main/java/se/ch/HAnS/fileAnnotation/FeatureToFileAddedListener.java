package se.ch.HAnS.fileAnnotation;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class FeatureToFileAddedListener implements FileEditorManagerListener {
    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Project myProject = ProjectManager.getInstance().getDefaultProject();
        MappingService mappingService = new MappingService(myProject);
        // Messages.showMessageDialog(file.getName(), "hello", Messages.getInformationIcon());
        mappingService.ServiceMethod(file);
    }
}
