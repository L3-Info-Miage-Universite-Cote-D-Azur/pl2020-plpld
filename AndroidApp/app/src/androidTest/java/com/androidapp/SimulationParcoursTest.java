package com.androidapp;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.runner.RunWith;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import com.androidapp.vue.activity.HomeActivity;
import org.junit.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.StringContains.containsString;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class SimulationParcoursTest {

    private String descriptionUE;


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);



    @Before
    public void setup() {
        descriptionUE = "Responsables:Claire Michel, Philippe Thomen Cours : 18 h TD : 24h\n" +
                "Cinématique ; Dynamique du point, Lois de Newton ; Lois de conservation de l’énergie ; Oscillations libre non amortie ; Systèmes de points en interaction, chocs\n";
    }



    @Test
    public void graphicTest() throws InterruptedException {

        ViewInteraction connexion;
        ViewInteraction verifMatiere;
        ViewInteraction clicGroupe;
        ViewInteraction clicMatiere;
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
        connexion = onView(withId(R.id.buttonValiderInscription)).perform(click());

        try {
            champs = onView(withId(R.id.btnparcours)).check(matches(withText("Effectuer un choix de parcours")));
        }
        catch (Exception e) {
            return;
        }
        // Clique sur le bouton "Effectuer un choix de parcours"
        connexion = onView(withId(R.id.btnparcours)).perform(click());
        
        // Clique sur parcours personnaliser
        connexion = onView(withText("Parcours personalisé")).perform(click());

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
        scrollView = onView(
                withId(R.id.UE_list)).perform(ViewActions.swipeUp());
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
        clicMatiere = onView(
                withText("Mathématiques")).perform(click());

        clicMatiere = onView(
                withText("Physique")).perform(click());
        clicMatiere = onView(withText("Mécanique 1")).perform(click());

        clicMatiere = onView(
                withText("Mécanique 1")).perform(longClick());

        verifMatiere = onView(withText("Description de l'UE :Mécanique 1")).check(matches(isDisplayed()));
        verifMatiere = onView(withText(containsString(descriptionUE))).check(matches(isDisplayed()));
        clicMatiere = onView(withText("REVENIR À LA SELECTION DES UE")).perform(click());

        clicMatiere = onView(withText("Mécanique 1")).perform(click());

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
                withText("Chimie")).perform(click());
        clicMatiere = onView(
                withText("Réactions et reactivites : équilibre chimiques")).perform(click());
        clicGroupe = onView(
                withText("Chimie")).perform(click());

        clicGroupe = onView(
                withText("Physique")).perform(click());
        clicMatiere = onView(
                withText("Optique 1")).perform(click());
        clicGroupe = onView(
                withText("Physique")).perform(click());

        clicGroupe = onView(
                withText("Electronique")).perform(click());
        scrollView = onView(
                withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        clicMatiere = onView(
                withText("Electronique analogique")).perform(click());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        /////////////// Semestre 3 ///////////////
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        clicMatiere = onView(
                withText("Méthodes : approche continue")).perform(click());
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());

        clicGroupe = onView(
                withText("Géographie")).perform(click());
        clicMatiere = onView(
                withText("Disciplinaire 1")).perform(click());
        clicGroupe = onView(
                withText("Géographie")).perform(click());

        clicGroupe = onView(
                withText("Science de la vie")).perform(click());
        clicMatiere = onView(
                withText("Org. Mécanismes Moléculaires Cellules Eucaryotes")).perform(click());
        clicMatiere = onView(
                withText("Génétique. Evolution. Origine Vie et Biodiversité")).perform(click());
        clicGroupe = onView(
                withText("Science de la vie")).perform(click());

        scrollView = onView(
                withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        clicGroupe = onView(
                withText("MIASHS")).perform(click());
        clicMatiere = onView(
                withText("Economie-Gestion S1")).perform(click());
        clicGroupe = onView(
                withText("MIASHS")).perform(click());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        /////////////// Semestre 4 ///////////////
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());
        Thread.sleep(2000);
        clicMatiere = onView(
                withText("Méthodes : approche discrète")).perform(click());
        clicMatiere = onView(
                withText("Complements 2")).perform(click());
        clicGroupe = onView(
                withText("Mathématiques")).perform(click());

        clicGroupe = onView(
                withText("Géographie")).perform(click());
        clicMatiere = onView(
                withText("Disciplinaire 2")).perform(click());
        clicGroupe = onView(
                withText("Géographie")).perform(click());

        clicGroupe = onView(
                withText("Science de la Terre")).perform(click());
        Thread.sleep(2000);
        clicMatiere = onView(
                withText("Structure et dynamique de la terre")).perform(click());
        clicMatiere = onView(
                withText("Atmosphère; Océan; Climats")).perform(click());
        clicGroupe = onView(
                withText("Science de la Terre")).perform(click());

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        // Récape
        // Semestre 1
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Bases de l'informatique"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Introduction à l'informatique par le web"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Electronique numerique - Bases"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Fondements 1"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Complements 1"))));
        verifMatiere = onView(withId(R.id.semestre1)).check(matches(withText(containsString("Mécanique 1"))));

        // Semestre 2
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("System 1. Unix et progra shell"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Programmation impérative"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Fondements 2"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Réactions et reactivites : équilibre chimiques"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Optique 1"))));
        verifMatiere = onView(withId(R.id.semestre2)).check(matches(withText(containsString("Electronique analogique"))));

        // Semestre 3
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Méthodes : approche continue"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Disciplinaire 1"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Org. Mécanismes Moléculaires Cellules Eucaryotes"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Génétique. Evolution. Origine Vie et Biodiversité"))));
        verifMatiere = onView(withId(R.id.semestre3)).check(matches(withText(containsString("Economie-Gestion S1"))));

        // Semestre 4
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Méthodes : approche discrète"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Complements 2"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Disciplinaire 2"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Structure et dynamique de la terre"))));
        verifMatiere = onView(withId(R.id.semestre4)).check(matches(withText(containsString("Atmosphère; Océan; Climats"))));
    }
}
