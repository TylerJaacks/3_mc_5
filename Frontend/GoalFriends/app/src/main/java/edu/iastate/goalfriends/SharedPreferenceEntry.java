package edu.iastate.goalfriends;


/**
 * This SharedPreferenceEntry class saves the info of the user once
 * they register. The class saves a users name, email, phone number, and
 * password.
 *
 * @author Kyle Todd
 */
public class SharedPreferenceEntry {

    private final String mName;
    private final String mEmail;
    private final String mPhone;
    private final String mPassword;


    /**
     * Default constructor for goal
     */
    public SharedPreferenceEntry(String name,  String email, String phone, String password) {
        mName = name;
        mEmail = email;
        mPhone = phone;
        mPassword = password;
    }

    /**
     * Gets the name of the user
     * @return Name of user as a Sting
     */
    public String getName() {
        return mName;
    }
    /**
     * Gets the email of the user
     * @return Name of email as a Sting
     */
    public String getEmail() {
        return mEmail;
    }
    /**
     * Gets the phone number of the user
     * @return Name of user as a Sting
     */
    public String getPhone() {
        return mPhone;
    }
    /**
     * Gets the password of the user
     * @return Name of password as a Sting
     */
    public String getPassword() {
        return mPassword;
    }
}
