package com.mycompany.instructorrater;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by loribatherson on 2/28/15.
 */
public class Instructor {

    // TAG for logging messages to LogCat
    public static final String LOGTAG = "Instructor";

    // Add strings for the JSON keys
    private static final String JSON_ID="id";
    private static final String JSON_FIRSTNAME="firstName";
    private static final String JSON_LASTNAME="lastName";

    // Member Variables
    String mFirstName;
    String mLastName;
    int mId;

    // constructor that takes a JSONObject and turns it into an Instructor
    public Instructor(JSONObject jsonInstructor) throws JSONException {
        mId = jsonInstructor.getInt(JSON_ID);
        mFirstName = jsonInstructor.getString(JSON_FIRSTNAME);
        mLastName = jsonInstructor.getString(JSON_LASTNAME);
        /* Log.d(LOGTAG, "Inside constructor, mId: " + mId + ", mFirstName: " + mFirstName +
                ", mLastName: " + mLastName); */
    } // end constructor from a JSONObject

    // Getters
    public String getFirstName() {
        return mFirstName;
    }
    public String getLastName() {
        return mLastName;
    }
    public int getId() {
        return mId;
    }

    // Setters
    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }
    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }
    public void setId(int mId) {
        this.mId = mId;
    }

} // end com.mycompany.instructorrater.Instructor
