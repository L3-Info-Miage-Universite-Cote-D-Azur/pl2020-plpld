package com.androidapp;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.runner.RunWith;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import com.androidapp.vue.activity.HomeActivity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class InscriptionTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void graphicTest() throws InterruptedException {

        ViewInteraction connexion;
        ViewInteraction champs;

        // Vérifie les champs de la page d'accueil
        connexion = onView(withId(R.id.btnpar)).check(matches(withText("Commencer un nouveau parcours")));
        connexion = onView(withId(R.id.textView2)).check(matches(withText("Bienvenue à la plateforme de création de parcours.\nPour un nouveau parcours cliquez sur commencer un nouveau parcours:")));
        connexion = onView(withId(R.id.btninscription)).check(matches(withText("Commencer une inscription")));

        // Clique sur le bouton Commencer un nouveau parcours
        connexion = onView(withId(R.id.btnpar)).perform(click());

        // Vérifie les champs sur le popup de connexion
        champs = onView(withText("Numéro étudiant : ")).check(matches(isDisplayed()));
        champs = onView(withText("Mot de passe : ")).check(matches(isDisplayed()));
        champs = onView(withId(R.id.userName)).check(matches(withHint("Numéro étudiant")));
        champs = onView(withId(R.id.password)).check(matches(withHint("Mot de passe")));

        // Clique sur le bouton Annuler
        connexion = onView(withId(R.id.button_cancel_user_data)).perform(click());

        // Clique sur le bouton Commencer une inscription
        connexion = onView(withId(R.id.btninscription)).perform(click());

        //Vérifie les champs de la page d'inscription
        champs = onView(withId(R.id.textView4)).check(matches(withText("Formulaire d'inscription")));
        champs = onView(withId(R.id.numEtudiant)).check(matches(withHint("Numéro étudiant")));
        champs = onView(withId(R.id.nom)).check(matches(withHint("Nom de famille")));
        champs = onView(withId(R.id.prénom)).check(matches(withHint("Prénom")));
        champs = onView(withId(R.id.naissance)).check(matches(withHint("Date de naissance")));
        champs = onView(withId(R.id.mdp)).check(matches(withHint("Mot de passe")));
        champs = onView(withId(R.id.buttonValiderInscription)).check(matches(withText("Valider")));

        // Remplissage des champs
        champs = onView(withId(R.id.numEtudiant)).perform(click()).perform(typeText("jm529620")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.nom)).perform(click()).perform(typeText("jean")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.prénom)).perform(click()).perform(typeText("marc")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.naissance)).perform(click()).perform(typeText("11/11/2011")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.mdp)).perform(click()).perform(typeText("jmjmjmjm")).perform(ViewActions.closeSoftKeyboard());

        // Clique sur le bouton valider
        connexion = onView(withId(R.id.buttonValiderInscription)).perform(click());
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
