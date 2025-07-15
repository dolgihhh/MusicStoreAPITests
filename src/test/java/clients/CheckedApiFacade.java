package clients;

import clients.checked.ArtistsApiClientChecked;
import clients.checked.AuthApiClientChecked;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class CheckedApiFacade {
    private final ArtistsApiClientChecked artists;
    private final AuthApiClientChecked auth;

    public CheckedApiFacade() {
        this.artists = new ArtistsApiClientChecked();
        this.auth = new AuthApiClientChecked();
    }
}