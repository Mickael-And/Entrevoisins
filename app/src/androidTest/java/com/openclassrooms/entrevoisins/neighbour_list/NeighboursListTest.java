
package com.openclassrooms.entrevoisins.neighbour_list;

import android.app.Activity;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.information.NeighbourInformationActivity;
import com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion;
import com.openclassrooms.entrevoisins.utils.RecyclerViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    /**
     * Service de gestion des utilisateurs.
     */
    private NeighbourApiService mApiService;

    @Rule
    public IntentsTestRule<ListNeighbourActivity> intentsTestRule = new IntentsTestRule<>(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        Activity mActivity = intentsTestRule.getActivity();
        this.mApiService = DI.getNewInstanceApiService();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Test si l'activité {@link NeighbourInformationActivity} se lance bien.
     */
    @Test
    public void checkIfMyNeighbourInformationActivityIsLaunching() {
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Intents.intended(IntentMatchers.hasComponent(NeighbourInformationActivity.class.getName()));
    }

    /**
     * Test si la suppression d'un utilisateur fonctionne bien.
     */
    @Test
    public void checkIfRemovingUserIsWorking() {
        int currentSizeList = mApiService.getNeighbours().size();
        onView(withId(R.id.list_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(currentSizeList));
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, RecyclerViewUtils.clickChildView(R.id.item_list_delete_button)));
        onView(withId(R.id.list_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(currentSizeList - 1));
    }


    /**
     * Test vérifiant que l'onglet favori n'affiche que les voisins favoris.
     */
    @Test
    public void checkFavoriteList() {
        onView(withId(R.id.list_favorite_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(0));
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fab_favorite)).perform(click());
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        onView(withId(R.id.list_favorite_neighbours)).check(RecyclerViewItemCountAssertion.withItemCount(1));
    }


}