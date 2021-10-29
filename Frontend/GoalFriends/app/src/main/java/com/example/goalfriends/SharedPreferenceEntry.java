package com.example.goalfriends;

public class SharedPreferenceEntry {
    // Name of the user.
    private final String mName;
    // Email address of the user.
    private final String mEmail;
    // Email address of the user.
    private final String mPhone;
    // Email address of the user.
    private final String mPassword;

    public SharedPreferenceEntry(String name,  String email, String phone, String password) {
        mName = name;
        mEmail = email;
        mPhone = phone;
        mPassword = password;
    }
    public String getName() {
        return mName;
    }
    public String getEmail() {
        return mEmail;
    }
    public String getPhone() {
        return mPhone;
    }
    public String getPassword() {
        return mPassword;
    }
}
