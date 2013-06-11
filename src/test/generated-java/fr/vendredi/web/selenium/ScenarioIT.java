/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/ScenarioIT.p.vm.java
 */
package fr.vendredi.web.selenium;

import org.junit.Test;
import org.openqa.selenium.By;

import fr.vendredi.web.selenium.page.account.AccountEdit;
import fr.vendredi.web.selenium.page.account.AccountSearch;
import fr.vendredi.web.selenium.page.document.DocumentEdit;
import fr.vendredi.web.selenium.page.document.DocumentSearch;
import fr.vendredi.web.selenium.page.role.RoleEdit;
import fr.vendredi.web.selenium.page.role.RoleSearch;
import fr.vendredi.web.selenium.support.SeleniumTest;

public class ScenarioIT extends SeleniumTest {
    // account
    AccountSearch accountSearch;
    AccountEdit accountEdit;

    // document
    DocumentSearch documentSearch;
    DocumentEdit documentEdit;

    // role
    RoleSearch roleSearch;
    RoleEdit roleEdit;

    @Test
    public void asAnAdminICanUpdateAUserAndLogInWithThisUpdatedUser() {
        String userName = "homer";
        englishHomePage();
        loginAsAnAdmin();
        loggedHomePage.accounts();
        searchAndEditAccount(userName);
        logout();
        loginAsPreviouslyModifiedUser();
        logout();
        loginAsAnAdmin();
        revertChangesDoneToUser(userName);
    }

    private void searchAndEditAccount(String userName) {
        selectAccount(userName);
        updateAccount("cnorris", "kickass", "gmail@chucknorris.com", "Paris");
        addRoleAdminToAccount();
        createRoleGodToAccount();
        addDocumentToAccount();
        saveToDatabase("cnorris");
    }

    private void updateAccount(String userName, String password, String email, String homeAddress) {
        accountEdit.form.username.type(userName);
        accountEdit.form.password.type(password);
        accountEdit.form.homeAddress.type(homeAddress);
        accountEdit.form.email.type(email);
    }

    private void selectAccount(String userName) {
        webClient.step("Search by username, select the user, and update its value");
        accountSearch.form.username.type(userName);
        accountSearch.form.search();
        accountSearch.table.hasText(userName + "@example.com");
        accountSearch.table.edit(userName);
    }

    private void addRoleAdminToAccount() {
        webClient.step("Add a ROLE_ADMIN to the selected user");
        accountEdit.tabs.securityRoles.open();
        accountEdit.tabs.securityRoles.search();
        roleSearch.form.roleName.type("ADMIN");
        roleSearch.form.search();
        roleSearch.table.hasSize(1);
        roleSearch.table.select("ROLE_ADMIN");
        accountEdit.messages.hasMessage("ROLE_ADMIN: Selected existing and added it, but not saved in database");
    }

    private void createRoleGodToAccount() {
        webClient.step("Create a ROLE_GOD for the the selected user");
        accountEdit.tabs.securityRoles.add();
        roleEdit.form.roleName.type("ROLE_GOD");
        accountEdit.action.ok();
        accountEdit.messages.hasMessage("ROLE_GOD: Created and added, but not saved in database");
    }

    private void addDocumentToAccount() {
        webClient.step("Add a document");
        accountEdit.tabs.edocs.open();
        accountEdit.tabs.edocs.add();
        documentEdit.form.documentBinary.upload("./src/test/resources/for_upload.txt");
        documentEdit.action.ok();
    }

    private void saveToDatabase(String userName) {
        webClient.step("Save to database");
        accountEdit.action.save();
        accountSearch.messages.hasMessage("Saved " + userName + " successfully in database");
    }

    private void loginAsPreviouslyModifiedUser() {
        webClient.step("Let's try to log as cnorris as set previously");
        anonymousHomePage.connexion();
        loginPage.login("cnorris", "kickass");
        loggedHomePage.accounts();
    }

    private void revertChangesDoneToUser(String userName) {
        webClient.step("Select account and revert previous changes");
        loggedHomePage.accounts();
        accountSearch.form.username.type("cnorris");
        accountSearch.form.search();
        accountSearch.table.hasText("gmail@chucknorris.com");
        accountSearch.table.edit("cnorris");
        accountSearch.table.hasText("Username");
        updateAccount(userName, userName, userName + "@example.com", "Tokyo");
        accountEdit.tabs.edocs.open();
        accountEdit.tabs.edocs.remove("for_upload.txt");
        accountEdit.messages.hasMessage("for_upload.txt: Removed, but not saved in database");
        accountEdit.tabs.securityRoles.open();
        accountEdit.tabs.securityRoles.remove("ROLE_ADMIN");
        accountEdit.messages.hasMessage("ROLE_ADMIN: Removed, but not saved in database");
        accountEdit.tabs.securityRoles.remove("ROLE_GOD");
        accountEdit.messages.hasMessage("ROLE_GOD: Removed, but not saved in database");
        accountEdit.action.save();
        accountSearch.messages.hasMessage("Saved " + userName + " successfully in database");
        loggedHomePage.home();
        loggedHomePage.roles();
        roleSearch.table.delete("ROLE_GOD");
        roleSearch.messages.hasMessage("Suppressed ROLE_GOD successfully from database");
    }
}
