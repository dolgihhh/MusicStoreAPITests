package utils;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class DatabaseUtils {
    public static void clearDatabase() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "clear_db.py");
            processBuilder.directory(new File("C:\\Users\\Pavel\\Desktop\\Music-Store-main"));//TODO сделать через Path
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Не удалось очистить БД, код выхода: " + exitCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при очистке БД", e);
        }
    }
}
