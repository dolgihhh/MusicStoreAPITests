package endpoints;

public class ArtistsEndpoints {
    private static final String BASE = "/artists";

    public static String postArtist() {
        return BASE + "/";
    }

    public static String getArtist(Long id) {
        return BASE + "/" + id;
    }

    public static String getArtists(int skip, int limit) {
        return BASE + "?skip=" + skip + "&limit=" + limit;
    }

    public static String putArtist(Long id) {
        return BASE + "/" + id;
    }

    public static String deleteArtist(Long id) {
        return BASE + "/" + id;
    }

}
