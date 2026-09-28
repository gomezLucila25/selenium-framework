# Selenium Framework — Base (Page Object · ThreadLocal driver · CI-ready)

> **Module 1 of my EPAM Test Automation track.** Each module added one layer to the same Selenium framework.
> The complete, final version lives in **[selenium-framework-patterns](https://github.com/gomezLucila25/selenium-framework-patterns)**.

## What this module added

- Base framework for [SauceDemo](https://www.saucedemo.com): **Page Object + PageFactory**, business objects (`User`, `Product`, `Order`).
- **ThreadLocal WebDriver** and a driver factory for Chrome, Firefox, Edge and headless.
- Environment config (`qa` / `dev`) and **smoke / regression** TestNG suites.
- Screenshot on failure through a TestNG listener, and Log4j2 logging.
- A **Jenkinsfile** with parameters for browser, env and suite, which publishes JUnit results and archives screenshots.
- A Selenide version of the login tests as a bonus.
- Kept working against a React SPA and Chrome 145 (JS-click workaround for checkout navigation).

## Stack

Java 17 · Selenium 4 · TestNG · Selenide · Log4j2 · Maven · Jenkins

## Run

```bash
mvn clean test
```
