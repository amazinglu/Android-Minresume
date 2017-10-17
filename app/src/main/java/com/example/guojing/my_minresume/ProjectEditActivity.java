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

import com.example.guojing.my_minresume.model.Project;
import com.example.guojing.my_minresume.util.DateUtil;

import java.util.Arrays;

/**
 * Created by AmazingLu on 8/25/17.
 */

public class ProjectEditActivity extends AppCompatActivity {
    public static final String KEY_PROJECT = "project";
    public static final String KEY_PROJECT_ID = "project_id";
    private Project project;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        // back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * get the package sent from other activity
         * project edit step 2
         * */
        project = getIntent().getParcelableExtra(KEY_PROJECT);
        if (project != null) {
            setUpUI();
        } else {
            findViewById(R.id.project_edit_delete_button).setVisibility(View.GONE);
        }
    }

    private void setUpUI() {
        ((EditText) findViewById(R.id.project_edit_projectName)).setText(project.projectName);
        ((EditText) findViewById(R.id.project_edit_startDate))
                .setText(DateUtil.dateToString(project.startDate));
        ((EditText) findViewById(R.id.project_edit_endDate))
                .setText(DateUtil.dateToString(project.endDate));
        ((EditText) findViewById(R.id.project_edit_details))
                .setText(TextUtils.join("\n", project.targetList));
        findViewById(R.id.project_edit_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_PROJECT_ID, project.id);
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
        if (project == null) {
            project = new Project();
        }
        /**
         * collect the data use experience
         * */
        project.projectName =
                ((EditText)findViewById(R.id.project_edit_projectName)).getText().toString();
        project.startDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.project_edit_startDate)).getText().toString());
        project.endDate = DateUtil.stringToDate
                (((EditText)findViewById(R.id.project_edit_endDate)).getText().toString());
        project.targetList = Arrays.asList(TextUtils.split
                (((EditText)findViewById(R.id.project_edit_details)).getText().toString(), "\n"));

        /**
         * use intent to package data and then transit it between activities\
         * we can use this method only when project class has implement Parceble
         * */
        Intent intent = new Intent();
        intent.putExtra(KEY_PROJECT, project); // put date into intent
        setResult(Activity.RESULT_OK, intent); // put intent into activity result
        finish(); // finish the current activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }
}
