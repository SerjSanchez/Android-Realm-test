package testingapps.serj.android_realm_test.Realm_data_model;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Song extends RealmList {

    @PrimaryKey
    private long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}