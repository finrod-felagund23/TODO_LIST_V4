package com.tirkisovkadyr.todolistv4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class MakeNewTodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_todo);

    }

    public void onClickAddBtn(View view) {
        EditText theme = findViewById(R.id.theme_edt);
        EditText descr = findViewById(R.id.descr_edt);

        List<Todo> todos = JsonHelper.importFromJson(this);

        WorkWithTodoHelper.init();

        int id = WorkWithTodoHelper.searchNewId(Objects.requireNonNull(todos, "Make new Todo Activity get id with workWithTodoHelper"));

        todos.add(new Todo(id, theme.getText().toString(), descr.getText().toString()));

        JsonHelper.exportToJson(this, todos);

        finish();
    }

    public void onClickDelBtn(View view) {
        finish();
    }
}