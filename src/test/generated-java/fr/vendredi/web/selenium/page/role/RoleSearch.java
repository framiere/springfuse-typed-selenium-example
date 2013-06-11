/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.role;

import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.elements.Autocomplete;
import fr.vendredi.web.selenium.support.elements.CustomElement;
import fr.vendredi.web.selenium.support.elements.Messages;
import fr.vendredi.web.selenium.support.elements.OrderBy;
import fr.vendredi.web.selenium.support.elements.SearchForm;
import fr.vendredi.web.selenium.support.elements.TableAction;

@Page
public class RoleSearch {
    public TableAction table;
    public Messages messages;
    public RoleSearchOrders orders;
    public RoleSearchForm form;

    public static class RoleSearchOrders extends CustomElement {
        public OrderBy roleName = new OrderBy("role_roleName");
    }

    public static class RoleSearchForm extends SearchForm {
        public Autocomplete roleName = new Autocomplete("form:roleName");
    }
}