package testingapps.serj.android_realm_test.Realm_data_model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Album extends RealmObject {

    @PrimaryKey
    private long id;

    @LinkingObjects ("albums")
    private final RealmResults<Artist> albumArtists = null;

    @Required
    private String name;
    private RealmList<Song> songs;

    //Getters and setters

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

    public void addSong(Song song){
        songs.add(song);
    }

    public RealmResults<Artist> getAlbumArtists() {
        return albumArtists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
