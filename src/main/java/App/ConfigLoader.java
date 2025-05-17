package App;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static String getKey(String Something){
        Properties properties = new Properties();
        InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties");

        try{
            properties.load(inputStream);
            return properties.getProperty(Something);
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
