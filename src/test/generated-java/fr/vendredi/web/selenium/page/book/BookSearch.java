/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/pages/entity/SearchPage.e.vm.java
 */
package fr.vendredi.web.selenium.page.book;

import fr.vendredi.web.selenium.support.Page;
import fr.vendredi.web.selenium.support.elements.Autocomplete;
import fr.vendredi.web.selenium.support.elements.CustomElement;
import fr.vendredi.web.selenium.support.elements.Messages;
import fr.vendredi.web.selenium.support.elements.OrderBy;
import fr.vendredi.web.selenium.support.elements.SearchForm;
import fr.vendredi.web.selenium.support.elements.StringRange;
import fr.vendredi.web.selenium.support.elements.TableAction;

@Page
public class BookSearch {
    public TableAction table;
    public Messages messages;
    public BookSearchOrders orders;
    public BookSearchForm form;

    public static class BookSearchOrders extends CustomElement {
        public OrderBy accountId = new OrderBy("book_accountId");
        public OrderBy bookTitle = new OrderBy("book_bookTitle");
        public OrderBy numberOfPages = new OrderBy("book_numberOfPages");
    }

    public static class BookSearchForm extends SearchForm {
        public Autocomplete bookTitle = new Autocomplete("form:bookTitle");
        public StringRange numberOfPages = new StringRange("form:numberOfPages");
        public Autocomplete owner = new Autocomplete("form:ownerSelector");
    }
}