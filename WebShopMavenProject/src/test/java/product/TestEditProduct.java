package product;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestEditProduct {
	private WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:/WEBPrograms/ChromeDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/WebShop/");

		createNewProduct();
	}

	@After
	public void clean() {
		WebElement link = driver.findElement(By.cssSelector("a[href='Controller?action=products']"));
		link.click();

		WebElement table = driver.findElement(By.xpath("//*[@id='container']/main/table"));
		List<WebElement> rows = table.findElements(By.xpath("//*[@id='container']/main/table/tbody/tr"));

		System.out.println("rows.size(): " + rows.size());
		
		for (int i = rows.size(); i > 1; i--) {
			System.out.println(i);
			driver.findElement(By.xpath("//*[@id='container']/main/table/tbody/tr[" + i + "]/td[4]/a")).click();
			driver.findElement(By.xpath("//*[@id='container']/main/form/input[2]")).click();;
		}
		driver.quit();
	}

	@Test
	public void test_update_everything_of_product_succesfully() {
		String name = "TestingProduct";
		String description = "Description for " + name;
		String price = "444.44";		
		
		WebElement link = driver.findElement(By.cssSelector("a[href='Controller?action=products']"));
		link.click();
		
		WebElement table = driver.findElement(By.xpath("//*[@id='container']/main/table"));
		List<WebElement> rows = table.findElements(By.xpath("//*[@id='container']/main/table/tbody/tr"));
		
		driver.findElement(By.xpath("//*[@id='container']/main/table/tbody/tr[" + (rows.size()) + "]/td[1]/a")).click();
		
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("price")).clear();
		
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("description")).sendKeys(description);
		driver.findElement(By.id("price")).sendKeys(price);
		driver.findElement(By.id("update")).click();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String nameAfterUpdate = driver.findElement(By.xpath("//*[@id='container']/main/table/tbody/tr[" + (rows.size()) + "]/td[1]/a")).getText();
		String descriptionAfterUpdate = driver.findElement(By.xpath("//*[@id='container']/main/table/tbody/tr[" + (rows.size()) + "]/td[2]")).getText();
		String priceAfterUpdate = driver.findElement(By.xpath("//*[@id='container']/main/table/tbody/tr[" + (rows.size()) + "]/td[3]")).getText();
		
		assertEquals(name, nameAfterUpdate);
		assertEquals(description, descriptionAfterUpdate);
		assertEquals(price, priceAfterUpdate);
	}

	// METHODS
	public void createNewProduct() {
		WebElement link = driver.findElement(By.cssSelector("a[href='Controller?action=addProduct']"));
		link.click();

		driver.findElement(By.id("name")).sendKeys("TestingProductBasic");
		driver.findElement(By.id("description")).sendKeys("Description for TestingProductBasic");
		driver.findElement(By.id("price")).sendKeys("555.55");
		driver.findElement(By.id("save")).click();
	}

}