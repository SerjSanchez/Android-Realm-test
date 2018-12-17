package testingapps.serj.android_realm_test.Realm_data_model;

import com.google.gson.annotations.Expose;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AllData extends RealmObject {

    //This class containing all the rest of classes is only necessary for the parser, since it only
        // write/read one class.
        // Thus, by writing/reading a class that contains everything, reads everything.
    @Expose
    @PrimaryKey
    private long  id;

    @Expose
    private RealmList<Artist> artists;

    public void addArtist(Artist artist) {
        artists.add(artist);
    }

    public RealmList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(RealmList<Artist> artists) {
        this.artists = artists;
    }

    public long getId() {
        return id;
    }
}
