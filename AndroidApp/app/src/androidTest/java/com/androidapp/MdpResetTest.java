package com.androidapp;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import com.androidapp.vue.activity.HomeActivity;
import org.junit.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MdpResetTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void motDePasseReset() {

        ViewInteraction connexion;
        ViewInteraction champs;

        // On vérifie le champs "Mot de passe oublié ?"
        champs = onView(withId(R.id.btnmdpoublie)).check(matches(withText("Mot de passe oublié ?")));

        // On clique sur "Mot de passe oublié ?"
        connexion = onView(withId(R.id.btnmdpoublie)).perform(click());

        // On vérifie les champs
        champs = onView(withId(R.id.edt_reset_numEtudiant)).check(matches(withHint("Entrez votre numéro étudiant")));
        champs = onView(withId(R.id.edt_reset_dateNaissance)).check(matches(withHint("Entrez votre date de naissance")));
        champs = onView(withId(R.id.btn_reset_password)).check(matches(withText("Réinitialiser mon mot de passe")));
        champs = onView(withId(R.id.btn_back)).check(matches(withText("Retour arrière")));

        // On remplis les champs
        champs = onView(withId(R.id.edt_reset_numEtudiant)).perform(click()).perform(typeText("000000")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.edt_reset_dateNaissance)).perform(click()).perform(typeText("12/12/1212")).perform(ViewActions.closeSoftKeyboard());

        // On vérifie les champs remplis précédemment
        champs = onView(withId(R.id.edt_reset_numEtudiant)).check(matches(withText("000000")));
        champs = onView(withId(R.id.edt_reset_dateNaissance)).check(matches(withText("12/12/1212")));

        // On clique sur le bouton retour
        connexion = onView(withId(R.id.btn_back)).perform(click());
    }
}
