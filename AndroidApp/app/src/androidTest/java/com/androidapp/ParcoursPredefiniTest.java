package com.androidapp;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import com.androidapp.vue.activity.HomeActivity;
import org.junit.*;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.StringContains.containsString;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class ParcoursPredefiniTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void graphiqueTest() throws InterruptedException {

        ViewInteraction connexion;
        ViewInteraction verifMatiere;
        ViewInteraction scrollView;
        ViewInteraction champs;
        ViewInteraction clicCommencer;

        // Clique sur le bouton Commencer une inscription
        connexion = onView(withId(R.id.btninscription)).perform(click());

        // Remplissage des champs
        champs = onView(withId(R.id.numEtudiant)).perform(click()).perform(typeText("jm529620")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.nom)).perform(click()).perform(typeText("jean")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.prénom)).perform(click()).perform(typeText("marc")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.naissance)).perform(click()).perform(typeText("11/11/2011")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.mdp)).perform(click()).perform(typeText("jmjmjmjm")).perform(ViewActions.closeSoftKeyboard());

        // Clique sur le bouton valider
        clicCommencer = onView(withId(R.id.buttonValiderInscription)).perform(click());

        // Clique sur parcours personnaliser
        connexion = onView(withText("Parcours prédéfini")).perform(click());

        // Clique sur Informatique
        connexion = onView(withText("Informatique")).perform(click());


        // Récape
        // Semestre 1
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Bases de l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Introduction à l'informatique par le web"))));

        // Semestre 2
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("System 1. Unix et progra shell"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Programmation impérative"))));

        // Semestre 3
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Structures de données et programmation C"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Bases de données"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Outils formels pour l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Algo & prog avec R"))));

        // Semestre 4
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Algorithmique 1"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Réseaux et télécommunication"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Systèmes 2 : Mécanisme internes des systèmes d'exploitation"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Introduction aux systèmes intelligents"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Technologie du Web"))));

        // Clique sur "finaliser mon inscription"
        clicCommencer = onView(withId(R.id.buttonConfirmation)).perform(click());
    }
}