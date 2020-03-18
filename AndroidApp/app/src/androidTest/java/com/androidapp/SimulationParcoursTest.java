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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.StringContains.containsString;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class SimulationParcoursTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void graphicTest() throws InterruptedException {

        ViewInteraction verifMatiere;
        ViewInteraction clicGroupe;
        ViewInteraction clicMatiere;
        ViewInteraction scrollView;

        ViewInteraction clicCommencer = onView(
                withId(R.id.btnpar)).perform(click());


        /////////////// Semestre 1 ///////////////
        clicGroupe = onView(
                withText("Informatique")).perform(click());
        clicMatiere = onView(
                withText("Bases de l'informatique")).perform(click());
        clicMatiere = onView(
                withText("Introduction à l'informatique par le web")).perform(click());
        clicGroupe = onView(
                withText("Informatique")).perform(click());

        clicGroupe = onView(
                withText("Electronique")).perform(click());
        clicMatiere = onView(
                withText("Electronique numerique - Bases")).perform(click());
        clicGroupe = onView(
                withText("Electronique")).perform(click());

        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Fondements 1")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : approche continue")).perform(click());
        clicMatiere = onView(
                withText("Complements 1")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : approche continue")).perform(click());
        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        /////////////// Semestre 2 ///////////////
        clicGroupe = onView(
                withText("Informatique")).perform(click());
        clicMatiere = onView(
                withText("System 1. Unix et progra shell")).perform(click());
        clicMatiere = onView(
                withText("Programmation impérative")).perform(click());
        clicGroupe = onView(
                withText("Informatique")).perform(click());

        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Fondements 2")).perform(click());
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());

        clicGroupe = onView(
                withText("MIASHS")).perform(click());
        clicMatiere = onView(
                withText("Economie-Gestion S2")).perform(click());
        clicGroupe = onView(
                withText("MIASHS")).perform(click());

        clicGroupe = onView(
                withText("Physique")).perform(click());
        clicMatiere = onView(
                withText("Mécanique 2")).perform(click());
        clicMatiere = onView(
                withText("Optique 1")).perform(click());
        clicMatiere = onView(
                withText("Mécanique 2")).perform(click());
        clicGroupe = onView(
                withText("Physique")).perform(click());

        clicGroupe = onView(
                withText("Electronique")).perform(click());
        clicMatiere = onView(
                withText("Electronique analogique")).perform(click());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        /////////////// Semestre 3 ///////////////
        clicGroupe = onView(
                withText("Informatique")).perform(click());
        clicMatiere = onView(
                withText("Structures de données et programmation C")).perform(click());
        clicMatiere = onView(
                withText("Bases de donnée")).perform(click());
        clicGroupe = onView(
                withText("Informatique")).perform(click());

        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : Mathématiques et ingénierie")).perform(click());
        clicMatiere = onView(
                withText("Complements 1")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : Mathématiques et ingénierie")).perform(click());
        clicMatiere = onView(
                withText("Complements 1")).perform(click());
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());

        clicGroupe = onView(
                withText("Electronique")).perform(click());
        clicMatiere = onView(
                withText("Système embarqué")).perform(click());
        clicGroupe = onView(
                withText("Electronique")).perform(click());

        clicGroupe = onView(
                withText("Géographie")).perform(click());
        clicMatiere = onView(
                withText("Decouverte 1")).perform(click());
        clicGroupe = onView(
                withText("Géographie")).perform(click());

        scrollView = onView(
                withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        clicGroupe = onView(
                withText("MIASHS")).perform(click());
        clicMatiere = onView(
                withText("Economie-Gestion S3")).perform(click());
        clicMatiere = onView(
                withText("Intro R")).perform(click());
        clicGroupe = onView(
                withText("MIASHS")).perform(click());

        scrollView = onView(
                withId(R.id.UE_list)).perform(ViewActions.swipeUp());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());
        Thread.sleep(1000);

        /////////////// Semestre 4 ///////////////

        clicGroupe = onView(
                withText("Informatique")).perform(click());
        clicMatiere = onView(
                withText("Réseaux et télécommunication")).perform(click());
        clicMatiere = onView(
                withText("Algorithmique 1")).perform(click());
        clicMatiere = onView(
                withText("Systèmes 2 : Mécanisme internes des systèmes d'exploitation")).perform(click());
        clicGroupe = onView(
                withText("Informatique")).perform(click());

        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Analyse")).perform(click());
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());

        clicGroupe = onView(
                withText("Physique")).perform(click());
        clicMatiere = onView(
                withText("Electromagnétisme 2")).perform(click());
        clicMatiere = onView(
                withText("Ondes")).perform(click());
        clicMatiere = onView(
                withText("Electromagnétisme 2")).perform(click());
        clicGroupe = onView(
                withText("Physique")).perform(click());

        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : approche discrète")).perform(click());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());

        Thread.sleep(10000);

        // Récape
        // Semestre 1
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Bases de l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Introduction à l'informatique par le web"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Electronique numerique - Bases"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Fondements 1"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Complements 1"))));

        // Semestre 2
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("System 1. Unix et progra shell"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Programmation impérative"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Fondements 2"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Economie-Gestion S2"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Optique 1"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Electronique analogique"))));

        // Semestre 3
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Structures de données et programmation C"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Bases de donnée"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Système embarqué"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Decouverte 1"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Economie-Gestion S3"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Intro R"))));

        // Semestre 4
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Réseaux et télécommunication"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Algorithmique 1"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Systèmes 2 : Mécanisme internes des systèmes d'exploitation"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Analyse"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Ondes"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Méthodes : approche discrète"))));
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
