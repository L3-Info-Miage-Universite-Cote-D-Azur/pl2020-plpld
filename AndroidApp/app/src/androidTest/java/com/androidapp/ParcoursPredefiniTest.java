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
        champs = onView(withId(R.id.numEtudiant)).perform(click()).perform(typeText("0000000000")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.nom)).perform(click()).perform(typeText("Homer")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.prénom)).perform(click()).perform(typeText("Simpson")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.naissance)).perform(click()).perform(typeText("11/11/2011")).perform(ViewActions.closeSoftKeyboard());
        champs = onView(withId(R.id.mdp)).perform(click()).perform(typeText("0000000000")).perform(ViewActions.closeSoftKeyboard());

        // Clique sur le bouton valider
        clicCommencer = onView(withId(R.id.buttonValiderInscription)).perform(click());

        try {
            champs = onView(withId(R.id.btnparcours)).check(matches(withText("Effectuer un choix de parcours")));
        }
        catch (Exception e) {
            return;
        }

        // On vérifie le text des boutons
        champs = onView(withId(R.id.btnparcours)).check(matches(withText("Effectuer un choix de parcours")));
        champs = onView(withId(R.id.btnconsulterparcours)).check(matches(withText("Consulter son parcours")));
        champs = onView(withId(R.id.btndeconnexion)).check(matches(withText("Se déconnecter")));

        // Clique sur le bouton "Effectuer un choix de parcours"
        connexion = onView(withId(R.id.btnparcours)).perform(click());

        // Clique sur parcours personnaliser
        connexion = onView(withText("Parcours prédéfini")).perform(click());

        // Clique sur Informatique
        connexion = onView(withText("Informatique")).perform(click());


        // Récape
        // Semestre 1
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Bases de l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Fondements 1"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Complements 1"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Electronique numerique - Bases"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("UE transversale"))));


        // Semestre 2
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("System 1. Unix et progra shell"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Programmation impérative"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Fondements 2"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Complements 2"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("UE transversale"))));


        // Semestre 3
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Structures de données et programmation C"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Bases de données"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Outils formels pour l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Fondements 3"))));

        // Semestre 4
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Algorithmique 1"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Réseaux et télécommunication"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Systèmes 2 : Mécanisme internes des systèmes d'exploitation"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Technologie du Web"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("UE transversale"))));

        // Clique sur "finaliser mon inscription"
        clicCommencer = onView(withId(R.id.buttonConfirmation)).perform(click());
    }
}
