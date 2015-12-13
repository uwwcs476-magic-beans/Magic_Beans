/* 
 * Copyright (c) 2015 Sebastian Brudzinski
 * 
 * See the file LICENSE for copying permission.
 */
package latexstudio.editor.pdf;

import java.awt.Point;
import latexstudio.editor.runtime.CommandLineExecutor;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import org.MagicBeans.Editor.EditorTopComponent;
import latexstudio.editor.files.FileService;
import latexstudio.editor.runtime.CommandLineBuilder;
import latexstudio.editor.util.ApplicationUtils;
import org.openide.util.Exceptions;

/**
 * A thread that is responsible for refreshing the PDF preview when necessary.
 * @author Sebastian
 */
public class PDFPreviewRefresher implements Runnable {
    
    private final JScrollPane jScrollPane;
    private final EditorTopComponent etc;
    private final PDFDisplay pdfDisplay;
    
    private static final Logger LOGGER = Logger.getLogger(PDFPreviewRefresher.class.getName());
    
    public PDFPreviewRefresher(JScrollPane jScrollPane, EditorTopComponent etc, PDFDisplay pdfDisplay) {
        this.jScrollPane = jScrollPane;
        this.etc = etc;
        this.pdfDisplay = pdfDisplay;
    }

    @Override
    public void run() {
        while(true) {
            if (etc.isDirty()) {
                compileTemporaryFile();
                drawPreview();
                etc.setDirty(false);
            }
            
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    private void drawPreview() {
        JPanel pdfImagePanel = pdfDisplay.drawPreviewOnJPanel();
       
        if (pdfImagePanel != null) {
            JViewport vp = jScrollPane.getViewport();
            Point p = vp.getViewPosition();
            vp.setView(pdfImagePanel);
            vp.setViewPosition(p);

        }
    }

    private void compileTemporaryFile() {        
        String fileLocation = ApplicationUtils.getTempSourceFile();
        String content = etc.getEditorContent();
        
        FileService.writeToFile(fileLocation, content);

        CommandLineExecutor.executeGeneratePDF(new CommandLineBuilder()
                .withPathToSource(ApplicationUtils.getTempSourceFile())
                .withOutputDirectory(ApplicationUtils.getAppDirectory())
                .withJobname("preview")
                .withWorkingFile(etc.getCurrentFile())
                .withLatexPath(etc.getLatexPath())
        );
    }

}
