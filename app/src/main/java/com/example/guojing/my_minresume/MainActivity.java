package com.example.guojing.my_minresume;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guojing.my_minresume.model.BasicInfo;
import com.example.guojing.my_minresume.model.Education;
import com.example.guojing.my_minresume.model.Experience;
import com.example.guojing.my_minresume.model.Project;
import com.example.guojing.my_minresume.util.DateUtil;
import com.example.guojing.my_minresume.util.ImageUtil;
import com.example.guojing.my_minresume.util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE_EDUCATION_EDIT = 100;
    public static final int REQ_CODE_EXPERIENCE_EDIT = 200;
    public static final int REQ_CODE_PROJECT_EDIT = 300;
    public static final int REQ_CODE_BASICINFO_EDIT = 400;

    public static final String MODEL_BASIC_INFO = "basicInfo";
    public static final String MODEL_EDUCATION = "educations";
    public static final String MODEL_EXPERIENCE = "experiences";
    public static final String MODEL_PROJECT = "projects";

    private List<Education> educationList;
    private BasicInfo basicInfo;
    private List<Experience> experienceList;
    private List<Project> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the function that this activity set view on
        setContentView(R.layout.activity_main);

//        fakeData();
        loadDate();
        setUpUI();
    }

    /*
    * set up UI section
    * */

    /**
     * if any activity return a result to the current activity, the function will be called
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * request from this activity to EducationEditActivity
         * the result is from EducationEditActivity to this acticity
         * education edit step 4
         * education add step 4
         * */
        if (requestCode == REQ_CODE_EDUCATION_EDIT
                && resultCode == Activity.RESULT_OK) {
            String educationId = data.getStringExtra(EducationEditActivity.KEY_EDUCATON_ID);
            /**
             * delete the education object in education list base on id and then re-set up the UI
             * eudcation delete button step 4
             * */
            if (educationId != null) { // delete an education
                deleteEducation(educationId);
                setUpEducation();
            } else { // edit or add an education
                Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATON);
                /**
                 * update the education list
                 * */
                updateEducation(education);
                /**
                 * re-set up the UI
                 * education edit step 5
                 * education add step 5
                 * */
                setUpEducation();
            }
        }
        /**
         * request from this activity to ExperienceEditActivity
         * the result is from ExperienceEditActivity to this acticity
         * */
        if (requestCode == REQ_CODE_EXPERIENCE_EDIT
                && resultCode == Activity.RESULT_OK) {
            String experienceId = data.getStringExtra(ExperienceEditActivity.KEY_EXPERIENCE_ID);
            if (experienceId != null) {
                deleteExperience(experienceId);
                setUpExperience();
            } else {
                Experience experience = data.getParcelableExtra(ExperienceEditActivity.KEY_EXPERIENCE);
                /**
                 * update the experience list
                 * experience edit step 4
                 * */
                updateExperience(experience);
                setUpExperience();
            }
        }
        /**
         * request from this activity to ProjectEditActivity
         * the result is from ProjectEditActivity to this acticity
         * */
        if (requestCode == REQ_CODE_PROJECT_EDIT
                && resultCode == Activity.RESULT_OK) {
            String projectId = data.getStringExtra(ProjectEditActivity.KEY_PROJECT_ID);
            if (projectId != null) {
                deleteProject(projectId);
                setUpProject();
            } else {
                Project project = data.getParcelableExtra(ProjectEditActivity.KEY_PROJECT);
                /**
                 * update the project list
                 * project edit step 4
                 * */
                updateProject(project);
                // re set up the education UI
                setUpProject();
            }
        }
        /**
         * request from this activity to BasicInfoeditActivity
         * the result is from BasicInfoeditActivity to this acticity
         * */
        if (requestCode == REQ_CODE_BASICINFO_EDIT && resultCode == Activity.RESULT_OK) {
            basicInfo = data.getParcelableExtra(BasicInfoEditActivity.KEY_BASICINFO);
//            updateBasicInfo(basicInfo);
            setUpBasicInfo();
        }
    }

    private void setUpUI() {
        setUpBasicInfo();
        setUpEducation();
        setUpExperience();
        setUpProject();

        /**
         * add education button
         * set the on click listener for the add button so that it can jump to other activity
         * and that activity can return a result to this activity
         * add education step 1
         * */
        // use the image button to launch EducationEditActivity
        ImageButton EducationAddButton = (ImageButton) findViewById(R.id.education_add_button);
        EducationAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the way to start a new activity
                // https://developer.android.com/training/basics/firstapp/starting-activity.html#RespondToButton
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
//                startActivity(intent);
                /**
                 * if EducationEditActivity will return a result to the current activity
                 * we should use startActivityForResult
                 * REQ_CODE_EDUCATION_EDIT marks that the intent send from this activity
                 * */
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        /**
         * add experience button
         * */
        ImageButton experienceAddButton = (ImageButton) findViewById(R.id.experience_add_button);
        experienceAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });

        /**
         * add project button
         * */
        ImageButton projectAddButton = (ImageButton) findViewById(R.id.project_add_button);
        projectAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }

        });
    }

    // set up basic information

    private void setUpBasicInfo() {
        ((TextView) findViewById(R.id.user_name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.email)).setText(basicInfo.email);
        /**
         * load user picture
         * */
        ImageView userPicture = ((ImageView) findViewById(R.id.user_picture));
        if (basicInfo.imageUri == null) {
            userPicture.setImageResource(R.drawable.user_ghost);
        } else {
            ImageUtil.loadImage(MainActivity.this, basicInfo.imageUri, userPicture);
        }
        /**
         * set up the basic info button
         * Basic info edit step 1
         * */
        findViewById(R.id.basic_info_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BasicInfoEditActivity.class);
                intent.putExtra(BasicInfoEditActivity.KEY_BASICINFO, basicInfo);
                startActivityForResult(intent, REQ_CODE_BASICINFO_EDIT);
            }
        });
    }

    /* set up education */

    private void setUpEducation() {
        // step 4: find the empty container that we put pur views on
        LinearLayout educationLayout = ((LinearLayout)findViewById(R.id.education_list));
        /**
         * every time we set up new educationLayout, we need to remove the old one
         * */
        educationLayout.removeAllViews();
        for (Education edu : educationList) {
            // step 5: add the view on
            educationLayout.addView(getEducationView(edu));
        }
    }

    // put the date in edu into a view
    // we use final here so that we can have a copy of edu
    private View getEducationView(final Education edu) {
        /**
         * step 1: find the container(view) that we are going to add contents on
         * */
        View view = getLayoutInflater().inflate(R.layout.education_element, null);
        String dateString =
                DateUtil.dateToString(edu.startDate) + " ~ " + DateUtil.dateToString(edu.endDate);
        String courseString = createCourseString(edu);
        /**
         * step 2: set up the view
         * */
        ((TextView) view.findViewById(R.id.school_and_date)).setText(edu.school + " " + dateString);
        ((TextView) view.findViewById(R.id.education_course_list)).setText(courseString);
        /**
         * step 3: set up the edit button
         * education edit step 1
         * */
        view.findViewById(R.id.education_element_edit_button).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                // the image button will correspondent to its education view
                /**
                 * here the edu has a "final". which means the edu always refer to same object
                 * */
                intent.putExtra(EducationEditActivity.KEY_EDUCATON, edu);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
        /**
         * step 4: return the view
         * */
        return view;
    }

    private String createCourseString(Education edu) {
        if (edu.courseList == null || edu.courseList.size() == 0) {
            return "";
        }
        int size = edu.courseList.size();
        String courseString = "-" + edu.courseList.get(0);
        for (int i = 1; i < size; ++i) {
            courseString  = courseString + "\n- " + edu.courseList.get(i);
        }
        return courseString;
    }

    /* set up experience */

    private void setUpExperience() {
        LinearLayout experienceLayout = ((LinearLayout)findViewById(R.id.experience_list));
        experienceLayout.removeAllViews();
        for (Experience exp : experienceList) {
            experienceLayout.addView(getExperienceView(exp));
        }
    }

    // put the date in edu into a view
    private View getExperienceView(final Experience exp) {
        View view = getLayoutInflater().inflate(R.layout.experience_element, null);
        String dateString =
                DateUtil.dateToString(exp.startDate) + " ~ " + DateUtil.dateToString(exp.endDate);
        String workString = createWorkString(exp);
        ((TextView) view.findViewById(R.id.company_and_date)).setText(exp.company + " " + dateString);
        ((TextView) view.findViewById(R.id.experience_work_list)).setText(workString);
        /**
         * set up the experience edit button
         * experience edit step 1
         * */
        view.findViewById(R.id.experience_element_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExperienceEditActivity.class);
                intent.putExtra(ExperienceEditActivity.KEY_EXPERIENCE, exp);
                startActivityForResult(intent, REQ_CODE_EXPERIENCE_EDIT);
            }
        });
        return view;
    }

    private String createWorkString(Experience exp) {
        if (exp.workList == null || exp.workList.size() == 0) {
            return "";
        }
        int size = exp.workList.size();
        String workString = "- " + exp.workList.get(0);
        for (int i = 1; i < size; ++i) {
            workString = workString + "\n- " + exp.workList.get(i);
        }
        return workString;
    }

    /* set up project */

    private void setUpProject() {
        LinearLayout projectLayout = ((LinearLayout)findViewById(R.id.project_list));
        projectLayout.removeAllViews();
        for (Project pro : projectList) {
            projectLayout.addView(getProjectView(pro));
        }
    }

    // put the date in edu into a view
    private View getProjectView(final Project pro) {
        View view = getLayoutInflater().inflate(R.layout.project_element, null);
        String dateString =
                DateUtil.dateToString(pro.startDate) + " ~ " + DateUtil.dateToString(pro.endDate);
        String targetString = createTargetString(pro);
        ((TextView) view.findViewById(R.id.projectName_and_date)).setText(pro.projectName + " " + dateString);
        ((TextView) view.findViewById(R.id.project_target_list)).setText(targetString);
        /**
         * set up the project edit button
         * project edit step 1
         * */
        view.findViewById(R.id.project_element_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectEditActivity.class);
                intent.putExtra(ProjectEditActivity.KEY_PROJECT, pro);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });
        return view;
    }

    private String createTargetString(Project pro) {
        if (pro.targetList == null || pro.targetList.size() == 0) {
            return "";
        }
        int size = pro.targetList.size();
        String targetString = "- " + pro.targetList.get(0);
        for (int i = 1; i < size; ++i) {
            targetString = targetString + "\n- " + pro.targetList.get(i);
        }
        return targetString;
    }


    /**
     * update data section
     * */
    private void updateBasicInfo(BasicInfo basicInfo) {
    }

    private void updateEducation(Education education) {
        boolean found = false;
        for (int i = 0; i < educationList.size(); ++i) {
            // update the existed education
            if (TextUtils.equals(education.id, educationList.get(i).id)) {
                found = true;
                educationList.set(i, education);
                break;
            }
        }
        // add a education object
        if (!found) {
            educationList.add(education);
        }
        // save the education list to shared preference
        ModelUtil.save(MainActivity.this, MODEL_EDUCATION, educationList);
    }

    private void updateExperience(Experience experience) {
        boolean found = false;
        for (int i = 0; i < experienceList.size(); ++i) {
            // update the existed education
            if (TextUtils.equals(experience.id, experienceList.get(i).id)) {
                found = true;
                experienceList.set(i, experience);
                break;
            }
        }
        // add a experience object
        if (!found) {
            experienceList.add(experience);
        }

        ModelUtil.save(MainActivity.this, MODEL_EXPERIENCE, experienceList);
    }

    private void updateProject(Project project) {
        boolean found = false;
        for (int i = 0; i < projectList.size(); ++i) {
            // update the existed education
            if (TextUtils.equals(project.id, projectList.get(i).id)) {
                found = true;
                projectList.set(i, project);
                break;
            }
        }
        // add a project object
        if (!found) {
            projectList.add(project);
        }

        ModelUtil.save(MainActivity.this, MODEL_PROJECT, projectList);
    }

    /**
     * delete section
     * */

    private void deleteEducation(String id) {
        for(int i = 0; i < educationList.size(); ++i) {
            String curId = educationList.get(i).id;
            if (TextUtils.equals(curId, id)) {
                educationList.remove(i);
                break;
            }
        }
        ModelUtil.save(MainActivity.this, MODEL_EDUCATION, educationList);
    }

    private void deleteExperience(String id) {
        for(int i = 0; i < experienceList.size(); ++i) {
            String curId = experienceList.get(i).id;
            if (TextUtils.equals(curId, id)) {
                experienceList.remove(i);
                break;
            }
        }
        ModelUtil.save(MainActivity.this, MODEL_EXPERIENCE, experienceList);
    }

    private void deleteProject(String id) {
        for(int i = 0; i < projectList.size(); ++i) {
            String curId = projectList.get(i).id;
            if (TextUtils.equals(curId, id)) {
                projectList.remove(i);
                break;
            }
        }
        ModelUtil.save(MainActivity.this, MODEL_PROJECT, projectList);
    }

    /**
     * load date section
     * */

    private void loadDate() {
        BasicInfo saveBasicInfo = ModelUtil.read(MainActivity.this,
                MODEL_BASIC_INFO, new TypeToken<BasicInfo>(){});
        if (saveBasicInfo == null) {
//            fakeDataBasicInfo();
//            ModelUtil.save(MainActivity.this, MODEL_BASIC_INFO, basicInfo);
            basicInfo = new BasicInfo();
        } else {
            basicInfo = saveBasicInfo;
        }

        List<Education> saveEducationList = ModelUtil.read(MainActivity.this, MODEL_EDUCATION,
                new TypeToken<List<Education>>(){});
        if (saveEducationList == null) {
//            fakeDataEducation();
//            ModelUtil.save(MainActivity.this, MODEL_EDUCATION, educationList);
            educationList = new ArrayList<Education>();
        } else {
            educationList = saveEducationList;
        }

        List<Experience> saveExperienceList = ModelUtil.read(MainActivity.this, MODEL_EXPERIENCE,
                new TypeToken<List<Experience>>(){});
        if (saveExperienceList == null) {
//            fakeDataExperience();
//            ModelUtil.save(MainActivity.this, MODEL_EXPERIENCE, experienceList);
            experienceList = new ArrayList<Experience>();
        } else {
            experienceList = saveExperienceList;
        }

        List<Project> saveProjectList = ModelUtil.read(MainActivity.this, MODEL_PROJECT,
                new TypeToken<List<Project>>(){});
        if (saveProjectList == null) {
//            fakeDateProject();
//            ModelUtil.save(MainActivity.this, MODEL_PROJECT, projectList);
            projectList = new ArrayList<Project>();
        } else {
            projectList = saveProjectList;
        }
    }

    /**
     * fake data section
     **/

    private void fakeData() {
        fakeDataEducation();
        fakeDataBasicInfo();
        fakeDataExperience();
        fakeDateProject();
    }

    private void fakeDataEducation() {
        // first fake data

        educationList = new ArrayList<Education>();

        Education edu1 = new Education();
        edu1.school = "school name";
        edu1.startDate = DateUtil.stringToDate("mm/yyyy");
        edu1.endDate = DateUtil.stringToDate("mm/yyyy");
        edu1.courseList = new ArrayList<>();
        edu1.courseList.add("course1");
        edu1.courseList.add("course2");
        educationList.add(edu1);
    }

    private void fakeDataBasicInfo() {
        basicInfo = new BasicInfo();
        basicInfo.name = "your name";
        basicInfo.email = "your_name@gmail.com";
    }

    private void fakeDataExperience() {
        experienceList = new ArrayList<Experience>();

        Experience exp1 = new Experience();
        exp1.company = "company name";
        exp1.startDate = DateUtil.stringToDate("mm/yyyy");
        exp1.endDate = DateUtil.stringToDate("mm/yyyy");
        exp1.workList = new ArrayList<>();
        exp1.workList.add("work1");
        exp1.workList.add("work2");
        experienceList.add(exp1);
    }

    private void fakeDateProject() {
        projectList = new ArrayList<Project>();

        Project pro1 = new Project();
        pro1.projectName = "project name";
        pro1.startDate = DateUtil.stringToDate("mm/yyyy");
        pro1.endDate = DateUtil.stringToDate("mm/yyyy");
        pro1.targetList = new ArrayList<>();
        pro1.targetList.add("target1");
        pro1.targetList.add("target2");
        projectList.add(pro1);
    }

}
