/*
 * (c) Copyright 2005-2013 JAXIO, http://www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to purchase Celerio ? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Template pack-selenium-primefaces:src/test/java/selenium/support/WebClient.p.vm.java
 */
package fr.vendredi.web.selenium.support;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.web.util.JavaScriptUtils.javaScriptEscape;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StopWatch;

import com.google.common.base.Function;

import fr.vendredi.web.selenium.support.elements.WebElementConfiguration;

public class WebClient {
    public final WebDriver webDriver;
    Timeouts timeout;
    public final String baseUrl;

    private long driverWaitBeforeStopInSeconds = 5;
    private long waitAfterClickInMs = 0;
    private long waitAfterClearInMs = 0;
    private long waitAfterStepInMs = 0;
    private long waitAfterFillMs = 0;
    private long waitAfterNotificationMs = 0;
    private boolean followVisually;

    public WebClient(WebClientBuilder builder) {
        this.webDriver = builder.webDriver;
        this.baseUrl = builder.baseUrl;
        this.driverWaitBeforeStopInSeconds = builder.waitTimeInSeconds;
        this.followVisually = builder.followVisually;
        if (followVisually) {
            waitAfterClickInMs = 250;
            waitAfterClearInMs = 250;
            waitAfterStepInMs = 4000;
            waitAfterFillMs = 250;
            waitAfterNotificationMs = 2000;
        }
        timeout = webDriver.manage().timeouts().implicitlyWait(driverWaitBeforeStopInSeconds, TimeUnit.SECONDS);
        webDriver.manage().window().setSize(new Dimension(1280, 1024));

        new WebElementConfiguration().configure(builder.testInstance, this);
    }

