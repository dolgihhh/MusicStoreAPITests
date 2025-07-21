package endpoints;

import java.util.ArrayList;
import java.util.List;

public class ArtistEndpoints {
    private static final String BASE = "/artists";

    public static String postArtist() {
        return BASE + "/";
    }

    public static String getArtist(Long id) {
        return BASE + "/" + id;
    }

    public static String getArtists(Integer skip, Integer limit) {
        StringBuilder url = new StringBuilder(BASE);
        List<String> params = new ArrayList<>();

        if (skip != null) params.add("skip=" + skip);
        if (limit != null) params.add("limit=" + limit);

        if (!params.isEmpty()) {
            url.append("?").append(String.join("&", params));
        }

        return url.toString();
    }


    public static String putArtist(Long id) {
        return BASE + "/" + id;
    }

    public static String deleteArtist(Long id) {
        return BASE + "/" + id;
    }

}
