package clients.checked;

import lombok.Getter;

public class CheckedApiFacade {
    private final ArtistsApiClientChecked artistsClient;
    private final AuthApiClientChecked authClient;

    public CheckedApiFacade() {
        this.artistsClient = new ArtistsApiClientChecked();
        this.authClient = new AuthApiClientChecked();
    }

    public ArtistsApiClientChecked artists() {
        return artistsClient;
    }

    public AuthApiClientChecked auth() {
        return authClient;
    }
}