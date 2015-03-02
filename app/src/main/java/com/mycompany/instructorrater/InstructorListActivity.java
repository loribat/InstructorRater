package com.mycompany.instructorrater;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import java.io.IOException;


public class InstructorListActivity extends ActionBarActivity {

    // URLs for getting instructor data
    public static final String LIST_URL = "http://bismarck.sdsu.edu/rateme/list";
    public static final String DETAIL_URL = "http://bismarck.sdsu.edu/rateme/instructor/";

    // Constant string for logging messages to LogCat
    public static final String LOGTAG = "InstructorListActivity";

    // Member variables
    ArrayList<Instructor> mInstructors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);

        // Start a thread to get the Instructor List
        new getInstructorListTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructor_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void detailButtonClicked(View v) {
        // TODO: Figure out which list item is currently selected
        int curListItem = 1;
        // Start a thread to get the Instructor List
        new getInstructorDetailTask().execute(curListItem);
    } // end method detailButtonClicked

    public void rateButtonClicked(View v) {
        // TODO: Figure out which list item is currently selected
        int curListItem = 1;

        // TODO: Start the rate activity!!
    } // end method detailButtonClicked

    // This class creates & executes a thread to get the Instructor List data from the internet
    // and populate the mInstructor array with the results
    private class getInstructorListTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                result = new InstructorDataFetcher().getUrl(LIST_URL);
                // Log.d(LOGTAG, "Fetched contents at URL: " + result);
            } catch (IOException ioe) {
                Log.e(LOGTAG, "Failed to fetch instructor data, exception is: " + ioe);
                return null;
            }
            return result;
        } // end method doInBackground

        @Override
        protected void onPostExecute(String instructorData) {
            Log.d(LOGTAG, "Inside onPostExecute, instructorData is: " + instructorData);

            // We should use JSON to populate the mInstructors array
            TextView tv = (TextView) findViewById(R.id.instructor_list_textView);
            tv.setText(instructorData);

            // Now call the JSON method & see what happens
            InstructorJSON ij = new InstructorJSON();
            try {
                mInstructors = ij.loadInstructors(instructorData);
            } catch (Exception e) {
                Log.e(LOGTAG, "onPostExecute, error loading instructors: " + e);
            }

            Log.d(LOGTAG, "onPostExecute, " + mInstructors.size() + " instructors loaded");
        } // end method onPostExecute
    } // end inner class getInstructorListTask


    // This class creates & executes a thread to get the Instructor Detail data from the internet
    // and populate the correct object in the mInstructor array with the results
    private class getInstructorDetailTask extends AsyncTask<Integer,Void,String> {

        @Override
        protected String doInBackground(Integer... params) {
            String result;
            String fullURL = DETAIL_URL + params[0];
            Log.d(LOGTAG, "getInstructorDetailTask:doInBackground, fullURL is: " + fullURL);
            try {
                result = new InstructorDataFetcher().getUrl(fullURL);
                Log.d(LOGTAG, "Fetched detail contents at URL: " + result);
            } catch (IOException ioe) {
                Log.e(LOGTAG, "Failed to fetch instructor detail, exception is: " + ioe);
                return null;
            }
            return result;
        } // end method doInBackground

        @Override
        protected void onPostExecute(String instructorDetail) {
            Log.d(LOGTAG, "getInstructorDetailTask:onPostExecute, instructorDetail is: "
                    + instructorDetail);

            Instructor instructor = mInstructors.get(0);

            try {
                JSONObject jo = new JSONObject(instructorDetail);
                int instructorId = jo.getInt("id");

                int i;
                for (i=0; i<mInstructors.size(); i++) {
                    instructor = mInstructors.get(i);

                    if (instructor.getId() == instructorId) {
                        instructor.addDetail(jo);
                        break;
                    }
                }
                if (i == mInstructors.size()) {
                    Log.d(LOGTAG, "getInstructorDetailTask:onPostExecute, no matching instructor " +
                            "found :(");
                }
            } catch (Exception e) {
                Log.e(LOGTAG, "getInstructorDetailTask:onPostExecute, error getting instructor " +
                        "detail: " + e);
            }

            // TODO: Pop up a dialog with the details in it
            String detailOutput =
                    "First Name    : " + instructor.getFirstName() + "\n" +
                    "Last Name     : " + instructor.getLastName() + "\n" +
                    "Office        : " + instructor.getOffice() + "\n" +
                    "Phone         : " + instructor.getPhone() + "\n" +
                    "E-mail        : " + instructor.getEmail() + "\n" +
                    "Average Rating: " + instructor.getAverageRating() + "\n" +
                    "Total Ratings : " + instructor.getTotalRatings() + "\n";

            TextView tv = (TextView) findViewById(R.id.instructor_list_textView);
            tv.setText(detailOutput);
        } // end method onPostExecute
    } // end inner class getInstructorDetailTask

} // end class InstructorListActivity
