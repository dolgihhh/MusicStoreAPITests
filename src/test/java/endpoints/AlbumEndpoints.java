package endpoints;

import enums.SortBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AlbumEndpoints {
    private static final String BASE = "/albums";

    public static String postAlbum() {
        return BASE + "/";
    }

    public static String getAlbums(Integer skip, Integer limit, String search, String genre,
                                   BigDecimal minPrice, BigDecimal maxPrice, SortBy sortBy) {

        StringBuilder url = new StringBuilder(BASE);
        List<String> params = new ArrayList<>();

        if (skip != null) params.add("skip=" + skip);
        if (limit != null) params.add("limit=" + limit);
        if (search != null) params.add("search=" + search);
        if (genre != null) params.add("genre=" + genre);
        if (minPrice != null) params.add("minPrice=" + minPrice);
        if (maxPrice != null) params.add("maxPrice=" + maxPrice);
        if (sortBy != null) params.add("sortBy=" + sortBy.value());

        if (!params.isEmpty()) {
            url.append("?").append(String.join("&", params));
        }

        return url.toString();
    }

    public static String getAlbum(Long id) {
        return BASE + "/" + id;
    }

    public static String putAlbum(Long id) {
        return BASE + "/" + id;
    }

    public static String deleteAlbum(Long id) {
        return BASE + "/" + id;
    }
}