    public void fast() {
        webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public void back() {
        webDriver.manage().timeouts().implicitlyWait(driverWaitBeforeStopInSeconds, TimeUnit.SECONDS);
    }

    public void hasTitle(String title) {
        hasText("<title>" + title + "</title>");
    }

    public void hasText(String text) {
        try {
            until(contains(text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]", e);
        }
    }

    public void hasText(WebElement webElement, String text) {
        try {
            until(new TextContains(webElement, text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]", e);
        }
    }

    public void hasNotText(WebElement webElement, String text) {
        try {
            until(new TextNotEquals(webElement, text));
            success("Found different text than [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find a text different to [" + text + "]", e);
        }
    }

    public void waitUntilDisplayed(By by) {
        try {
            until(displayed(by));
        } catch (RuntimeException e) {
            error("not displayed " + by, e);
        }
    }

    public void waitUntilRemoved(By by) {
        try {
            until(removed(by, driverWaitBeforeStopInSeconds));
        } catch (RuntimeException e) {
            error("not removed " + by, e);
        }
    }

    public void waitUntilDisplayed(WebElement webElement) {
        try {
            until(displayed(webElement));
        } catch (RuntimeException e) {
            error("element " + webElement.getTagName() + " is not displayed", e);
        }
    }

    public void waitUntilEnabled(WebElement webElement) {
        try {
            until(enabled(webElement));
        } catch (RuntimeException e) {
            error("element " + webElement.getTagName() + " is not enabled", e);
        }
    }

    public void waitUntilEnabled(By by) {
        try {
            until(enabled(by));
        } catch (RuntimeException e) {
            error("element " + by + " is not enabled", e);
        }
    }

    public boolean until(Function<WebDriver, Boolean> function) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // iterate until we have no more StaleElementReferenceException
            while (true) {
                try {
                    return tryUntil(function);
                } catch (StaleElementReferenceException e) {
                    //
                }
            }
        } finally {
            stopWatch.stop();
            // System.out.println(function.getClass().getName() + " took " + stopWatch.getTotalTimeSeconds() + "s");
        }
    }

    private boolean tryUntil(Function<WebDriver, Boolean> function) {
        // test the function now
        if (function.apply(webDriver)) {
            return true;
        }
        // nope ? ok, once more
        if (function.apply(webDriver)) {
            return true;
        }
        // test for 1 second with very rapid tests
        try {
            if (new WebDriverWait(webDriver, 1).until(function)) {
                return true;
            }
        } catch (TimeoutException e) {
            // no op
        }
        // ok it's still not ready, so let's wait
        return new WebDriverWait(webDriver, driverWaitBeforeStopInSeconds).until(function);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }

    public static ExpectedCondition<Boolean> displayed(final WebElement webElement) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return webElement.isDisplayed();
            }
        };
    }

    public static ExpectedCondition<Boolean> removed(final By by, final long driverWaitBeforeStopInSeconds) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                try {
                    from.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                    return from.findElements(by).isEmpty();
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    from.manage().timeouts().implicitlyWait(driverWaitBeforeStopInSeconds, TimeUnit.SECONDS);
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> displayed(final By by) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                try {
                    WebElement findElement = from.findElement(by);
                    return findElement.isDisplayed();
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    return false;
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> enabled(final WebElement webElement) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return webElement.isEnabled();
            }
        };
    }

    public static ExpectedCondition<Boolean> enabled(final By by) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver from) {
                return from.findElement(by).isEnabled();
            }
        };
    }

    public void step(String text) {
        if (followVisually) {
            message(text);
            sleep(waitAfterStepInMs);
        }
    }

    public void message(String text) {
        notification(text, "info");
    }

    public void warning(String text) {
        notification(text, "warn");
    }

    public void error(String text, Throwable e) {
        if (followVisually) {
            notification(text, "error");
            sleep(60);
        }
        throw new RuntimeException(text, e);
    }

    public void success(String text) {
        notification(text, "succ_bg");
    }

    public void notification(String text, String type) {
        if (!followVisually) {
            return;
        }
        String notification = "growlNotificationBar.renderMessage({detail: '" + javaScriptEscape(text) + "', severity: '" + type + "'});";
        ((JavascriptExecutor) webDriver).executeScript(notification);
        sleep(waitAfterNotificationMs);
    }

    public void sleep(long sleepInMs) {
        try {
            MILLISECONDS.sleep(sleepInMs);
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickLinkTitle(String title) {
        clickCss("a[title=\"" + javaScriptEscape(title) + "\"]");
    }

    public void clickCss(String css) {
        click(By.cssSelector(css));
    }

    public void click(By by) {
        try {
            waitUntilDisplayed(by);
            click(find(by));
        } catch (StaleElementReferenceException e) {
            System.out.println("Not found " + by.toString());
            throw e;
        }
    }

    public List<WebElement> findAllNow(By by) {
        fast();
        try {
            return findAll(by);
        } finally {
            back();
        }
    }

    public List<WebElement> findAll(By by) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int i = 0;
        try {
            List<WebElement> findElements = webDriver.findElements(by);
            i = findElements.size();
            return findElements;
        } finally {
            stopWatch.stop();
            System.out.println("found " + i + " results in " + stopWatch.getTotalTimeSeconds() + "s for " + by);
        }
    }

    public WebElement find(By by) {
        return webDriver.findElement(by);
    }

    public void click(WebElement webElement) {
        message("Clicking");
        waitUntilDisplayed(webElement);
        webElement.click();
        sleep(waitAfterClickInMs);
    }

    public void page(String relative) {
        webDriver.get(baseUrl + relative);
    }

    public void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            sleep(waitAfterClearInMs);
        }
    }

    public void fill(By by, String text) {
        fill(find(by), text);
    }

    public void fill(WebElement webElement, String text) {
        waitUntilDisplayed(webElement);
        webElement.clear();
        webElement.sendKeys(text);
        sleep(waitAfterFillMs);
    }

    public void selectComboValue(WebElement webElement, String value) {
        new Select(webElement).selectByValue(value);
    }

    public void close() {
        webDriver.close();
    }

    public void takeScreenshot(String description) {
        if (webDriver instanceof TakesScreenshot) {
            try {
                File source = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                String extension = FilenameUtils.getExtension(source.getAbsolutePath());
                String date = DateFormatUtils.format(new Date(), "HH-mm-ss");
                String path = "./target/screenshots/" + description + "_" + date + "." + extension;
                FileUtils.copyFile(source, new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class WebClientBuilder {
        WebDriver webDriver;
        int waitTimeInSeconds = 10;
        Object testInstance;
        String baseUrl;
        boolean followVisually = true;

        public static WebClientBuilder newWebClient() {
            return new WebClientBuilder();
        }

        public WebClientBuilder waitTimeInSeconds(int waitTimeInSeconds) {
            this.waitTimeInSeconds = waitTimeInSeconds;
            return this;
        }

        public WebClientBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public WebClientBuilder onTest(Object testInstance) {
            this.testInstance = testInstance;
            return this;
        }

        public WebClientBuilder followVisually(boolean followVisually) {
            this.followVisually = followVisually;
            return this;
        }

        public WebClientBuilder webDriver(String driver) {
            if ("htmlunit".equalsIgnoreCase(driver)) {
                this.webDriver = new HtmlUnitDriver(true);
            } else if ("firefox".equalsIgnoreCase(driver)) {
                this.webDriver = new FirefoxDriver();
            } else if ("ie".equalsIgnoreCase(driver)) {
                this.webDriver = new InternetExplorerDriver();
            } else if ("chrome".equalsIgnoreCase(driver)) {
                this.webDriver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(driver + " is not a valid web driver");
            }
            return this;
        }

        public WebClient build() {
            checkNotNull(baseUrl);
            checkNotNull(testInstance);
            checkNotNull(webDriver);
            return new WebClient(this);
        }
    }
}