package testingapps.serj.android_realm_test;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import testingapps.serj.android_realm_test.Realm_data_model.Album;
import testingapps.serj.android_realm_test.Realm_data_model.AllData;
import testingapps.serj.android_realm_test.Realm_data_model.Artist;
import testingapps.serj.android_realm_test.Realm_data_model.Song;


public class ViewEntryFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;

    TextView artistsTV;
    TextView albumsTV;
    TextView songsTV;

    TextView jsonExampleTV;

    RealmResults<Artist> artists;
    private RealmList<Album> albums;
    private RealmList<Song> songs;

    String TAG = ViewEntryFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_entry, container, false);

        try(Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction((Realm realm1) -> {
                try {
                    //Load this data when creating the database

                    //reset to test if changes are working correctly
                    //realm1.deleteAll();
                    Gson gson = new GsonBuilder().create();
                    AllData data = gson.fromJson("{\"artists\":[{\"albums\":[{\"id\":1,\"name\":\"black album\",\"songs\":[{\"id\":1,\"name\":\"unforgiven\"},{\"id\":2,\"name\":\"nothing else matters\"}]}],\"name\":\"metallica\"},{\"albums\":[{\"id\":2,\"name\":\"white album\",\"songs\":[{\"id\":3,\"name\":\"blackbird\"}]},{\"id\":3,\"name\":\"let it be\",\"songs\":[{\"id\":4,\"name\":\"octopuss garden\"}]}],\"id\":2,\"name\":\"the beatles\"}],\"id\":1}",AllData.class);

                    realm1.copyToRealmOrUpdate(data);

                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }

            });
        }

        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {

                loadData();
                /* CODE */

                //Call this line to stop it from refreshing
                mSwipeRefreshLayout.setRefreshing(false);
            }, 500);//Delays the execution of the code in RUN for 2500 ms
        });

        initUI(view);
        loadData();

        return view;
    }

    private void loadData() {

        try(Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction((Realm realm1) -> {
                try {

                    artists = realm1.where(Artist.class).findAll();

                    final AllData data = realm1.where(AllData.class).equalTo("id",1).findFirst();



                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {

                        artistsTV.setText(artists.get(0).getName());

                        albums = artists.get(0).getAlbums();

                        String albumsStr = "";
                        String songsStr = "";

                        for (Album album : albums ){

                            albumsStr = albumsStr.concat(album.getName() +System.getProperty("line.separator"));
                            songs = album.getSongs();

                            for(Song song : songs){
                                songsStr = songsStr.concat(song.getName() +System.getProperty("line.separator"));
                            }
                            songsStr = songsStr.concat(System.getProperty("line.separator"));

                        }

                        albumsTV.setText(albumsStr);
                        songsTV.setText(songsStr);

                        //Initialize GSON parser
                        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return false;
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        }).excludeFieldsWithoutExposeAnnotation().create();

                        if(data !=null && data.getArtists().size()>0) {
                            //Gson to string
                            //NEEDED TO COPY FROM REALM
                            String jsonInString = gson.toJson(realm1.copyFromRealm(data));

                            //Show Json string
                           jsonExampleTV.setText(jsonInString);
                            //jsonExampleTV.setText(data.getArtists().get(0).getName());
                        }

                    });
                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }

            });
        }


    }

    private void initUI(View view){

        artistsTV = view.findViewById(R.id.artist);
        albumsTV = view.findViewById(R.id.albums);
        songsTV = view.findViewById(R.id.songs);
        jsonExampleTV = view.findViewById(R.id.json_example);

    }

}
