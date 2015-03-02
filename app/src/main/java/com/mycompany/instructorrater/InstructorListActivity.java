package com.mycompany.instructorrater;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

        // Now go get the instructor detail for this list item

    }


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

} // end class InstructorListActivity
