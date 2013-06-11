/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/support/element/Tab.p.vm.java
 */
package fr.vendredi.web.selenium.support.elements;

import static com.google.common.collect.Lists.newArrayList;
import static com.palominolabs.xpath.XPathUtils.getXPathString;
import static org.openqa.selenium.By.name;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Tab extends CustomElement {
    String idPlural;

    public Tab(String id, String idPlural) {
    	super(id);
        this.idPlural = idPlural;
    }

    public void open() {
        webClient.click(By.xpath("//div[@id='form:tabs']//a[@href='#form:tabs:" + idPlural + "']"));
    }

    public void add() {
    	clickInHeaderUsingClass("iconAdd");
    }

    public void search() {
    	clickInHeaderUsingClass("iconSearch");
    }
    
    private void clickInHeaderUsingClass(String className) {
    	String xpath = "//div[@id='form:tabs:" + idPlural + "']//th[contains(@class,'actions-column')]//div[contains(@class,'" + className + "')]";
        webClient.click(By.xpath(xpath));
    }

    public void edit(String value) {
        webClient.clickLinkTitle("Edit " + value);
    }

    public void delete(String value) {
        webClient.clickLinkTitle("Remove " + value);
    }

    public void select(String value) {
        webClient.clickLinkTitle("Select " + value);
    }

    public void remove(String value) {
        webClient.clickLinkTitle("Remove " + value);
    }
    
    public void confirmRemove() {
        webClient.click(name("form:askForRemove" + WordUtils.capitalize(id) + "DialogYes"));
    }
    
    public void cancelRemove() {
        webClient.click(name("form:askForRemove" + WordUtils.capitalize(id) + "DialogNo"));
    }
    
    public List<String> values(String columnName) {
    	String xpath = "//div[@id='form:tabs:" + idPlural + "']//tr/td[contains(@class," + getXPathString(columnName) + ")]";
    	List<String> ret = newArrayList();
    	for(WebElement webElement : webClient.findAll(By.xpath(xpath))) {
    		ret.add(webElement.getText());
    	}
    	return ret; 
    }

    public String value(String columnName, int line) {
    	String xpath = "//tbody[@id='form:tabs:" + idPlural + "List_data']//tr[" + line + "]/td[contains(@class," + getXPathString(columnName) + ")]";
    	return webClient.find(By.xpath(xpath)).getText(); 
    }
}
