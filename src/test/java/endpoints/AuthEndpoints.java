package endpoints;

public class AuthEndpoints {
    private static final String BASE = "/auth";

    public static String register() {
        return BASE + "/register";
    }

    public static String login() {
        return BASE + "/token";
    }

    public static String updateUserRights(String username) {
        return BASE + "/users/" + username + "/rights";
    }
}
