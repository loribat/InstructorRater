package com.mycompany.instructorrater;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONTokener;

/**
 * Created by loribatherson on 3/1/15.
 */
public class InstructorJSON {

    public static final String LOGTAG = "InstructorJSON";

    public InstructorJSON() {
        Log.d(LOGTAG, "Inside the constructor");
    }

    // Method to create an ArrayList of Instructor Objects
    // given the String of input from the rateme/list URL
    public ArrayList<Instructor> loadInstructors(String instructorStringFromURL) throws IOException {
        ArrayList<Instructor> instructors = new ArrayList<Instructor>();
        Log.d(LOGTAG, "Inside loadInstructors, before try/catch, string from URL is: " +
                instructorStringFromURL);

        try {
            JSONArray js = (JSONArray) new JSONTokener(instructorStringFromURL).nextValue();
            Log.d(LOGTAG, "loadInstructors: the length of the array is: " + js.length());

            for (int i = 0; i < js.length(); i++) {
                // Add this instructor to the list
                instructors.add(new Instructor(js.getJSONObject(i)));
            } // end for each instructor found in the string

        } catch (Exception e) {
            Log.d(LOGTAG, "loadInstructors: inside the catch");
            return null;
        }

        return instructors;
    } // end method loadInstructors
}
