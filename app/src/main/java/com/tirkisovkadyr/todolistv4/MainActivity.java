package com.tirkisovkadyr.todolistv4;
// javac -classpath .;.\libs\*; ch01\sec01\jacksonWorks\Main.java && java -classpath libs\*; ch01.sec01.jacksonWorks.Main
// javac -classpath libs\*; ch01\sec01\HelloWorld.java && java -classpath libs\*; ch01.sec01.HelloWorld


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Tittle 11");
        toolbar.setSubtitle("My Subtitle");
        toolbar.inflateMenu(R.menu.main_menu);


        if ("night".equals(Crutch.getTheme())) {
            setTheme(R.style.Theme_TODOLISTV4_night1);
        } else if ("light".equals(Crutch.getTheme())) {
            setTheme(R.style.Theme_TODOLISTV4_light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonHelper.init();
        WorkWithTodoHelper.init();

//        ScrollView scrollView = findViewById(R.id.main_scroll_view);
//        Button createTask = findViewById(R.id.create_task_btn);
//
////        scrollView.setOnScrollChangeListener((view) -> {
////
////        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                }
//            });
//        }
//
//        scrollView.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//
//                return false;
//            }
//        });

        ArrayList<Todo> todos0 = new ArrayList<Todo>() {
            {
                add(new Todo(0, "Do something", "Someday", false));
                add(new Todo(1, "Do nothing", "Everyday", false));
                add(new Todo(2, "Take medicine", "Use 2 pills of anti-drag medicine", false));
//                add(new Todo(3, "Go to Gym", "Go To Gum and make some workout!", true));
//                add(new Todo(4, "Make impact in todo list project", "Begin making styles and themes for done design", false));
//                add(new Todo(5, "Write tests for my android app", "First of all learn how write tests for android apps", false));
            }
        };

        JsonHelper.exportToJson(this, todos0);

        ConstraintLayout mainLayout = findViewById(R.id.main_constraint_layout);
//        mainLayout.setBackgroundResource(R.drawable.layout_border);
        List<Todo> todos = JsonHelper.importFromJson(this);
//        System.out.println(todos);
//        ConstraintLayout todoRepr;
//        int counter = 0;
//        int idOfTopView = -1;
//
//        for (Todo todo : Objects.requireNonNull(todos, "JsonHelper returned null")) {
//            System.out.println(todo);
//            if (counter == 0) {
//                counter++;
//                todoRepr = WorkWithTodoHelper.getTodoRepresentation
//                        (
//                                this,
//                                todo
//                        );
//            } else {
//                todoRepr = WorkWithTodoHelper.getTodoRepresentation
//                        (
//                                this,
//                                todo,
//                                idOfTopView
//                        );
//            }
//            idOfTopView = todoRepr.getId();
//            mainLayout.addView(todoRepr);
//        }

//        TextView txt1 = new TextView(this);
//        txt1.setText("My Test View!");
//        txt1.setTextSize(27);
//
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setCustomView(txt1);
////        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
//        actionBar.setTitle("My Title");
//        actionBar.show();



        try {
            Objects.requireNonNull(todos, "Now you haven't any todo");
        } catch(NullPointerException ex) {
            System.out.println("Todo Size == 0");
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.setMargins(0, 40, 0, 0);

            TextView txt = new TextView(this);
            txt.setText("Now You Haven't any todo");
            txt.setTextSize(27);
            txt.setLayoutParams(layoutParams);

            mainLayout.addView(txt);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
        //https://stackoverflow.com/questions/3438276/how-to-change-the-text-on-the-action-bar
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        ConstraintLayout mainLayout = findViewById(R.id.main_constraint_layout);
        mainLayout.setBackgroundResource(R.drawable.layout_border);
        List<Todo> todos = JsonHelper.importFromJson(this);
        System.out.println(todos);
        ConstraintLayout todoRepr;
        int counter = 0;
        int idOfTopView = -1;
        try {
            for (Todo todo : Objects.requireNonNull(todos, "JsonHelper returned null1")) {
                System.out.println(todo);
                if (counter == 0) {
                    counter++;
                    todoRepr = WorkWithTodoHelper.getTodoRepresentation
                            (
                                    this,
                                    todo
                            );
                } else {
                    todoRepr = WorkWithTodoHelper.getTodoRepresentation
                            (
                                    this,
                                    todo,
                                    idOfTopView
                            );
                }
                idOfTopView = todoRepr.getId();
                mainLayout.addView(todoRepr);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }




    public void addNewTodoOnClick(View view) {
        Intent intent = new Intent(this, MakeOrChangeTodoActivity.class);
        intent.putExtra("id", -1);
        startActivity(intent);
    }

    public void changeTheme(View view) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        if ("night".equals(Crutch.getTheme())) {
            Crutch.setTheme("light");
        } else {
            Crutch.setTheme("night");
        }
//        intent.putExtra("theme", theme);
//        setTheme(R.style.Theme_TODOLISTV4_night1);
        startActivity(intent);
    }
}