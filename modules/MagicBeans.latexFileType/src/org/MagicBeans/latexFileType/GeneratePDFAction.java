/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.MagicBeans.latexFileType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "org.MagicBeans.latexFileType.GeneratePDFAction"
)
@ActionRegistration(
        iconBase = "org/MagicBeans/latexFileType/document-pdf-text24.png",
        displayName = "#CTL_GeneratePDFAction"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/Build", position = 500),
    @ActionReference(path = "Loaders/text/latex/Actions", position = 250, separatorAfter = 275)
})
@Messages("CTL_GeneratePDFAction=Generate PDF")
public final class GeneratePDFAction implements ActionListener {

    private final latexDataObject context;

    public GeneratePDFAction(latexDataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        PDFGenerator.currentGenerator.savePDF();
    }
}
