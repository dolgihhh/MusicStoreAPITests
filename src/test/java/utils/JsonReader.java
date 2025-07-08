package utils;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;

@UtilityClass
public class JsonReader {
    public final String RESOURCES_PATH = "src/test/resources/";
    private final Gson GSON = new Gson();

    public <T> T deserializeJson(String filePath, Class<T> tClass) {
        try {
            return GSON.fromJson(new FileReader(filePath), tClass);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
