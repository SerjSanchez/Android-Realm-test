package testingapps.serj.android_realm_test.Realm_data_model;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Song extends RealmObject {

    @Expose (deserialize = false)
    @PrimaryKey
    private long id;

    @Expose
    @Required
    private String name;

    @LinkingObjects ("songs")
    private final transient RealmResults<Album> songAlbums = null;

    //Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmResults<Album> getSongAlbums() {
        return songAlbums;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
