/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/EditPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.document;

import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.elements.CustomElement;
import fr.vendredi.web.selenium.support.elements.EditAction;
import fr.vendredi.web.selenium.support.elements.ManyToOne;
import fr.vendredi.web.selenium.support.elements.Messages;
import fr.vendredi.web.selenium.support.elements.Upload;

@Page
public class DocumentEdit {
    public EditAction action;
    public Messages messages;
    public DocumentEditForm form;
    public DocumentEditTabs tabs;

    public static class DocumentEditForm extends CustomElement {
        public Upload documentBinary = new Upload("form:documentBinary");

        public ManyToOne owner = new ManyToOne("form:owner");
    };

    public static class DocumentEditTabs extends CustomElement {
    };
}