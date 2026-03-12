# EPAM Selenium Test Automation Framework

**AUT:** https://www.saucedemo.com

## Project Structure

```
src/
├── main/java/com/epam/framework/
│   ├── config/         ConfigProvider           — reads env properties
│   ├── core/           DriverManager            — ThreadLocal WebDriver
│   │                   DriverFactory            — creates browser instances
│   ├── model/          User, Product, Order     — business objects
│   ├── pages/          BasePage, LoginPage,     — Page Object / PageFactory
│   │                   InventoryPage, CartPage,
│   │                   CheckoutPage
│   └── utils/          ScreenshotUtils          — screenshot on failure
│                       HighlightUtils           — BONUS: element highlight
│
├── main/resources/
│   ├── log4j2.xml                               — console + rolling file
│   └── config/
│       ├── qa.properties
│       └── dev.properties
│
└── test/java/com/epam/framework/
    ├── listeners/  TestListener                  — screenshot on failure hook
    └── tests/      BaseTest, LoginTest,
                    CartTest, CheckoutTest,
                    SelenideLoginTest             — BONUS: Selenide
```

## Running Tests

### Default (Chrome, QA, Smoke)
```bash
mvn clean test
```

### Custom browser / env / suite
```bash
mvn clean test -Dbrowser=firefox -Denv=dev -Dsuite=regression
```

### Headless (CI)
```bash
mvn clean test -Dbrowser=chrome-headless -Denv=qa -Dsuite=smoke
```

## CI — Jenkins

Configure the pipeline using the provided `Jenkinsfile`.  
Parameters `BROWSER`, `ENV`, and `SUITE` are selectable from the Jenkins UI.

After each run:
- Test results are shown in **Test Result Trend** graph (`junit`)
- Screenshots are archived as **Build Artifacts** under `target/screenshots/`
- Logs are archived under `target/logs/`
