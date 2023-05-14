package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Manufacturer;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.service.BalloonService;
import mk.ukim.finki.wp.lab.service.ManufacturerService;
import mk.ukim.finki.wp.lab.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    @Autowired
    UserService userService;
    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    BalloonService balloonService;

    private HtmlUnitDriver driver;
    private static Manufacturer manufacturer1;
    private static Manufacturer manufacturer2;
    private static User regularUser;
    private static User adminUser;
    private static boolean dataInitialized = false;

    private static String user = "user";
    private static String admin = "admin";

    @BeforeEach
    public void setUp() {
        this.driver = new HtmlUnitDriver(false);
        initData();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null)
            driver.close();
    }

    private void initData() {
        if (!dataInitialized) {
            manufacturer1 = manufacturerService.save("Manufacturer 1", "Address 1", "Country 1");
            manufacturer2 = manufacturerService.save("Manufacturer 2", "Address 2", "Country 2");

            regularUser = userService.register(user, user, user, user, user, LocalDate.now(), Role.ROLE_USER);
            adminUser = userService.register(admin, admin, admin, admin, admin, LocalDate.now(), Role.ROLE_ADMIN);

            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() {
        BalloonsPage balloonsPage = BalloonsPage.to(driver);
        balloonsPage.assertElements(0, 0, 0, 0);

        LoginPage loginPage = LoginPage.openLogin(driver);

        balloonsPage = LoginPage.doLogin(driver, loginPage, regularUser.getUsername(), user);
        balloonsPage.assertElements(0, 0, 0, 0);

        loginPage = LoginPage.doLogout(driver);

        balloonsPage = LoginPage.doLogin(driver, loginPage, adminUser.getUsername(), admin);
        balloonsPage.assertElements(0, 0, 0, 1);

        balloonsPage = AddOrEditBalloon.addBalloon(driver, "Balloon 1", "This is Balloon 1.", manufacturer1.getName());
        balloonsPage.assertElements(1, 1, 1, 1);

        balloonsPage = AddOrEditBalloon.addBalloon(driver, "Balloon 2", "This is Balloon 2.", manufacturer1.getName());
        balloonsPage.assertElements(2, 2, 2, 1);

        loginPage = LoginPage.doLogout(driver);

        balloonsPage = LoginPage.doLogin(driver, loginPage, regularUser.getUsername(), user);
        balloonsPage.assertElements(2, 0, 0, 0);
    }

}
