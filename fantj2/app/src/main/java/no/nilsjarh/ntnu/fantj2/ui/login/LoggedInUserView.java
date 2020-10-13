package no.nilsjarh.ntnu.fantj2.ui.login;

import android.widget.Button;

import no.nilsjarh.ntnu.fantj2.R;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String displayMail;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String displayMail) {
        this.displayName = displayName;
        this.displayMail = displayMail;
    }


    public String getDisplayMail() {
        return displayMail;
    }

    String getDisplayName() {
        if (this.displayName.isEmpty()) return "user";
        return displayName;
    }
}