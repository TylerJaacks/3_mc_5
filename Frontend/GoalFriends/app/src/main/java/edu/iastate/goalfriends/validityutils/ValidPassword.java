package edu.iastate.goalfriends.validityutils;

import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.Pattern;

/**
 * This ValidPassword class implements the correct Password Pattern, so
 * users will not implement the incorrect Password.
 * @author Kyle Todd
 */
public class ValidPassword implements TextWatcher{
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$"
    );

    private boolean valid = false;
    public boolean isValid() {
        return valid;
    }

    public static boolean isValidPassword(CharSequence password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
    @Override
    final public void afterTextChanged(Editable editableText) {
        valid = isValidPassword(editableText);
    }
    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*No-op*/}
    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {/*No-op*/}
}
