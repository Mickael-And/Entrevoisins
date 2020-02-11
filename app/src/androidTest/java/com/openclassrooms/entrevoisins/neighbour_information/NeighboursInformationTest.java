
package com.openclassrooms.entrevoisins.neighbour_information;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.information.NeighbourInformationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.intents.IntentName.INFORMATION_ACTIVITY_INTENT_NAME;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursInformationTest {

    /**
     * Service de gestion des utilisateurs.
     */
    private NeighbourApiService mApiService;

    @Rule
    public IntentsTestRule<NeighbourInformationActivity> mActivityRule =
            new IntentsTestRule<NeighbourInformationActivity>(NeighbourInformationActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = ApplicationProvider.getApplicationContext();
                    Intent result = new Intent(targetContext, NeighbourInformationActivity.class);
                    result.putExtra(INFORMATION_ACTIVITY_INTENT_NAME, 1);
                    return result;
                }
            };

    @Before
    public void setUp() {
        this.mApiService = DI.getNewInstanceApiService();
    }

    /**
     * Test si le nom affiché est bien celui de l'utilisateur passé en paramètre.
     */
    @Test
    public void checkInitNameValue() {
        onView(withId(R.id.tv_neighbour_name)).check(matches(withText(mApiService.getNeighbour(1).getName())));
    }

}