package com.tirkisovkadyr.todolistv4;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class for make todo representation
 * rectangles which contain all todos for user
 */
public class WorkWithTodoHelper {
    private static final ConstraintLayout.LayoutParams layoutParamsForThemeTxt = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
    );
    private static final ConstraintLayout.LayoutParams layoutParamsForDescriptionTxt = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
    );
    private static final ConstraintLayout.LayoutParams layoutParamsForIsDoneCheckbox = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
    );

//    private static ConstraintLayout.LayoutParams l = new ConstraintLayout.LayoutParams(width, height)

    /**
     * Need to make before first use
     */
    public static void init() {
        layoutParamsForThemeTxt.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.setMargins(0, 5, 0, 0);

        layoutParamsForDescriptionTxt.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.setMargins(0, 0, 0, 5);

        layoutParamsForIsDoneCheckbox.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForIsDoneCheckbox.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForIsDoneCheckbox.setMargins(20, 20, 20,0);
    }

    /**
     * Returns new todo representation with given arguments
     * @param context - just <code>this</code>
     * @param todo todo from json
     * @return - <code>constraintLayout</code> with given arguments
     */
    public static ConstraintLayout getTodoRepresentation(Context context, Todo todo) {
        // TODO: 25.01.2023 make todo representations clickable
        // TODO: 25.01.2023 if theme longer than 31characters show shortened version
        // TODO: 25.01.2023 if descr longer than \d+characters show shortened version
        // TODO: 25.01.2023 make button "create task" always on display in app
        // TODO: 25.01.2023 make button "create task" disappear if scroll on very bottom // setVisibility()

        String theme = todo.getTheme();
        String descr = todo.getDescription();
        boolean isDone = todo.getIsDone();

        ConstraintLayout todoRepr = new ConstraintLayout(context);
        todoRepr.setLayoutParams(makeLayoutParamsForTodoRepr()); // own func
        todoRepr.setId(View.generateViewId());

        CheckBox chkBox = generateChkIsDone(context, isDone);

        layoutParamsForThemeTxt.startToEnd = chkBox.getId(); // for place theme on right side of checkBox

        TextView txtTheme = generateTxtTheme(context, theme);

        layoutParamsForDescriptionTxt.startToEnd = chkBox.getId(); // for place descr on right side of checkBox
        layoutParamsForDescriptionTxt.topToBottom = txtTheme.getId(); // for place descr under theme

        TextView txtDescription = generateTxtDescr(context, descr);

        todoRepr.addView(chkBox);
        todoRepr.addView(txtTheme);
        todoRepr.addView(txtDescription);

        todoRepr.setBackgroundResource(R.drawable.layout_border);
        todoRepr.setOnClickListener((view) -> {
            int id = todo.getId();
            Intent intent = new Intent(context, MakeOrChangeTodoActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        });
        // TODO add checkbox isDone for every todo representation // done

        return todoRepr;
    }

    /**
     * If that todo repr
     * is not first, we need place him below prev
     * @param context - just <code>this</code> for mobile activity
     * @param todo todo from json
     * @param idOfTopView - id of above todo repr
     * @return - - <code>constraintLayout</code> with given arguments
     */
    public static ConstraintLayout getTodoRepresentation(Context context, Todo todo, int idOfTopView) {
        String theme = todo.getTheme();
        String descr = todo.getDescription();
        boolean isDone = todo.getIsDone();

        ConstraintLayout todoRepr = new ConstraintLayout(context);
        todoRepr.setLayoutParams(makeLayoutParamsForTodoRepr(idOfTopView)); // own func
        todoRepr.setId(View.generateViewId());

        CheckBox chkBox = generateChkIsDone(context, isDone);

        layoutParamsForThemeTxt.startToEnd = chkBox.getId();

        TextView txtTheme = generateTxtTheme(context, theme);

        layoutParamsForDescriptionTxt.startToEnd = chkBox.getId();
        layoutParamsForDescriptionTxt.topToBottom = txtTheme.getId();

        TextView txtDescription = generateTxtDescr(context, descr);

        todoRepr.addView(chkBox);
        todoRepr.addView(txtTheme);
        todoRepr.addView(txtDescription);

        todoRepr.setBackgroundResource(R.drawable.layout_border);
        todoRepr.setOnClickListener((view) -> {
            int id = todo.getId();
            Intent intent = new Intent(context, MakeOrChangeTodoActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        });

        return todoRepr;
    }

    /**
     * That method search new id for todo
     * just return most big value and make <code>bigValue++</code>
     * @param todos - list of todos from json
     * @return - new id for todo
     */
    public static int searchNewId(List<Todo> todos) {
        if (todos.size() == 0) return 0;
        ArrayList<Integer> ids = new ArrayList<>();


        for (Todo todo : todos) {
            ids.add(todo.getId());
        }
        Collections.sort(ids);

        return ids.get(ids.size()-1) + 1;
    }

    public static void onClickChangeTodo(View view) {

    }

    /**
     * Method helper for <code>getTodoRepresentation</code>
     * @return layout params for <code>constraintLayout</code>> that represents todo
     */
    private static ConstraintLayout.LayoutParams makeLayoutParamsForTodoRepr() {
        ConstraintLayout.LayoutParams layoutParams;

        layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.MATCH_PARENT, 200);

        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        layoutParams.setMargins(15, 30, 15, 0);

        return layoutParams;
    }

    /**
     * If that layout not top repr and has more reprs above
     * we need to place him below last above todo
     * @param idOfTopView - id of last above view
     * @return layout params for <code>constraintLayout</code> that represents todo
     */
    private static ConstraintLayout.LayoutParams makeLayoutParamsForTodoRepr(int idOfTopView) {

        ConstraintLayout.LayoutParams layoutParams;

        layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.MATCH_PARENT, 200);

        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToBottom = idOfTopView;

        layoutParams.setMargins(15, 30, 15, 0);

        return layoutParams;
    }

    private static TextView generateTxtTheme(Context context, String theme) {
        TextView txtTheme = new TextView(context);
        txtTheme.setId(View.generateViewId());
        txtTheme.setLayoutParams(layoutParamsForThemeTxt);
        txtTheme.setId(View.generateViewId());
        txtTheme.setText(theme);
        txtTheme.setTextSize(20);

        return txtTheme;
    }

    private static TextView generateTxtDescr(Context context, String descr) {
        TextView txtDescription = new TextView(context);
        txtDescription.setId(View.generateViewId());
        txtDescription.setLayoutParams(layoutParamsForDescriptionTxt);
        txtDescription.setId(View.generateViewId());
        txtDescription.setText(descr);

        return txtDescription;
    }

    private static CheckBox generateChkIsDone(Context context, boolean isDone) {
        CheckBox chkBox = new CheckBox(context);
        chkBox.setId(View.generateViewId());
//        chkBox.setEnabled(isDone);
        chkBox.setLayoutParams(layoutParamsForIsDoneCheckbox);

        return chkBox;
    }


}