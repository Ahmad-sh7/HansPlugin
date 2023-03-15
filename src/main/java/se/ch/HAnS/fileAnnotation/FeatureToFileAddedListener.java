package se.ch.HAnS.fileAnnotation;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.ProjectManagerListener;

public class FeatureToFileAddedListener implements ProjectManagerListener {

    public void fileAdded() {
        MappingService mappingService = ApplicationManager.getApplication().getService(MappingService.class);
    }

}
