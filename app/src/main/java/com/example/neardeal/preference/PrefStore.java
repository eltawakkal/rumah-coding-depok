package com.example.neardeal.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefStore {

    Context context;
    SharedPreferences myPref;
    SharedPreferences.Editor myEditor;

    public PrefStore(Context context) {
        this.context = context;

        myPref = context.getSharedPreferences("StorePref", 0);
    }

    public void setUser(String userName, String pass) {
        myEditor = myPref.edit();

        myEditor.putString("user", userName);
        myEditor.putString("pass", pass);

        myEditor.commit();
    }

    public String getUsername() {
        return myPref.getString("user", null);
    }

    public String getPass() {
        return myPref.getString("pass", null);
    }

    public void deleteUser() {
        myEditor = myPref.edit();

        myEditor.clear();
        myEditor.commit();
    }

}
