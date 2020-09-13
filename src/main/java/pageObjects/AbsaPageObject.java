package pageObjects;

import org.openqa.selenium.By;

public class AbsaPageObject {
    public static String url() {
        return "https://www.absa.co.za/personal/ ";
    }

    public static By searchIcon() {
        return By.xpath("//div[@class= 'richText-content']/p[contains(text(), 'Search')]");
    }

    public static By txtSearch() {
        return By.xpath("//input[@name = 'q']");
    }

    public static By btnSearch() {
        return By.xpath("//button[contains(text(), 'Search')]");
    }

    public static By chkProduct() {
        return By.xpath("//label[contains(text(), 'Products')]");
    }

    public static By lnk_IslamicPremiumBanking() {
        return By.xpath("//div[@class = 'globalSearchResults-desc']//a[contains(text(), 'Islamic Premium')]");
    }

    public static String lbl_IslamicBankingTitle() {
        return "//li[contains(text(), ' Islamic Premium Banking')]";
    }

    public static By div_element() {
        return By.xpath("//div[@data-id ='link_content top_bank']");
    }

    public static By btnApplyNow() {
        return By.xpath("//a[contains(text(), 'Apply now')]");
    }

    public static By txtIdNumber() {
        return By.xpath("//input[@id = 'id_identityNo']");
    }

    public static By txtFirstName() {
        return By.xpath("//input[@id = 'id_firstName']");
    }

    public static By txtSurname() {
        return By.xpath("//input[@id = 'id_lastName']");
    }

    public static By txtEmail() {
        return By.xpath("//input[@id = 'id_emailAddress']");
    }

    public static By txtCellNumber() {
        return By.xpath("//input[@id = 'id_cellphoneNo']");
    }

    public static By btnContinue() {
        return By.xpath("//span[@class = 'ng-star-inserted']");
    }

    public static By lbl_ErrorMessage() {
        return By.xpath("//div[contains(text(), 'ID number invalid')]");
    }

}
