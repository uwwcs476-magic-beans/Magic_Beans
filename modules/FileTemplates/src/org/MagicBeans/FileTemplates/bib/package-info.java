/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@TemplateRegistration(
        folder = "LaTeX",
        iconBase="org/MagicBeans/FileTemplates/bib/bib.gif", 
        displayName = "#BibTemplate_displayName", 
        content = "BibTemplate.bib",
        description = "BibTemplateDescription.html",
        scriptEngine="freemarker")
@Messages(value = "BibTemplate_displayName=Sample Bib File")
package org.MagicBeans.FileTemplates.bib;

import org.netbeans.api.templates.TemplateRegistration;
import org.openide.util.NbBundle.Messages;