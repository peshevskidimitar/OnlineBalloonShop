package mk.ukim.finki.wp.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BalloonsPage extends AbstractPage {

    @FindBy(css = "tr[class=balloon]")
    private List<WebElement> balloonRows;

    @FindBy(css = ".delete-balloon")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-balloon")
    private List<WebElement> editButtons;

    @FindBy(css = ".add-new-balloon-btn")
    private List<WebElement> addBalloonButton;

    public BalloonsPage(WebDriver driver) {
        super(driver);
    }

    public static BalloonsPage to(WebDriver driver) {
        get(driver, "/balloons");
        return PageFactory.initElements(driver, BalloonsPage.class);
    }

    public void assertElements(int balloonsNumber, int deleteButtons, int editButtons, int addButtons) {
        Assert.assertEquals("Rows do not match.", balloonsNumber, getBalloonRows().size());
        Assert.assertEquals("Delete do not match.", deleteButtons, getDeleteButtons().size());
        Assert.assertEquals("Edit do not match.", editButtons, getEditButtons().size());
        Assert.assertEquals("Add is visible.", addButtons, getAddBalloonButton().size());
    }

}