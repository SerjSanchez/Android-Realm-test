package testingapps.serj.android_realm_test;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import testingapps.serj.android_realm_test.Realm_data_model.Album;
import testingapps.serj.android_realm_test.Realm_data_model.AllData;
import testingapps.serj.android_realm_test.Realm_data_model.Artist;
import testingapps.serj.android_realm_test.Realm_data_model.Song;


public class AddEntryFragment extends Fragment {

    EditText artist;
    EditText album;
    EditText song;
    Button add;

    String artistStr;
    String albumStr;
    String songStr;

    private String TAG = "ADD_ENTRY_FRAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_entry, container, false);

        //Find EditTexts
        artist = view.findViewById(R.id.artist);
        album = view.findViewById(R.id.album);
        song = view.findViewById(R.id.song);

        //Add text listeners to EditTexts
        artist.addTextChangedListener(textWatcherArtist);
        album.addTextChangedListener(textWatcherAlbum);
        song.addTextChangedListener(textWatcherSong);

        //find the Add button
        add = view.findViewById(R.id.add);

        //Add click listener to the button
        add.setOnClickListener(addEntryListener);

        return view;
    }

    /**
     * Text listeners
     */
    TextWatcher textWatcherArtist = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try {
                    artistStr =artist.getText().toString();

            }catch(Exception e){
                Log.e(TAG, "Artist cannot be read");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher textWatcherAlbum = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                albumStr =album.getText().toString();

            }catch(Exception e){
                Log.e(TAG, "Album cannot be read");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher textWatcherSong = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                songStr =song.getText().toString();

            }catch(Exception e){
                Log.e(TAG, "Song cannot be read");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * Button listeners
     */

    View.OnClickListener addEntryListener = (View view) -> {

        try(Realm realm = Realm.getDefaultInstance()){


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    //To set ID it's necessary to enter an unused id, so let's take the maxId
                    // for each type and add 1
                    Number maxIdNumArtist = realm.where(Artist.class).max("id");
                    Number maxIdNumAlbum = realm.where(Album.class).max("id");
                    Number maxIdNumSong = realm.where(Song.class).max("id");

                    AllData database = realm.where(AllData.class).findFirst();

                    if(database == null){
                        //if there is no "allData", create it otherwise, just save everything under that object.
                        //there should only exist one instance of alldata in the database since its only
                        //purpose is to contain the full database
                        database = realm.createObject(AllData.class,1);
                    }

                    //Find all artists to check if it's already in the database
                    Artist artist = realm.where(Artist.class).contains("name",artistStr).findFirst();

                    if(artist == null) {
                        //if the artist doesnt exist, create new one

                        //Create objects like this because ID is a primary key. Primary keays MUST be
                        // added at the time of object creation
                        artist = realm.createObject(Artist.class, (maxIdNumArtist == null) ? 1 : maxIdNumArtist.intValue() + 1);
                        Album album = realm.createObject(Album.class, (maxIdNumAlbum == null) ? 1 : maxIdNumAlbum.intValue() + 1);
                        Song song = realm.createObject(Song.class, (maxIdNumSong == null) ? 1 : maxIdNumSong.intValue() + 1);
                        //Add parameters to song
                        song.setName(songStr);

                        //Add parameters to album ("song" is a parameter from Album)
                        album.setName(albumStr);
                        album.addSong(song);

                        //Add parameters to artist ("album" is a parameter from Artist)
                        artist.setName(artistStr);
                        artist.addAlbum(album);

                        //since the artist is new, add it to the database
                        database.addArtist(artist);

                    } else {
                        // if the artist exists, add the parameters

                        //Find if there is a album called *albumStr* with owner *artistStr*
                        Album artistAlbum = realm.where(Album.class).equalTo("name", albumStr).equalTo("albumArtists.id", artist.getId()).findFirst();

                        if(artistAlbum == null){
                            //if there is no album
                            Album album = realm.createObject(Album.class, (maxIdNumAlbum == null) ? 1 : maxIdNumAlbum.intValue() + 1);
                            Song song = realm.createObject(Song.class, (maxIdNumSong == null) ? 1 : maxIdNumSong.intValue() + 1);
                            //Add parameters to song
                            song.setName(songStr);

                            //Add parameters to album ("song" is a parameter from Album)
                            album.setName(albumStr);
                            album.addSong(song);
                            artist.addAlbum(album);
                        }else{
                            //if there is album
                            Song song = realm.createObject(Song.class, (maxIdNumSong == null) ? 1 : maxIdNumSong.intValue() + 1);
                            song.setName(songStr);
                            artistAlbum.addSong(song);
                        }

                    }
                }
            });


        }

    };

}
