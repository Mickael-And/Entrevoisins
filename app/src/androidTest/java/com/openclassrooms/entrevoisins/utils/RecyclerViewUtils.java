package com.openclassrooms.entrevoisins.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public abstract class RecyclerViewUtils {

    /**
     * Fourni une {@link ViewAction} permettant de cliquer sur une vue à l'int"rieur d'un item de la RecyclerView.
     *
     * @param id view que l'on souhaite
     * @return l'action
     */
    public static ViewAction clickChildView(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
