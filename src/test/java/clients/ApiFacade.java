package clients;

import clients.checked.AlbumsApiChecked;
import clients.checked.ArtistsApiChecked;
import clients.checked.AuthApiChecked;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ApiFacade {
    private final ArtistsApiChecked artists;
    private final AuthApiChecked auth;
    private final AlbumsApiChecked albums;

    public ApiFacade() {
        this.artists = new ArtistsApiChecked();
        this.auth = new AuthApiChecked();
        this.albums = new AlbumsApiChecked();
    }
}