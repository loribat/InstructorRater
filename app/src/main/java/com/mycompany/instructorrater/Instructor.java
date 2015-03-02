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

    // Add strings for the JSON instructor list keys
    private static final String JSON_ID="id";
    private static final String JSON_FIRSTNAME="firstName";
    private static final String JSON_LASTNAME="lastName";

    // Add strings for the JSON instructor detail keys
    private static final String JSON_OFFICE="office";
    private static final String JSON_PHONE="phone";
    private static final String JSON_EMAIL="email";
    private static final String JSON_RATING="rating";
    private static final String JSON_RATING_AVERAGE="average";
    private static final String JSON_RATING_TOTAL="totalRatings";

    // Member Variables
    String mFirstName;
    String mLastName;
    int mId;
    String mOffice;
    String mPhone;
    String mEmail;
    Double mAverageRating;
    int mTotalRatings;

    // constructor that takes a JSONObject and turns it into an Instructor
    public Instructor(JSONObject jsonInstructor) throws JSONException {
        mId = jsonInstructor.getInt(JSON_ID);
        mFirstName = jsonInstructor.getString(JSON_FIRSTNAME);
        mLastName = jsonInstructor.getString(JSON_LASTNAME);
        /* Log.d(LOGTAG, "Inside constructor, mId: " + mId + ", mFirstName: " + mFirstName +
                ", mLastName: " + mLastName); */
    } // end constructor from a JSONObject



    public void addDetail(JSONObject jsonInstructorDetail) throws JSONException {
        mOffice = jsonInstructorDetail.getString(JSON_OFFICE);
        mPhone = jsonInstructorDetail.getString(JSON_PHONE);
        mEmail = jsonInstructorDetail.getString(JSON_EMAIL);
        JSONObject ratingInfo = jsonInstructorDetail.getJSONObject(JSON_RATING);
        mAverageRating = ratingInfo.getDouble(JSON_RATING_AVERAGE);
        mTotalRatings = ratingInfo.getInt(JSON_RATING_TOTAL);

        Log.d(LOGTAG, "Inside addDetail, mOffice: " + mOffice + ", mPhone: " + mPhone +
                ", mEmail: " + mEmail + ", mAverageRating: " + mAverageRating + ", mTotalRatings: "
                + mTotalRatings);
    } // end method addDetail

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
    public String getOffice() {
        return mOffice;
    }
    public String getPhone() {
        return mPhone;
    }
    public String getEmail() {
        return mEmail;
    }
    public Double getAverageRating() {
        return mAverageRating;
    }
    public int getTotalRatings() {
        return mTotalRatings;
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
