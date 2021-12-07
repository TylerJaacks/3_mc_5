package edu.iastate.goalfriends.validityutils;

import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.Pattern;

/**
 * This ValidEmail class implements the correct Email Pattern, so
 * users will not implement the incorrect Email.
 * @author Kyle Todd
 */
public class ValidEmail implements TextWatcher{
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean valid = false;
    public boolean isValid() {
        return valid;
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    @Override
    final public void afterTextChanged(Editable editableText) {
        valid = isValidEmail(editableText);
    }
    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*No-op*/}
    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {/*No-op*/}
}

