package clients;

import clients.unchecked.ArtistsApiClientUnchecked;
import clients.unchecked.AuthApiClientUnchecked;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class UncheckedApiFacade {
    private final AuthApiClientUnchecked auth;
    private final ArtistsApiClientUnchecked artists;

    public UncheckedApiFacade() {
        this.auth = new AuthApiClientUnchecked();
        this.artists = new ArtistsApiClientUnchecked();
    }
}
