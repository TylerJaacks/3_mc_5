package com.example.goalfriends;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    // Keys for saving values in SharedPreferences.
    static final String KEY_NAME = "key_name";
    static final String KEY_EMAIL = "key_email";
    static final String KEY_PHONE = "key_phone";
    static final String KEY_PASSWORD = "key_password";

    private final SharedPreferences mSharedPreferences;


    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, sharedPreferenceEntry.getName());
        editor.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail());
        editor.putString(KEY_PHONE, sharedPreferenceEntry.getPhone());
        editor.putString(KEY_PASSWORD, sharedPreferenceEntry.getPassword());

        return editor.commit();
    }

    public SharedPreferenceEntry getPersonalInfo() {

        String name = mSharedPreferences.getString(KEY_NAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        String phone = mSharedPreferences.getString(KEY_PHONE, "");
        String password = mSharedPreferences.getString(KEY_PASSWORD, "");

        return new SharedPreferenceEntry(name, email, phone, password);
    }
}
