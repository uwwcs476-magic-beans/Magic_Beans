package org.magicbeans;

import org.openide.modules.ModuleInstall;
import org.openide.windows.OnShowing;



@OnShowing
public class MagicBeans extends ModuleInstall implements Runnable {

    @Override
    public void run() {
        System.out.println("Magic Beans Is ALIVE!!!");
    }
}
