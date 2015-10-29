/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.magicbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.magicbeans.OpenMagicBeans"
)
@ActionRegistration(
        iconBase = "org/magicbeans/MB_Button.png",
        displayName = "#CTL_OpenMagicBeans"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/File", position = 0),
    @ActionReference(path = "Shortcuts", name = "D-L")
})
@Messages("CTL_OpenMagicBeans=Open Magic Beans")
public final class OpenMagicBeans implements ActionListener {
    public static boolean open = false;
    private static MBFrame frame = new MBFrame();
    private static MBTopComponent component = new MBTopComponent();
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        open = !open;
        component.open();
    }
}
