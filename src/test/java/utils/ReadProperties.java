package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Vijayakumar_G on 31-07-2017.
 */
public class ReadProperties
{
    private Properties properties = new Properties();

    public ReadProperties()
    {
        loadProperties();
    }

    private void loadProperties()
    {
        try
        {
            String fileName = System.getProperty("propFile", "config.properties");
            String filePath = "src/test/resources/DataFiles/";
            InputStream inputStream = new FileInputStream(filePath+fileName);
            properties.load(inputStream);
            for (Object key: properties.keySet())
            {
                String keyValue = System.getProperty((String) key);
                if(keyValue != null)
                {
                    properties.put(key, keyValue);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String readProperties(String key)
    {
        return properties.getProperty(key);
    }

    public void writeProperties(String key, String value)
    {
         properties.setProperty(key, value);
    }
}
