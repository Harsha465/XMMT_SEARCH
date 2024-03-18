package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCases {
    ChromeDriver driver;

    public void selectDate(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        String month_year = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]")).getText();
        WebElement nxt_btn = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
        if(month_year != "April 2024"){
            nxt_btn.click();
        }

        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']/div[text()='April 2024']/../following-sibling::div[@class='DayPicker-Body']/div/div[contains(@aria-label,'2024')]/descendant::p[1]"));
        for(WebElement w : elementList){
            if(w.getText() == "29"){
                w.click();
            }
        }
    }
    public TestCases()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
         options.addArguments("start-maximized");
         options.addArguments("--disable-blink-features=AutomationControlled");
//        options.addArguments("--start-maximized"); // Maximize the browser window
//
//        options.addArguments("--disable-extensions"); // Disable browser extensions
        // options.setExperimentalOption("w3c", false);
        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.makemytrip.com/");


        String url = driver.getCurrentUrl();
        if(url.toLowerCase().contains("makemytrip")){
            System.out.println("The URL of the Make My Trip homepage contains makemytrip");
        }else{
            System.out.println("Url loading failed");
        }

        System.out.println("end Test case: testCase01");
    }
    public void testCase02() throws InterruptedException {

            System.out.println("Start Test case: testCase02");
            driver.get("https://www.makemytrip.com/");
        driver.manage().deleteAllCookies();
        try {
            WebDriverWait wait_popup = new WebDriverWait(driver, Duration.ofSeconds(5000));
            wait_popup.until(ExpectedConditions.visibilityOfElementLocated(By.name("notification-frame-~251439651")));
            driver.switchTo().frame("notification-frame-~251439651");
            WebElement alertHandling = driver.findElement(By.xpath("//i[@class='wewidgeticon we_close']"));
            alertHandling.click();
//            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Modal not found or already closed.");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='chHeaderContainer']//li[@class='menu_Flights']")));

        // Click on Flights tab
        WebElement flightsTab = driver.findElement(By.xpath("//div[@class='chHeaderContainer']//li[@class='menu_Flights']"));
        flightsTab.click();

        // Wait for flight search form to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fromCity")));

        // Select Bangalore as departure location
        WebElement fromCity = driver.findElement(By.xpath("//label[@for='fromCity']"));
        fromCity.click();
        WebElement fromCityInput = driver.findElement(By.xpath("//input[@placeholder='From']"));
        fromCityInput.sendKeys("blr");
        WebElement bangaloreOption = fromCity.findElement(By.xpath("//descendant::span[text()='BLR']"));
        bangaloreOption.click();

        // Select New Delhi as arrival location
        WebElement toCity = driver.findElement(By.xpath("//label[@for='toCity']"));
        toCity.click();
        WebElement toCityInput = driver.findElement(By.id("toCity"));
        toCityInput.sendKeys("del");
        WebElement newDelhiOption = driver.findElement(By.xpath("//div[contains(@class,'react-autosuggest__section-container')]//descendant::span[text()='DEL']"));
        newDelhiOption.click();


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebDriverWait wait_date = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait_date.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-live='polite']/div")));
//        selectDate();

         String month_year = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]")).getText();
            WebElement nxt_btn = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
        System.out.println("Present : "+month_year);
        do {
            // Click on the next button to navigate to the next month
            nxt_btn.click();

            // Get the month and year text
            month_year = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]")).getText();
            System.out.println("Present : " + month_year);
        } while (!month_year.equals("May 2024"));
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,100)");
        Thread.sleep(1000);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']/div[text()='May 2024']/../following-sibling::div[@class='DayPicker-Body']/div/div[contains(@aria-label,'2024')]/descendant::p[1]"));
        for(WebElement w : elementList){
            System.out.println(w.getText());
            if(w.getText().equals("29")){
                w.click();
                break;
            }
        }

//        Thread.sleep(10000);
        // Click on search button
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='fsw ']/descendant::a[text()='Search']"));
        searchButton.click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        String error = driver.findElement(By.xpath("//p[text()='NETWORK PROBLEM']")).getText();
