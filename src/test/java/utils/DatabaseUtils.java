package utils;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class DatabaseUtils {
    public static void clearDatabase() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "clear_db.py");

            Path scriptDirectory = Paths.get("C:", "Users", "Pavel", "Desktop", "Music-Store-main");
            processBuilder.directory(scriptDirectory.toFile());

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

