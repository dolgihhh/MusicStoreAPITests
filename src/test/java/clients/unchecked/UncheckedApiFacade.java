package clients.unchecked;

public class UncheckedApiFacade {
    private final AuthApiClientUnchecked authApiClientUnchecked;
    private final ArtistsApiClientUnchecked artistsApiClientUnchecked;

    public UncheckedApiFacade() {
        this.authApiClientUnchecked = new AuthApiClientUnchecked();
        this.artistsApiClientUnchecked = new ArtistsApiClientUnchecked();
    }

    public AuthApiClientUnchecked auth() {
        return authApiClientUnchecked;
    }

    public ArtistsApiClientUnchecked artists() {
        return artistsApiClientUnchecked;
    }
}
