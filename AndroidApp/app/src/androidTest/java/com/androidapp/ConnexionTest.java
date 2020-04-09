package com.androidapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.runner.RunWith;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import com.androidapp.vue.activity.HomeActivity;
import org.junit.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class ConnexionTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);
    

    @Test
    public void graphicTest() throws InterruptedException {

        ViewInteraction connexion;
        ViewInteraction champs;

        // Vérifie les champs de la page d'accueil
        champs = onView(withId(R.id.btnpar)).check(matches(withText("Commencer un nouveau parcours")));
        champs = onView(withId(R.id.textView2)).check(matches(withText("Bienvenue à la plateforme de création de parcours.\nPour un nouveau parcours cliquez sur commencer un nouveau parcours:")));
        champs = onView(withId(R.id.btninscription)).check(matches(withText("Commencer une inscription")));

        // Clique sur le bouton valider
        connexion = onView(withId(R.id.btnpar)).perform(click());

        // Vérifie les champs sur le popup de connexion
        champs = onView(withText("Numéro étudiant : ")).check(matches(isDisplayed()));
        champs = onView(withText("Mot de passe : ")).check(matches(isDisplayed()));
        champs = onView(withId(R.id.userName)).check(matches(withHint("Numéro étudiant")));
        champs = onView(withId(R.id.password)).check(matches(withHint("Mot de passe")));
        champs = onView(withId(R.id.button_cancel_user_data)).check(matches(withText("Annuler")));
        champs = onView(withId(R.id.button_connexion)).check(matches(withText("Valider")));

        // Connexion
        connexion = onView(withId(R.id.userName)).perform(click()).perform(typeText("oj628639"));
        connexion = onView(withId(R.id.password)).perform(click()).perform(typeText("motdepasse"));
        connexion = onView(withId(R.id.button_connexion)).perform(click());
    }
}
