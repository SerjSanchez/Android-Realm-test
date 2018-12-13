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

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import testingapps.serj.android_realm_test.Realm_data_model.Album;
import testingapps.serj.android_realm_test.Realm_data_model.Artist;
import testingapps.serj.android_realm_test.Realm_data_model.Song;


public class ViewEntryFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;

    TextView artistsTV;
    TextView albumsTV;
    TextView songsTV;

    RealmResults<Artist> artists;
    private RealmList<Album> albums;
    private RealmList<Song> songs;

    String TAG = ViewEntryFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_entry, container, false);


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
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    try {
                        artists = realm.where(Artist.class).equalTo("id", 1).findAll();

                        getActivity().runOnUiThread(() -> {

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

                        });
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                    }

                }
            });
        }


    }

    private void initUI(View view){

        artistsTV = view.findViewById(R.id.artist);
        albumsTV = view.findViewById(R.id.albums);
        songsTV = view.findViewById(R.id.songs);

    }

}
