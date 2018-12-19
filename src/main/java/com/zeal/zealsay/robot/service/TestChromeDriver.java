package com.zeal.zealsay.robot.service;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


/**
 * chromeDriver是谷歌的浏览器驱动，用来适配Selenium,有图形页面存在，在调试爬虫下载运行的功能的时候会相对方便
 * @author zhuangj
 * @date 2017/11/14
 */
public class TestChromeDriver {

    private static ChromeDriverService service;

    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe")) .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        WebDriver driver = TestChromeDriver.getChromeDriver();
        // 让浏览器访问 企查查
        driver.get("http://www.qichacha.com/");
        // 用下面代码也可以实现
        //driver.navigate().to("http://www.baidu.com");
        // 获取 网页的 title
        System.out.println(" Page title is: " +driver.getTitle());
        // 通过 id 找到 input 的 DOM
        WebElement input =driver.findElement(By.id("searchkey"));
        WebElement search_btn =driver.findElement(By.id("V3_Search_bt"));
        // 输入关键字
        input.sendKeys("阿里巴巴");
        System.out.println("*****************"+input.getText());
        // 提交 input 所在的 form
//        element.submit();
        //点击搜索按钮
        Thread.sleep(3000);
        search_btn.click();
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        //WebDriverWait wait=new WebDriverWait(driver,10,10);

        Function<WebDriver, Boolean> waitFn = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    driver.getTitle().contains("【阿里巴巴】");
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
        //wait.until(waitFn);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        wait.until(new ExpectedCondition() {
//            @Override
//            public Object apply(Object input) {
//                return ((WebDriver)input).getTitle().toLowerCase().startsWith("东鹏瓷砖");
//            }
//        });
//        wait.until(ExpectedConditions.titleContains(""));

        List<WebElement>elements = driver.findElements(By.className("ma_h1"));
        for (WebElement element:elements){
            System.out.println("*******公司名称:"+element.getText());
        }
        Thread.sleep(4000);
        elements.get(0).click();
        //会有新窗口弹出，获取所有窗口句柄
        Set<String> all_handles = driver.getWindowHandles();
        //循环判断，把当前句柄从所有句柄中移除，剩下的就是你想要的新窗口
        Iterator<String> it = all_handles.iterator();
        //获得当前窗口
        String current_handle = driver.getWindowHandle();
        //申明新窗口
        WebDriver newWindow=null;
        String handle = null;
        while(it.hasNext()){
            handle = it.next();
            if(current_handle==handle){
                continue;
            }
//跳入新窗口,并获得新窗口的driver - newWindow
            newWindow = driver.switchTo().window(handle);
        }
        newWindow.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement>mainInfos= newWindow.findElements(By.className("ntable"));
        //解析抓取到的信息
        String name = newWindow.findElement(By.className("bname")).getText();
        System.out.println("*******法人代表:"+name);
        //解析主要信息
        Map<String,String> dataMap = new HashMap();
        WebElement mianCompanyInfo = mainInfos.get(1);
        //解析表格里面数据
        List<WebElement> infolist = mianCompanyInfo.findElements(By.tagName("tr"));
        for (WebElement info:infolist){
            List<WebElement> infos = info.findElements(By.tagName("td"));
            if (infos.size()>=2) {
                dataMap.put(infos.get(0).getText(), infos.get(1).getText());
            }
            if (infos.size()>=4) {
                dataMap.put(infos.get(2).getText(), infos.get(3).getText());
            }
        }
        // 显示搜索结果页面的 title
        System.out.println(" Page title is: " +newWindow.getTitle());
        System.out.println(dataMap);
        // 关闭浏览器
        //driver.quit();

        // 关闭 ChromeDriver 接口
        service.stop();
    }



}