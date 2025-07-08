package utils;


import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FakerProvider {
    private static final Faker FAKER = new Faker();

    public static Faker get() {
        return FAKER;
    }
}
