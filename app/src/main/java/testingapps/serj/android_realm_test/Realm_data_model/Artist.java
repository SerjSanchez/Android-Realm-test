package testingapps.serj.android_realm_test.Realm_data_model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Artist extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private RealmList<Album> albums;

    public RealmList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(RealmList<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
