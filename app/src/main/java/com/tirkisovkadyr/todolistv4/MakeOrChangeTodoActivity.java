package com.tirkisovkadyr.todolistv4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MakeOrChangeTodoActivity extends AppCompatActivity {

    private int todoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if ("night".equals(Crutch.getTheme())) {
            setTheme(R.style.Theme_TODOLISTV4_night1);
        } else if ("light".equals(Crutch.getTheme())) {
            setTheme(R.style.Theme_TODOLISTV4_light);
        }

        TextView txt1 = new TextView(this);
        txt1.setText("My Test View!");
        txt1.setTextSize(27);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setCustomView(txt1);
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
        actionBar.setTitle("My Title");
        actionBar.show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_todo);

        WorkWithTodoHelper.init();
        JsonHelper.init();

        Bundle arguments = getIntent().getExtras();


        if (-1 != (int) arguments.get("id")) {
            EditText themeEdt = findViewById(R.id.theme_edt);
            EditText descrEdt = findViewById(R.id.descr_edt);

            List<Todo> todos = JsonHelper.importFromJson(this);
            for (Todo todo : Objects.requireNonNull(todos, "Json helper returned NUll MakeOrChangeActivity0")) {
                if (todo.getId() == (int) arguments.get("id")) {
                    themeEdt.setText(todo.getTheme());
                    descrEdt.setText(todo.getDescription());
                    todoId = todo.getId();
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("Настройки");
        menu.add("Открыть");
        menu.add("Сохранить");
        return true;
    }

    public void onClickAddBtn(View view) {

        EditText theme = findViewById(R.id.theme_edt);
        EditText descr = findViewById(R.id.descr_edt);

        List<Todo> todos = JsonHelper.importFromJson(this);

        if (todoId != -1) {
            for (Todo todo : Objects.requireNonNull(todos, "Make new todo onclickaddbtn1")) {
                if (todo.getId() == todoId) {
                    todo.setTheme(theme.getText().toString());
                    todo.setDescription(descr.getText().toString());
                    break;
                }
            }

        } else {
            if (todos == null) {
                todos = new ArrayList<>();
            }
            int id = WorkWithTodoHelper.searchNewId(Objects.requireNonNull(todos, "Make new Todo Activity searchId with workWithTodoHelper"));
            todos.add(
                    new Todo(
                            id,
                            theme.getText().toString(),
                            descr.getText().toString(),
                            false
                    )
            );

        }

        JsonHelper.exportToJson(this, todos);

        finish();
    }

    public void onClickDelBtn(View view) {
        finish();
    }
}