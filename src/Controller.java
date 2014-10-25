import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.*;

public class Controller {
	public static void main(String[] args) throws Exception {
		String email = "";
		String password = "";
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://erepublik.com");
		driver.findElements(By.xpath("//input[@id='citizen_email']")).get(0)
				.sendKeys(email);
		driver.findElements(By.xpath("//input[@id='citizen_password']")).get(0)
				.sendKeys(password);
		driver.findElements(By.xpath("//button[@type='submit']")).get(0)
				.click();

		String MUIds[] = { "1121", "2434", "1517", "1982", "1350", "4446",
				"2550", "3054" };
		String RegimentIds[][] = {
				{ "2216", "3956", "7017", "7673", "17553", "18363" },
				{ "12942" }, { "7465" }, { "11121", "15720" },
				{ "5623", "6362", "7300", "14664", "15158", "17163" },
				{ "18418" }, { "13219" }, { "14963", "17182" } };
		List<String> memberIds = new ArrayList<String>();
		String baseUrl = "http://www.erepublik.com/en/main/group-list/members/";
		for (int i = 0; i < MUIds.length; i++) {
			for (int j = 0; j < RegimentIds[i].length; j++) {
				System.out.println("\n" + MUIds[i] + " " + RegimentIds[i][j]
						+ "\n");
				driver.get(baseUrl + MUIds[i] + "/" + RegimentIds[i][j]);
				Thread.sleep(5000);
				List<WebElement> members = driver
						.findElements(By
								.xpath("//table[@id='member_listing']//tr[@class='members ']"));
				System.out
						.println("No. of ppl in regiment : " + members.size());
				for (int k = 0; k < members.size(); k++) {
					System.out.println(members.get(k).getAttribute("id")
							.toString().substring(7));
					memberIds.add(members.get(k).getAttribute("id").toString()
							.substring(7));
				}

			}
		}
		File file = new File("Erep India users.txt");
		if (!file.exists())
			file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < memberIds.size(); i++)
			bw.write(memberIds.get(i) + '\n');
		bw.close();

		// Private messaging starts here

		memberIds.clear();
		FileReader fr = new FileReader("Erep India users.txt");
		BufferedReader br = new BufferedReader(fr);
		String id;
		while ((id = br.readLine()) != null) {
			memberIds.add(id);
		}
		br.close();
		System.out.println(memberIds.size());
		String pmUrl = "http://www.erepublik.com/en/main/messages-compose/";
		String subject = "INDIAN GOVT: DO NOT MAKE A SINGLE HIT";
		String message = "If you are receiving this message, you are a member of a Military Unit of eIndia. We are aiming to win the Q7 defence shield (10 Billion damage) and 5000 gold. To do that, we need the highest average prestige points. Top 3 fighters from India who are expected to do around 40000 Prestige Points will be fighting in this tournament. If you make a single hit during the tournament, our country will lose this reward.\n\nRemember : DO NOT MAKE A SINGLE HIT DURING THE TOURNAMENT";
		for (int i = 230; i < 231; i++) {
			driver.get(pmUrl + memberIds.get(i));
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@id='citizen_subject']"))
					.sendKeys(subject);
			driver.findElement(By.xpath("//textarea[@id='citizen_message']"))
					.sendKeys(message);
			if (driver.findElements(
					By.xpath("//input[@id='recaptcha_response_field']")).size() > 0)
				Thread.sleep(25000);

			driver.findElement(
					By.xpath("//a[@class='fluid_blue_dark_medium message_submit']"))
					.click();
			Thread.sleep(2000);
			System.out.println("Sent to user " + i);

		}

	}

}
