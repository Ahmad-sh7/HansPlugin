package se.ch.HAnS.fileAnnotation;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import se.ch.HAnS.fileAnnotation.psi.FileAnnotationFile;
import se.ch.HAnS.fileAnnotation.psi.FileAnnotationFileReferences;
import se.ch.HAnS.fileAnnotation.psi.FileAnnotationLpq;

import java.util.*;

public class FileAnnotationUtil {

    /**
     * Searches the entire project for FeatureModel language files with instances of the Simple property with the given key.
     *
     * @param project current project
     * @param featurename     to check
     * @return matching properties
     */
    public static List<FileAnnotationLpq> findFileLPQs(Project project, String featurename) {
        List<FileAnnotationLpq> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(FileAnnotationFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            FileAnnotationFile fileAnnotationFile = (FileAnnotationFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (fileAnnotationFile != null) {
                FileAnnotationLpq[] properties = PsiTreeUtil.getChildrenOfType(fileAnnotationFile, FileAnnotationLpq.class);
                if (properties != null) {
                    for (FileAnnotationLpq property : properties) {
                        if (featurename.equals(property.getText())) {
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static List<FileAnnotationLpq> findFileLPQs(Project project) {
        List<FileAnnotationLpq> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(FileAnnotationFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            FileAnnotationFile folderAnnotationFileFile = (FileAnnotationFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (folderAnnotationFileFile != null) {
                FileAnnotationLpq[] properties = PsiTreeUtil.getChildrenOfType(folderAnnotationFileFile, FileAnnotationLpq.class);
                if (properties != null) {
                    Collections.addAll(result, properties);
                }
            }
        }
        return result;
    }


    public static Map<FileAnnotationLpq, FileAnnotationFileReferences> findAllFileMappings(Project project) {
        Map<FileAnnotationLpq, FileAnnotationFileReferences> featureFileMap = new HashMap<>();
        List<FileAnnotationLpq> fileAnnotationFeatureList = findFileLPQs(project);

        for (FileAnnotationLpq feature: fileAnnotationFeatureList ) {
            if (feature.getParent().getPrevSibling() instanceof FileAnnotationFileReferences ) {
                featureFileMap.put(feature, (FileAnnotationFileReferences)feature.getParent().getPrevSibling());
            }
        }

        return featureFileMap;
    }

    public static Map<FileAnnotationLpq, FileAnnotationFileReferences> findFileMapping(Project project, String featureName) {
        Map<FileAnnotationLpq, FileAnnotationFileReferences> featureFileMap = new HashMap<>();
        List<FileAnnotationLpq> fileAnnotationFeatureList = findFileLPQs(project, featureName);

        for (FileAnnotationLpq feature: fileAnnotationFeatureList ) {
            if (feature.getParent().getPrevSibling() instanceof FileAnnotationFileReferences ) {
                featureFileMap.put(feature, (FileAnnotationFileReferences)feature.getParent().getPrevSibling());
            }
        }

        return featureFileMap;
    }

}
