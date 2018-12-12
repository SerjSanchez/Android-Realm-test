package testingapps.serj.android_realm_test;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        RealmConfiguration config = new RealmConfiguration.Builder().build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        OperationsPagerAdapter myPagerAdapter = new OperationsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {

        realm.close();
        super.onDestroy();
    }
}
