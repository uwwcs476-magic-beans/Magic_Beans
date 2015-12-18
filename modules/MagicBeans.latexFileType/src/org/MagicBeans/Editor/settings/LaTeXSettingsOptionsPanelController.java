/* 
 * Copyright (c) 2015 Sebastian Brudzinski
 * 
 * See the file LICENSE for copying permission.
 */
package org.magicbeans.editor.settings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;

@OptionsPanelController.TopLevelRegistration(
        categoryName = "#OptionsCategory_Name_LaTeXSettings",
        iconBase = "org/magicbeans/editor/resources/icons/latex-ico.png",
        keywords = "#OptionsCategory_Keywords_LaTeXSettings",
        keywordsCategory = "LaTeXSettings"
)
@org.openide.util.NbBundle.Messages({"OptionsCategory_Name_LaTeXSettings=LaTeX", "OptionsCategory_Keywords_LaTeXSettings=latex, path"})
public final class LaTeXSettingsOptionsPanelController extends OptionsPanelController {

    private LaTeXSettingsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    @Override
    public void update() {
        getPanel().load();
        changed = false;
    }

    @Override
    public void applyChanges() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                getPanel().store();
                changed = false;
            }
        });
    }

    @Override
    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    @Override
    public boolean isValid() {
        return getPanel().valid();
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private LaTeXSettingsPanel getPanel() {
        if (panel == null) {
            panel = new LaTeXSettingsPanel(this);
        }
        return panel;
    }

    void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }
    private static String latexPath;
    public static void initPrefListener(){        
        Preferences pref = NbPreferences.forModule(LaTeXSettingsPanel.class);
        String path = pref.get("latexPath", "");
        pref.addPreferenceChangeListener(new PreferenceChangeListener() {
            public void preferenceChange(PreferenceChangeEvent evt) {
                if (evt.getKey().equals("latexPath")) {
                    latexPath = evt.getNewValue();
                }
            }
        });
        latexPath = path;
    }
    public static String getLatexPath(){
        if (latexPath == null){
            initPrefListener();
        }
        return latexPath;
    }

}
