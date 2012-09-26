/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.weld.examples.login.ftest;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Tests login examples in Weld
 *
 * @author maschmid
 * @author kpiwko
 * @author plenyi
 */
@RunWith(Arquillian.class)
@RunAsClient
public class CommonLoginTest {
    protected String MAIN_PAGE = "/home.jsf";

    private static final By LOGGED_IN = By.xpath("//li[contains(text(),'Welcome')]");
    private static final By LOGGED_OUT = By.xpath("//li[contains(text(),'Goodbye')]");
    
    private static final By USERNAME_FIELD = By.id("loginForm:username");
    private static final By PASSWORD_FIELD = By.id("loginForm:password");

    private static final By LOGIN_BUTTON = By.id("loginForm:login");
    private static final By LOGOUT_BUTTON = By.id("loginForm:logout");

    @Drone
    WebDriver driver;
    
    @ArquillianResource
    private URL contextPath;
    
    @Deployment(testable = false)
    public static WebArchive createTestDeployment1() {
        return Deployments.createDeployment();
    }
    
    @Before
    public void openStartUrl() {
        driver.manage().deleteAllCookies();
        driver.navigate().to(contextPath);
    }

    @Test
    public void loginTest() {
        checkElementPresent(driver, USERNAME_FIELD, "username field should be present");
        checkElementNotPresent(driver, LOGOUT_BUTTON, "User should not be logged in!");
        
        driver.findElement(USERNAME_FIELD).clear();
        driver.findElement(USERNAME_FIELD).sendKeys("demo");
        
        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys("demo");
        
        driver.findElement(LOGIN_BUTTON).click();
        
        checkElementPresent(driver, LOGGED_IN, "User should be logged in!");
    }

    @Test
    public void logoutTest() {
        checkElementPresent(driver, USERNAME_FIELD, "username field should be present");
        checkElementNotPresent(driver, LOGOUT_BUTTON, "User should not be logged in!");
        
        driver.findElement(USERNAME_FIELD).clear();
        driver.findElement(USERNAME_FIELD).sendKeys("demo");
        
        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys("demo");
        
        driver.findElement(LOGIN_BUTTON).click();
        
        checkElementPresent(driver, LOGGED_IN, "User should be logged in!");
        driver.findElement(LOGOUT_BUTTON).click();
        
        checkElementPresent(driver, LOGGED_OUT, "User should be logged in!");
    }

    protected void checkElementPresent(WebDriver driver, By by, String errorMsg) {
       try {
           Assert.assertTrue(errorMsg, driver.findElement(by) != null);
       } catch (NoSuchElementException e) {
           Assert.fail(errorMsg);
       }
    }
    
    protected void checkElementNotPresent(WebDriver driver, By by, String errorMsg) {
       try {
           Assert.assertTrue(errorMsg, driver.findElement(by) == null);
       } catch (NoSuchElementException e) {
       }
    }
}
