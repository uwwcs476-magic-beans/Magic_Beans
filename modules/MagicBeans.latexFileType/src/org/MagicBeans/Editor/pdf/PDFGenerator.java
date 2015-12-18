/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.magicbeans.editor.pdf;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.magicbeans.editor.runtime.CommandLineBuilder;
import org.magicbeans.editor.runtime.CommandLineExecutor;
import org.magicbeans.editor.settings.LaTeXSettingsOptionsPanelController;
import org.magicbeans.editor.util.ApplicationUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.openide.util.Exceptions;

/**
 *
 * @author Nicholas
 */
public class PDFGenerator {
    private PDFDisplay display;
    private String workingDir;
    private File file;
    public PDFGenerator(File file) {
        super();
        this.workingDir = file.getParentFile().getAbsolutePath();
        this.file = file;
        display = new PDFDisplay();
    }
    public PDFDisplay getDisplay(){
        return display;
    }
    public void  generate(){
        CommandLineExecutor.executeGeneratePDF(new CommandLineBuilder()
                .withPathToSource(file.getAbsolutePath())
                .withOutputDirectory(ApplicationUtils.getBuildDirectory(workingDir))
                .withJobname("preview")
                .withWorkingFile(file.getParentFile())
                .withLatexPath(LaTeXSettingsOptionsPanelController.getLatexPath())
        );
    }
    
    private static final double SCALE_FACTOR = 0.4;
    private static final int SCALE_TYPE = Image.SCALE_SMOOTH;
    public Image buildPDFPreview(PDPage pdfPage, int zoom) {
        if (pdfPage == null) {
            return null;
        }
         

        double newScale = SCALE_FACTOR * (((double)zoom)/100.0);
        BufferedImage pageImage = null;
        try {
            pageImage = pdfPage.convertToImage();
            int width = (int) (newScale * pageImage.getWidth());
            int height = (int) (newScale * pageImage.getHeight());
            int type = pageImage.getType();
            
            return pageImage.getScaledInstance(width, height, SCALE_TYPE);
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        return null;
    }
    private static PDDocument inputPDF;
    private static String PDF_PATH ;

    public PDPage getPDFPage(int number) {
        PDF_PATH = ApplicationUtils.getTempPDFFile(workingDir);
        
        PDPage page = null;
        
        File pdfFile = null;
        try {
            pdfFile = new File(PDF_PATH);
            if (pdfFile.exists()) {
                inputPDF = PDDocument.load(pdfFile);
                List<PDPage> allPages = inputPDF.getDocumentCatalog().getAllPages();
                if (allPages != null && !allPages.isEmpty() && allPages.size() >= number && number > 0) {
                    page = allPages.get(number - 1);
                }
            }
        } catch (IOException ex) {
            
        }
        closeDocument();
        return page;
    }
    
    public static void closeDocument() {
        if (inputPDF != null) {
            try {
                inputPDF.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    public class PDFDisplay {

        private int selectedPage;
        private int viewZoom;

        public PDFDisplay() {
            this.selectedPage = 1;
            this.viewZoom = 100;
        }

        public JPanel drawPreviewOnJPanel() {
            JPanel pdfImagePanel = new JPanel();

            Image generatedImage = buildPDFPreview(getPDFPage(selectedPage), viewZoom);

            if (generatedImage != null) {
                ImageIcon icon = new ImageIcon(generatedImage);
                JLabel picLabel = new JLabel(icon);
                pdfImagePanel.add(picLabel);
            }
            return pdfImagePanel;
        }

        public void previousPage() {
            selectedPage--;
        }

        public void nextPage() {
            selectedPage++;
        }

        public void setPage(int page) {
            selectedPage = page;
        }

        public int getSelectedPage() {
            return selectedPage;
        }

        public void setZoom(int zoom) {
            viewZoom = zoom;
        }
        public int getZoom () {
            return viewZoom;
        }
    }
}
