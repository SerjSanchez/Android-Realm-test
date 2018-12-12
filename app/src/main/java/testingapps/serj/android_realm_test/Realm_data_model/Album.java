package testingapps.serj.android_realm_test.Realm_data_model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Album extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private RealmList<Song> songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Song> getSongs() {
        return songs;
    }

    public void setSongs(RealmList<Song> songs) {
        this.songs = songs;
    }
}
