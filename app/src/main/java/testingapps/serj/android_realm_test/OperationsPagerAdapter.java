package testingapps.serj.android_realm_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OperationsPagerAdapter extends FragmentStatePagerAdapter {

    public OperationsPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new AddEntryFragment();
            case 1: return new ViewEntryFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Tab 1";
        case 1: return "Tab 2";
        default: return null;
    }
    }

}
