package edu.iastate.goalfriends.validityutils;

import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.Pattern;

/**
 * This ValidPhoneNumber class implements the correct Phone Number Pattern, so
 * users will not implement the incorrect Phone Number.
 * @author Kyle Todd
 */
public class ValidPhoneNumber implements TextWatcher{
    public static final Pattern PHONE_PATTERN = Pattern.compile(
            "\"^(\\\\d{3}[- .]?){2}\\\\d{4}$\""
    );

    private boolean valid = false;
    public boolean isValid() {
        return valid;
    }

    public static boolean isValidPhone(CharSequence phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    @Override
    final public void afterTextChanged(Editable editableText) {
        valid = isValidPhone(editableText);
    }
    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*No-op*/}
    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {/*No-op*/}
}