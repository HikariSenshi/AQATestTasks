package google;

import org.testng.annotations.DataProvider;

public class GoogleTest {

    //this method will provide browser data for Selenide-framework
    @DataProvider
    public static Object[] browserProvider(){
        return new Object[]{
                "chrome",
                 "opera",
                "firefox"
        };
    }



    public void setUp(){


    }
}
