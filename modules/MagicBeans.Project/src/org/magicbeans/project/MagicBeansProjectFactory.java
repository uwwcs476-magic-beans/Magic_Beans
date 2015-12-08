/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.magicbeans.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=ProjectFactory.class)
public class MagicBeansProjectFactory implements ProjectFactory{

    public static final String PROJECT_FILE = "tex";

    //Specifies when a project is a project, i.e.,
    //if "customer.txt" is present in a folder:
    @Override
    public boolean isProject(FileObject projectDirectory) {
        FileObject children[] = projectDirectory.getChildren();
        for (FileObject child:children){
            if (child.getExt().equals(PROJECT_FILE)){
                return true;
            }
        }
        return  false;
    }

    //Specifies when the project will be opened, i.e., if the project exists:
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new MagicBeansProject(dir, state) : null;
    }

    @Override
    public void saveProject(final Project project) throws IOException, ClassCastException {
        // leave unimplemented for the moment
    }
    
}
