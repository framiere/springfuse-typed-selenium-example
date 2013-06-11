/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.legacy;

import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.elements.Autocomplete;
import fr.vendredi.web.selenium.support.elements.CustomElement;
import fr.vendredi.web.selenium.support.elements.Messages;
import fr.vendredi.web.selenium.support.elements.OrderBy;
import fr.vendredi.web.selenium.support.elements.SearchForm;
import fr.vendredi.web.selenium.support.elements.StringInput;
import fr.vendredi.web.selenium.support.elements.TableAction;

@Page
public class LegacySearch {
    public TableAction table;
    public Messages messages;
    public LegacySearchOrders orders;
    public LegacySearchForm form;

    public static class LegacySearchOrders extends CustomElement {
        public OrderBy name = new OrderBy("legacy_name");
        public OrderBy code = new OrderBy("legacy_code");
        public OrderBy dept = new OrderBy("legacy_dept");
        public OrderBy extraInfo = new OrderBy("legacy_extraInfo");
    }

    public static class LegacySearchForm extends SearchForm {
        public StringInput name = new StringInput("form:name");
        public StringInput code = new StringInput("form:code");
        public StringInput dept = new StringInput("form:dept");
        public Autocomplete extraInfo = new Autocomplete("form:extraInfo");
    }
}