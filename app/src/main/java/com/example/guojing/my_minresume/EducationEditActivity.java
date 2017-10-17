package com.example.guojing.my_minresume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guojing.my_minresume.model.Education;
import com.example.guojing.my_minresume.util.DateUtil;

import java.util.Arrays;

/**
 * Created by AmazingLu on 8/24/17.
 */

public class EducationEditActivity extends AppCompatActivity {
    public static final String KEY_EDUCATON = "education";
    public static final String KEY_EDUCATON_ID = "education_id";
    private Education education;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);
        // every time add a new activity, also add it into the manifests

        // this function will display the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * get the result past from other activity (main activity here)
         * different from the get result in main activity
         * but still get the result from intent
         * education edit step 2:
         * */
        education = getIntent().getParcelableExtra(KEY_EDUCATON);
        // edit education
        if (education != null) {
            setUpUI();
        } else { // add education
            /**
             * when we at add education, the delete button should not appear
             * education delete button step 1
             * */
            findViewById(R.id.education_edit_delete_button).setVisibility(View.GONE);
        }
    }

    private void setUpUI() {
        ((EditText) findViewById(R.id.education_edit_school)).setText(education.school);
        ((EditText) findViewById(R.id.education_edit_startDate))
                .setText(DateUtil.dateToString(education.startDate));
        ((EditText) findViewById(R.id.education_edit_endDate))
                .setText(DateUtil.dateToString(education.endDate));
        ((EditText) findViewById(R.id.education_edit_courses))
                .setText(TextUtils.join("\n", education.courseList));
        /**
         * if we are at edit education, the delete button should appear and have a listener
         * education delete button step 2
         * */
        findViewById(R.id.education_edit_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_EDUCATON_ID, education.id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * the call back function of the items on action bar
     * manege all the buttons on menus bar
     * for education edit and add, the function will call after we add the content into the  view
     * and click save or back button
     * education edit step 3
     * education add step 2
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: // click the back button
                finish();
                return true;
            case R.id.ic_save: // click the save button
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * education add step 3
     * */
    private void saveAndExit() {
        if (education == null) {
            education = new Education();
        }
        /**
         * collect the data use experience
         * */
        education.school =
                ((EditText)findViewById(R.id.education_edit_school)).getText().toString();
        education.startDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.education_edit_startDate)).getText().toString());
        education.endDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.education_edit_endDate)).getText().toString());
        education.courseList = Arrays.asList(TextUtils.split
                (((EditText)findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        /**
         * use intent to package data and then transit it between activities\
         * we can use this method only when education class has implement Parceble
         * */
        Intent intent = new Intent();
        /**
         * if we want to transfer objects between activities
         * the class of the objects must implement Parcelable and over ride the certain method好的
         * */
        intent.putExtra(KEY_EDUCATON, education); // put date into intent
        setResult(Activity.RESULT_OK, intent); // put intent into activity result
        finish(); // finish the current activity
    }

    // ""inflate" or create the button on the menus"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
}
