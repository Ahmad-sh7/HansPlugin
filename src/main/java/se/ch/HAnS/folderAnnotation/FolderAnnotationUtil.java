package se.ch.HAnS.folderAnnotation;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import se.ch.HAnS.folderAnnotation.psi.FolderAnnotationFile;
import se.ch.HAnS.folderAnnotation.psi.FolderAnnotationLpq;

import java.util.*;

public class FolderAnnotationUtil {
    /**
     * Searches the entire project for FeatureModel language files with instances of the Simple property with the given key.
     *
     * @param project current project
     * @param featurename     to check
     * @return matching properties
     */
    public static List<FolderAnnotationLpq> findFolderFeatures(Project project, String featurename) {
        List<FolderAnnotationLpq> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(FolderAnnotationFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            FolderAnnotationFile folderAnnotationFileFile = (FolderAnnotationFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (folderAnnotationFileFile != null) {
                FolderAnnotationLpq[] properties = PsiTreeUtil.getChildrenOfType(folderAnnotationFileFile, FolderAnnotationLpq.class);
                if (properties != null) {
                    for (FolderAnnotationLpq property : properties) {
                        if (featurename.equals(property.getText())) {
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<FolderAnnotationLpq> findFolderFeatures(Project project) {
        List<FolderAnnotationLpq> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(FolderAnnotationFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            FolderAnnotationFile folderAnnotationFileFile = (FolderAnnotationFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (folderAnnotationFileFile != null) {
                FolderAnnotationLpq[] properties = PsiTreeUtil.getChildrenOfType(folderAnnotationFileFile, FolderAnnotationLpq.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }

    public static Map<FolderAnnotationLpq, PsiDirectory> findAllFolderMappings(Project project) {
        Map<FolderAnnotationLpq, PsiDirectory> featureFolderMap = new HashMap<>();
        List<FolderAnnotationLpq> folderAnnotationFeatureList = findFolderFeatures(project);

        for (FolderAnnotationLpq feature: folderAnnotationFeatureList ) {
            featureFolderMap.put(feature, feature.getContainingFile().getContainingDirectory());
        }

        return featureFolderMap;
    }

    public static Map<FolderAnnotationLpq, PsiDirectory> findFolderMapping(Project project, String featureName) {
        Map<FolderAnnotationLpq, PsiDirectory> featureFolderMap = new HashMap<>();
        List<FolderAnnotationLpq> folderAnnotationFeatureList = findFolderFeatures(project, featureName);

        for (FolderAnnotationLpq feature: folderAnnotationFeatureList ) {
            featureFolderMap.put(feature, feature.getContainingFile().getContainingDirectory());
        }

        return featureFolderMap;
    }
}
