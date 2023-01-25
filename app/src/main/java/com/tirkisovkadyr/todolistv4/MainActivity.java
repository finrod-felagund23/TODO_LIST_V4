package com.tirkisovkadyr.todolistv4;
// javac -classpath .;.\libs\*; ch01\sec01\jacksonWorks\Main.java && java -classpath libs\*; ch01.sec01.jacksonWorks.Main
// javac -classpath libs\*; ch01\sec01\HelloWorld.java && java -classpath libs\*; ch01.sec01.HelloWorld


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonHelper.init();
        WorkWithTodoHelper.init();

//        ArrayList<Todo> todos0 = new ArrayList<Todo>() {
//            {
//                add(new Todo(0, "Do something", "Someday", false));
//                add(new Todo(1, "Do nothing", "Everyday", false));
//                add(new Todo(2, "Take medicine", "Use 2 pills of anti-drag medicine", false));
//                add(new Todo(3, "Go to Gym", "Go To Gum and make some workout!", true));
//                add(new Todo(4, "Make impact in todo list project", "Begin making styles and themes for done design", false));
//                add(new Todo(5, "Write tests for my android app", "First of all learn how write tests for android apps", false));
//            }
//        };
//
//        JsonHelper.exportToJson(this, todos0);

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

        if (Objects.requireNonNull(todos, "Now you haven't any todo").size() == 0) {
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
    }




    public void addNewTodoOnClick(View view) {
        Intent intent = new Intent(this, MakeOrChangeTodoActivity.class);
        intent.putExtra("id", -1);
        startActivity(intent);
    }
}