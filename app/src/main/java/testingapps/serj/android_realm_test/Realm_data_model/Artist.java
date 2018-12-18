package testingapps.serj.android_realm_test.Realm_data_model;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.Required;

public class Artist extends RealmObject {

    @Expose (deserialize = false)
    @PrimaryKey
    private long id;

    @Expose
    @Required
    private String name;

    @Expose
    private RealmList<Album> albums;

    //Getters and setters

    public RealmList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(RealmList<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album){
        albums.add(album);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
