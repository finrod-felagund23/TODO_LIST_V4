package com.tirkisovkadyr.todolistv4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT
    );
    private static final ConstraintLayout.LayoutParams layoutParamsForDescriptionTxt = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT
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
    }

    /**
     * Returns new todo representation with given arguments
     * @param context - just <code>this</code>
     * @param theme - theme for make todo repr
     * @param descr - description for make todo repr
     * @return - <code>constraintLayout</code> with given arguments
     */
    public static ConstraintLayout getTodoRepresentation(Context context, String theme, String descr) {
        ConstraintLayout todoRepr = new ConstraintLayout(context);
        todoRepr.setLayoutParams(makeLayoutParamsForTodoRepr()); // own func
        todoRepr.setId(View.generateViewId());

        TextView txtTheme = new TextView(context);
        txtTheme.setId(View.generateViewId());
        txtTheme.setLayoutParams(layoutParamsForThemeTxt);
        txtTheme.setId(View.generateViewId());
        txtTheme.setText(theme);
        txtTheme.setTextSize(20);

        layoutParamsForDescriptionTxt.topToBottom = txtTheme.getId();

        TextView txtDescription = new TextView(context);
        txtDescription.setId(View.generateViewId());
        txtDescription.setLayoutParams(layoutParamsForDescriptionTxt);
        txtDescription.setId(View.generateViewId());
        txtDescription.setText(descr);

        todoRepr.addView(txtTheme);
        todoRepr.addView(txtDescription);

        todoRepr.setBackgroundResource(R.drawable.layout_border);

        // TODO add checkbox isDone for every todo representation

        return todoRepr;
    }

    /**
     * If that todo repr
     * is not first, we need place him below prev
     * @param context - just <code>this</code> for mobile activity
     * @param theme - theme of todo
     * @param descr - description of todo
     * @param idOfTopView - id of above todo repr
     * @return - - <code>constraintLayout</code> with given arguments
     */
    public static ConstraintLayout getTodoRepresentation(Context context, String theme, String descr, int idOfTopView) {
        ConstraintLayout todoRepr = new ConstraintLayout(context);
        todoRepr.setLayoutParams(makeLayoutParamsForTodoRepr(idOfTopView)); // own func
        todoRepr.setId(View.generateViewId());


        TextView txtTheme = new TextView(context);
        txtTheme.setLayoutParams(layoutParamsForThemeTxt);
        txtTheme.setId(View.generateViewId());
        txtTheme.setText(theme);
        txtTheme.setTextSize(20);

        layoutParamsForDescriptionTxt.topToBottom = txtTheme.getId();

        TextView txtDescription = new TextView(context);
        txtDescription.setLayoutParams(layoutParamsForDescriptionTxt);
        txtDescription.setId(View.generateViewId());
        txtDescription.setText(descr);

        todoRepr.addView(txtTheme);
        todoRepr.addView(txtDescription);

        todoRepr.setBackgroundResource(R.drawable.layout_border);

        return todoRepr;
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
}