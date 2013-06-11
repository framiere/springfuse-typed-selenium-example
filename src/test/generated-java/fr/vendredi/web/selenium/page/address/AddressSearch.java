/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.address;

import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.elements.Autocomplete;
import fr.vendredi.web.selenium.support.elements.CustomElement;
import fr.vendredi.web.selenium.support.elements.Messages;
import fr.vendredi.web.selenium.support.elements.OrderBy;
import fr.vendredi.web.selenium.support.elements.SearchForm;
import fr.vendredi.web.selenium.support.elements.TableAction;

@Page
public class AddressSearch {
    public TableAction table;
    public Messages messages;
    public AddressSearchOrders orders;
    public AddressSearchForm form;

    public static class AddressSearchOrders extends CustomElement {
        public OrderBy streetName = new OrderBy("address_streetName");
        public OrderBy city = new OrderBy("address_city");
    }

    public static class AddressSearchForm extends SearchForm {
        public Autocomplete streetName = new Autocomplete("form:streetName");
        public Autocomplete city = new Autocomplete("form:city");
    }
}