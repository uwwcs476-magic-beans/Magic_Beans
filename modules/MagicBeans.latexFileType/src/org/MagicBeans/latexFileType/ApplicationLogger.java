/* 
 * Copyright (c) 2015 Sebastian Brudzinski
 * 
 * See the file LICENSE for copying permission.
 */
package org.MagicBeans.latexFileType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.MagicBeans.Editor.OutputTopComponent;

/**
 * Class responsible for logging any information to the user's
 * Logs output window.
 * 
 * @author Sebastian
 */
public class ApplicationLogger {
    private final String loggerName;
    private final OutputTopComponent otc = new OutputTopComponent();
    private final DateFormat loggerDateFormat = new SimpleDateFormat("MMM dd HH:mm:ss");
    
    
    public ApplicationLogger(String loggerName) {
        this.loggerName = loggerName;
    }
    
    public void log(String message) {
		Date currentDate = new Date();
        otc.logToOutput(loggerDateFormat.format(currentDate) + " [" + loggerName + "] " + message);
    }
    
    public void log(String[] messages) {
        for (String message : messages) {
            log(message);
        }
    }
    
    public void log(List<String> messages) {
        log((String[]) messages.toArray());
    }
    
}