//
//        if(error.equals("NETWORK PROBLEM")){
//            driver.findElement(By.xpath("//button[text()='Refresh']")).click();
//        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        String OK = driver.findElement(By.xpath("//*[contains(text(), 'OKAY, GOT IT!')]")).getText();
        if(OK.equals("OKAY, GOT IT!")){
            WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'OKAY, GOT IT!')]"));
            element.click();
        }
               // Store the flight price (per adult)

        WebElement ListingFlightsdetails = driver.findElement(By.xpath("//div[@class='clusterContent']"));
//                Thread.sleep(2000);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                List<WebElement> PriceLists = ListingFlightsdetails.findElements(By.xpath("//div[@class='textRight flexOne']"));
//                Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        int FlightspricePerAdultcount = PriceLists.size();
                System.out.println("Number of flights prices count:" + FlightspricePerAdultcount);

//                Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        for (WebElement PriceList : PriceLists) {
                    String pricePerAdult = PriceList.getText();
                    System.out.println("Flight Price Per Adult:" + pricePerAdult);
                    System.out.println("---------------");
                }

    }

    public void testCase03() throws InterruptedException {
        try {
            System.out.println("Start Test case: testCase03");
            driver.get("https://www.makemytrip.com/");

            try {
                // Handling notification frame modal
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("notification-frame-~251439651")));
                WebElement closeModalButton = driver.findElement(By.xpath("//i[@class='wewidgeticon we_close']"));
                closeModalButton.click();
                driver.switchTo().defaultContent();
            } catch (TimeoutException e) {
                // If no popup is found, continue with the flow
                System.out.println("No popup found. Continuing with the flow.");
            }
            // Click on Trains menu
            WebElement trainsMenu = driver.findElement(By.className("menu_Trains"));
            trainsMenu.click();

            // Select departure location (Bangalore)
            WebElement selectFrom = driver.findElement(By.xpath("//span[text()='From']"));
            selectFrom.click();
            WebElement departureInput = driver.findElement(By.xpath("//input[@placeholder='From']"));
            departureInput.sendKeys("ypr");

            // Selecting Bangalore from the suggestions
            WebElement bangaloreOption = driver.findElement(By.xpath("//span[text()='YPR']"));
            bangaloreOption.click();

            // Select arrival location (Delhi)
            WebElement searchTo = driver.findElement(By.xpath("//input[@placeholder='To']"));
            searchTo.sendKeys("ndls");

            // Selecting Delhi from the suggestions
            WebElement delhiOption = driver.findElement(By.xpath("//span[text()='NDLS']"));
            delhiOption.click();


            WebDriverWait wait_date = new WebDriverWait(driver,Duration.ofSeconds(3));
            wait_date.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-live='polite']/div")));

            String month_year = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]")).getText();
            WebElement nxt_btn = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
            System.out.println("Present : "+month_year);
            do {
                // Click on the next button to navigate to the next month
                nxt_btn.click();

                // Get the month and year text
                month_year = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']/div[1]")).getText();
                System.out.println("Present : " + month_year);
            } while (!month_year.equals("May 2024"));
            Thread.sleep(1000);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.scrollBy(0,100)");
            Thread.sleep(1000);
            List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']/div[text()='May 2024']/../following-sibling::div[@class='DayPicker-Body']/div/div[contains(@aria-label,'2024')]"));
            for(WebElement w : elementList){
                System.out.println(w.getText());
                if(w.getText().equals("29")){
                    w.click();
                    break;
                }
            }



            // Select seating class (3A)
            WebElement classSelector = driver.findElement(By.xpath("//span[@data-cy='class']"));
            classSelector.click();
            List<WebElement> classOptions = driver.findElements(By.xpath("//ul[@class='travelForPopup']//li"));
            for (WebElement option : classOptions) {
                if ("3A".equals(option.getAttribute("data-cy"))) {
                    option.click();
                    break;
                }
            }

            // Click on search button
            WebElement searchButton = driver.findElement(By.xpath("//a[@class='primaryBtn font24 latoBold widgetSearchBtn']"));
            searchButton.click();

            // Wait for search results
            Thread.sleep(2000);

            // Print ticket prices for 3A class
            try {
                List<WebElement> ticketPricefor3A = driver.findElements(By.xpath("//div[@id='train_options_29-05-2024_0']"));
                for (WebElement tr : ticketPricefor3A) {
                    WebElement trains = tr.findElement(By.xpath(".//descendant::div[@class='rail-class']"));
                    String AC_3 = trains.getText();
                    String price = "";
                    if(AC_3.equals("3A")){
                        price = trains.findElement(By.xpath("./../../div[@class='ticket-price justify-flex-end']")).getText();
                        System.out.println("Ticket Prices for 3A: " + price);
                        System.out.println("------------");
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Failed to load the prices of tickets");
            }

            System.out.println("End Test case: testCase03");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//        public void testCase03 () throws InterruptedException {
//
//            try {
//                System.out.println("Start Test case: testCase03");
//                driver.get("https://www.makemytrip.com/");
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("notification-frame-~251439651")));
//                driver.switchTo().frame("notification-frame-~251439651");
//                try {
//                    WebElement alertHandling = driver.findElement(By.xpath("//span[@data-cy='closeModal']"));
//                    alertHandling.click();
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//                    System.out.println("Modal not found or already closed.");
//                }
//
//                Thread.sleep(1000);
//
//
//                try {
//                    WebElement TrainsMenu = driver.findElement(By.className("menu_Trains"));
//                    TrainsMenu.click();
//                } catch (Exception e) {
//                    System.out.println("Train menu not found");
//                }
//
//                Thread.sleep(2000);
//
//                try {
//                    WebElement SelectFrom = driver.findElement(By.xpath("//span[text()='From']"));
//                    SelectFrom.click();
//                } catch (Exception e) {
//                    System.out.println("Element departure From clicking failure");
//                }
//
//                Thread.sleep(2000);
//
//                WebElement typedeparture = driver.findElement(By.xpath("//input[@placeholder='From']"));
//                typedeparture.sendKeys("ypr");
//
//                Thread.sleep(1000);
//
//                List<WebElement> selectCity = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']//li"));
//                boolean cityFound = false;
//
//                try {
//                    for (WebElement i : selectCity) {
//                        Thread.sleep(1000);
//                        try {
//                            WebElement cityNameElement = i.findElement(By.xpath(".//p[@class='searchedResult font14 darkText']"));
//                            if (cityNameElement.getText().contains("Bangalore")) {
//                                cityNameElement.click();
//                                cityFound = true;
//                                break;
//                            }
//                        } catch (NoSuchElementException e) {
//                            // Handle the exception or log a message if the element is not found
//                            System.out.println("CityNameElement not found in the current item");
//                        }
//                    }
//
//                    if (cityFound) {
//                        System.out.println("Successfully clicked Bangalore");
//                    } else {
//                        System.out.println("Failed to search Bangalore");
//                    }
//                } catch (Exception e) {
//                    // Handle the exception or log a message
//                    e.printStackTrace();
//                }
//
//                Thread.sleep(1000);
//
//                try {
//
//                    WebElement SearchTo = driver.findElement(By.xpath("//input[@placeholder='To']"));
//                    SearchTo.sendKeys("ndls");
//                    Thread.sleep(2000);
//
//
//                    List<WebElement> selectArrival = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']//li"));
//                    cityFound = false;
//
//                    try {
//                        for (WebElement i : selectArrival) {
//                            Thread.sleep(1000);
//                            try {
//                                WebElement cityNameElement = i.findElement(By.xpath(".//p[@class='searchedResult font14 darkText']"));
//                                if (cityNameElement.getText().contains("Delhi")) {
//                                    cityNameElement.click();
//                                    cityFound = true;
//                                    break;
//                                }
//                            } catch (NoSuchElementException e) {
//                                System.out.println("CityNameElement not found in the current item");
//                            }
//                        }
//
//                        if (cityFound) {
//                            System.out.println("Successfully clicked Delhi");
//                        } else {
//                            System.out.println("Failed to search Delhi");
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Failed to search Delhi");
//                    }
//                } catch (Exception e) {
//                    System.out.println("Failed to click Arrival To element");
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                List<WebElement> MonthPicker = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']"));
//
//                long endTime = System.currentTimeMillis() + 60000; // 60 seconds timeout
//
//                while (System.currentTimeMillis() < endTime) {
//                    if (!MonthPicker.isEmpty() && !MonthPicker.get(0).getText().contains("May 2024")) {
//                        Thread.sleep(1000);
//                        System.out.println("Successfully select month & year: May 2024");
//
//                        WebElement Next = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
//                        Next.click();
//
//                        MonthPicker = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']"));
//                    } else {
//                        break;
//                    }
//
//                }
//            } catch (Exception e) {
//                System.err.println("Error occurred in the while loop: " + e.getMessage());
//                // Handle the exception or log a message as needed
//                System.out.println("Failed to select correct month & year");
//            }
//
//            Thread.sleep(1000);
//
//            try {
//                WebElement ondate = driver.findElement(By.xpath("//div[@aria-label='Wed May 08 2024']"));
//                Thread.sleep(1000);
//                ondate.click();
//                System.out.println("Successfully selected the input date: Wed May 08 2024");
//                Thread.sleep(2000);
//
//
//            } catch (Exception e) {
//                System.out.println("Failed to select the input date");
//            }
//
//            Thread.sleep(2000);
//
//            try {
//                WebElement classSelector = driver.findElement(By.xpath("//span[@data-cy='class']"));
//                classSelector.click();
//
//                WebElement classpopup = driver.findElement(By.xpath("//ul[@class='travelForPopup']"));
//
//                List<WebElement> ClassLists = classpopup.findElements(By.tagName("li"));
//                for (WebElement i : ClassLists) {
//                    if ("3A".equals(i.getAttribute("data-cy"))) {
//                        i.click();
//                        System.out.println("successfully selected seating class: 3A");
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("Unable to select seating class");
//            }
//
//            Thread.sleep(2000);
//
//            try {
//                WebElement clcsearch = driver.findElement(By.xpath("//a[@class='primaryBtn font24 latoBold widgetSearchBtn']"));
//                clcsearch.click();
//                System.out.println("Successfully clicked search button");
//
//            } catch (Exception e) {
//                System.out.println("Failed to select the search functionality");
//            }
//
//            Thread.sleep(20000);
//
//            try {
//                WebElement ticketPricefor3A = driver.findElement(By.xpath("//*[@id='train_options_08-05-2024_0']"));
//
//                List<WebElement> allThridAcPrices = ticketPricefor3A.findElements(By.xpath("//*[@id=\"train_options_08-05-2024_0\"]/div[1]/div[2]"));
//                Thread.sleep(3000);
//                for (WebElement i : allThridAcPrices) {
//                    String PricesList = i.getText();
//                    {
//                        System.out.println("Ticket Prices for 3A:" + PricesList);
//                        System.out.println("------------");
//                    }
//                }
//
//
//            } catch (Exception e) {
//                System.out.println("Failed to load the prices of ticket");
//            }
//
//
//            System.out.println("End Test case: testCase03");
//        }


        public void testCase04 () throws InterruptedException {

            try {
                System.out.println("Start Test case: testCase04");
                driver.get("https://www.makemytrip.com/");

                try {
                    // Handling notification frame modal
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name("notification-frame-~251439651")));
                    WebElement closeModalButton = driver.findElement(By.xpath("//i[@class='wewidgeticon we_close']"));
                    closeModalButton.click();
                    driver.switchTo().defaultContent();
                } catch (TimeoutException e) {
                    // If no popup is found, continue with the flow
                    System.out.println("No popup found. Continuing with the flow.");
                }

                try {
                    WebElement BusMenu = driver.findElement(By.className("menu_Buses"));
                    BusMenu.click();
                } catch (Exception e) {
                    System.out.println("Bus menu not found");
                }

                Thread.sleep(2000);

                try {
                    WebElement SelectFrom = driver.findElement(By.xpath("//span[text()='From']"));
                    SelectFrom.click();
                } catch (Exception e) {
                    System.out.println("Element departure From clicking failure");
                }

                Thread.sleep(2000);

                WebElement typedeparture = driver.findElement(By.xpath("//input[@placeholder='From']"));
                typedeparture.sendKeys("bangl");

                Thread.sleep(3000);

                List<WebElement> selectCity = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']//li"));
                boolean cityFound = false;

                try {
                    for (WebElement i : selectCity) {
                        Thread.sleep(3000);
                        try {
                            WebElement cityNameElement = i.findElement(By.xpath(".//p[@class='searchedResult font14 darkText']"));
                            if (cityNameElement.getText().contains("Bangalore")) {
                                cityNameElement.click();
                                cityFound = true;
                                break;
                            }
                        } catch (NoSuchElementException e) {
                            System.out.println("CityNameElement not found in the current item");
                        }
                    }

                    if (cityFound) {
                        System.out.println("Successfully clicked Bangalore");
                    } else {
                        System.out.println("Failed to search Bangalore");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Thread.sleep(1000);

                try {

                    WebElement SearchTo = driver.findElement(By.xpath("//input[@placeholder='To']"));
                    SearchTo.sendKeys("ran");
                    Thread.sleep(2000);


                    List<WebElement> selectArrival = driver.findElements(By.xpath("//ul[@class='react-autosuggest__suggestions-list']//li"));
                    cityFound = false;

                    try {
                        for (WebElement i : selectArrival) {
                            Thread.sleep(1000);
                            try {
                                WebElement cityNameElement = i.findElement(By.xpath(".//p[@class='searchedResult font14 darkText']"));
                                if (cityNameElement.getText().contains("Ranchi")) {
                                    cityNameElement.click();
                                    cityFound = true;
                                    break;
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println("CityNameElement not found in the current item");
                            }
                        }

                        if (cityFound) {
                            System.out.println("Successfully clicked Ranchi");
                        } else {
                            System.out.println("Failed to search Ranchi");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to search Ranchi");
                    }
                } catch (Exception e) {
                    System.out.println("Failed to click Arrival To element");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                List<WebElement> MonthPicker = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']"));

                long endTime = System.currentTimeMillis() + 60000; // 60 seconds timeout

                while (System.currentTimeMillis() < endTime) {
                    if (!MonthPicker.isEmpty() && !MonthPicker.get(0).getText().contains("May 2024")) {
                        Thread.sleep(1000);
                        System.out.println("Successfully select month & year: May 2024 ");

                        WebElement Next = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
                        Next.click();

                        MonthPicker = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']"));
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {
                System.err.println("Error occurred in the while loop: " + e.getMessage());
                System.out.println("Failed to select correct month & year");
            }

            Thread.sleep(1000);

            try {
                WebElement ondate = driver.findElement(By.xpath("//div[@aria-label='Wed May 08 2024']"));
                ondate.click();
                System.out.println("Successfully selected the input date: Wed May 08 2024");
                Thread.sleep(1000);


            } catch (Exception e) {
                System.out.println("Failed to select the input date");
            }

            Thread.sleep(1000);

            try {
                WebElement clcsearch = driver.findElement(By.xpath("//button[text()=\"Search\"]"));
                clcsearch.click();
                System.out.println("Successfully clicked search button");

            } catch (Exception e) {
                System.out.println("Failed to select the search functionality");
            }

            Thread.sleep(20000);

            try {
                WebElement errorTitle = driver.findElement(By.className("error-title"));
                String errorMessage = errorTitle.getText();
                if (errorMessage.contains("No buses found for")) {
                    System.out.println("Sorry, no buses found message: " + errorMessage);
                } else {

                }
            } catch (Exception e) {
            }

            System.out.println("End Test case: testCase04");
        }

}
