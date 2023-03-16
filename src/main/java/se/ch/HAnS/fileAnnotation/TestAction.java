package se.ch.HAnS.fileAnnotation;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class TestAction extends AnAction{

    @Override
    public void update(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        event.getPresentation().setEnabledAndVisible(project != null);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        MappingService mappingService = new MappingService(project);
        mappingService.ServiceMethod();
    }
}
