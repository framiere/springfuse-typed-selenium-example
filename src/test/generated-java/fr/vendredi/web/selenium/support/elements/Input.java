/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/support/element/Input.p.vm.java
 */
package fr.vendredi.web.selenium.support.elements;

import org.openqa.selenium.By;

public abstract class Input<T> extends CustomElement {
    public Input(String id) {
        super(id);
    }

    public abstract void type(T value);

    public abstract T value();

    protected void typeString(String value) {
        webClient.fill(By.id(id), value);
    }

    protected String valueAttribute() {
        return webClient.find(By.id(id)).getAttribute("value");
    }
}
