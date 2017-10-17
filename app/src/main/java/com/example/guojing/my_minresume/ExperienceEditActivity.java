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
import android.widget.EditText;

import com.example.guojing.my_minresume.model.Education;
import com.example.guojing.my_minresume.model.Experience;
import com.example.guojing.my_minresume.util.DateUtil;

import java.util.Arrays;

/**
 * Created by AmazingLu on 8/24/17.
 */

public class ExperienceEditActivity extends AppCompatActivity {
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";
    private Experience experience;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_edit);
        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * get the package sent from other activity
         * experience edit step 2:
         * */
        experience = getIntent().getParcelableExtra(KEY_EXPERIENCE);
        if (experience != null) {
            setUpUI();
        } else {
            findViewById(R.id.experience_edit_delete_button).setVisibility(View.GONE);
        }
    }

    private void setUpUI() {
        ((EditText) findViewById(R.id.experience_edit_company)).setText(experience.company);
        ((EditText) findViewById(R.id.experience_edit_startDate))
                .setText(DateUtil.dateToString(experience.startDate));
        ((EditText) findViewById(R.id.experience_edit_endDate))
                .setText(DateUtil.dateToString(experience.endDate));
        ((EditText) findViewById(R.id.experience_edit_details))
                .setText(TextUtils.join("\n", experience.workList));
        findViewById(R.id.experience_edit_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_EXPERIENCE_ID, experience.id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        if (experience == null) {
            experience = new Experience();
        }
        /**
         * collect the data use experience
         * */
        experience.company =
                ((EditText)findViewById(R.id.experience_edit_company)).getText().toString();
        experience.startDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.experience_edit_startDate)).getText().toString());
        experience.endDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.experience_edit_endDate)).getText().toString());
        experience.workList = Arrays.asList(TextUtils.split
                (((EditText)findViewById(R.id.experience_edit_details)).getText().toString(), "\n"));
        /**
         * use intent to package data and then transit it between activities\
         * we can use this method only when education class has implement Parceble
         * */
        Intent intent = new Intent();
        intent.putExtra(KEY_EXPERIENCE, experience); // put date into intent
        setResult(Activity.RESULT_OK, intent); // put intent into activity result
        finish(); // finish the current activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
}
