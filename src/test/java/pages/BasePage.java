package pages;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class BasePage {

	/**
	 * Page
	 */
	
	protected Browser browser;
	protected Page page;

	public Page createPlaywrightPageInstance(String browserTypeAsString) throws UnsupportedEncodingException {
		BrowserType browserType = null;
		switch (browserTypeAsString) {
		case "Firefox":
			browserType = Playwright.create().firefox();
			break;
		case "Chromium":
			browserType = Playwright.create().chromium();
			break;
		case "Webkit":
			browserType = Playwright.create().webkit();
			break;

		}
		if (browserType == null) {
			throw new IllegalArgumentException("Could not launch a browser for type " + browserTypeAsString);
		}
		JsonObject capabilities = new JsonObject();
		JsonObject ltOptions = new JsonObject();

		capabilities.addProperty("browsername", "Chrome"); // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
		capabilities.addProperty("browserVersion", "latest");

		String user = System.getenv("LT_USERNAME");
		String accessKey = System.getenv("LT_ACCESS_KEY");

		ltOptions.addProperty("platform", "Windows 10");
		ltOptions.addProperty("name", "Playwright Test");
		ltOptions.addProperty("build", "Playwright Java Build");
		ltOptions.addProperty("user", "ghoshasish99");
		ltOptions.addProperty("accessKey", "AixUviKbDhFun9y5ieFcUrA8aUd0NHNICnKMZeDa39N1qhruMk");
		capabilities.add("LT:Options", ltOptions);




		String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
		String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;
		//browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false));
		browser = browserType.connect(cdpUrl);
		page = browser.newPage();
		return page;

	}

}