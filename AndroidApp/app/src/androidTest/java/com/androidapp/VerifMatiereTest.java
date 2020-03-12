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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)


/////////// Ce test vérifie que toutes les UE pour chaque semestres se charge correctement ///////////

public class VerifMatiereTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);
    

    @Test
    public void graphicTest() throws InterruptedException {

        String[] strArray = {"MIASHS", "Géographie", "Science de la vie", "Informatique","Mathématiques",
                "Chimie", "Science de la Terre", "CLE 1D (Continuum Licence Enseignement)",
                "Physique", "UE facultative", "Electronique"};

        String[] InformatiqueS1 = {"Bases de l'informatique", "Introduction à l'informatique par le web"};
        String[] MathS1 = { "Fondements 1", "Méthodes : approche continue", "Complements 1"};
        String[] ChimieS1 = {"Structure Microscopique de la Matière"};
        String[] ElectroniqueS1 = { "Electronique numerique - Bases"};
        String[] GeographieS1 = { "Decouverte 1" ,"Decouverte 2", "Disciplinaire 1"};
        String[] MIASHSS1 = { "Economie-Gestion S1"};
        String[] PhysiqueS1 = {"Mécanique 1"};
        String[] SDTS1 = { "Découverte des sciences de la terre"};
        String[] SDVS1 = { "Org. Mécanismes Moléculaires Cellules Eucaryotes", "Génétique. Evolution. Origine Vie et Biodiversité"};
        String[] CLES1 = { "Enseignements fondamentaux à l'école primaire 1"};


        String[] InformatiqueS2 = {"System 1. Unix et progra shell", "Programmation impérative"};
        String[] MathS2 = { "Fondements 2", "Méthodes : approche discrète", "Complements 2"};
        String[] ChimieS2 = {"Réactions et reactivites chimiques ","Thermodynamique chimique / Options"};
        String[] ElectroniqueS2 = { "Electronique analogique", "Communication sans fil"};
        String[] GeographieS2 = { "Découverte 3" ,"Découverte 4", "Disciplinaire 2"};
        String[] MIASHSS2 = { "Economie-Gestion S2"};
        String[] PhysiqueS2 = { "Optique 1", "Mécanique 2"};
        String[] SDTS2 = { "Structure et dynamique de la terre", "Atmosphère, Océan, Climats"};
        String[] SDVS2 = { "Physiologie. Neurologie. Enzymologie. Methodologie", "Diversité du Vivant"};
        String[] CLES2 = { "Enseignements fondamentaux à l'école primaire 2", "Méthodologie du concours et didactique - Géométrie", "Méthodologie du concours et didactique - Physique-Chimie"};
        String[] FacultativeS2 = { "Projet FabLab"};


        String[] InformatiqueS3 = {"Bases de l'informatique", "Introduction à l'informatique par le web", "Structures de données et programmation C", "Bases de donnée", "Outils formels pour l'informatique"};
        String[] MathS3 = { "Fondements 1", "Méthodes : approche continue", "Complements 1", "Fondements 3", "Compléments d'Analyse", "Compléments d'Algèbre", "Méthodes : Mathématiques et ingénierie", "Méthodes : approche géométrique"};
        String[] ChimieS3 = {"Structure Microscopique de la Matière", "Chimie des solutions", "Chimie organique", "Matériaux 1"};
        String[] ElectroniqueS3 = { "Electronique numerique - Bases", "Automatique : une introduction", "Système embarqué", "Physique des capteurs"};
        String[] GeographieS3 = { "Decouverte 1" ,"Decouverte 2", "Disciplinaire 1", "Disciplinaire 3", "Disciplinaire 4", "Disciplinaire 5", "Approfondissement hors géographie 1"};
        String[] MIASHSS3 = { "Economie-Gestion S1", "Economie-Gestion S3", "Intro R"};
        String[] PhysiqueS3 = {"Mécanique 1", "Electromagnétisme 1", "Thermodynamique 1", "Outils et Méthodes 1"};
        String[] SDTS3 = { "Découverte des sciences de la terre", "Atmosphère, Océan, Climats", "Le temps en Géosciences", "Physique de la Terre", "Matériaux terrestres"};
        String[] SDVS3 = { "Org. Mécanismes Moléculaires Cellules Eucaryotes", "Génétique. Evolution. Origine Vie et Biodiversité"};
        String[] CLES3 = { "Enseignements fondamentaux à l'école primaire 1"};
        String[] FacultativeS3 = { "Projet FabLab"};


        String[] InformatiqueS4 = {"System 1. Unix et progra shell", "Programmation impérative", "Algorithmique 1", "Réseaux et télécommunication", "Systèmes 2 : Mécanisme internes des systèmes d'exploitation", "Introduction aux systèmes intelligents", "Technologie du Web"};
        String[] MathS4 = { "Fondements 2", "Méthodes : approche discrète", "Complements 2", "Analyse", "Probabilités et Introduction à la Statistiques", "Algèbre", " Résolution numérique des systèmes d'équations linéaires et non-linéaires", "Méthodes : approche aléatoire"};
        String[] ChimieS4 = {"Reactions et reactivites chimiques", "Thermodynamique chimique / Options", "Vision macroscopique des molécules", "Matériaux 2", "Chimie Organique Fonctionnelle 2", "Bloc de Chimie Expérimentale"};
        String[] ElectroniqueS4 = { "Electronique analogique", "Communication sans fil", "Système optimisé en énergie", "Electronique analogique avancée", "Architecture des processeurs", "Systèmes embarqués 2"};
        String[] GeographieS4 = { "Découverte 3" ,"Découverte 4", "Disciplinaire 2", "Disciplinaire 6", "Disciplinaire 7", "Disciplinaire 8", "Approfondissement hors géographie 2"};
        String[] MIASHSS4 = { "Economie-Gestion S2", "Economie-Gestion S4", "Mathématiques pour la finance"};
        String[] PhysiqueS4 = { "Optique 1", "Mécanique 2", "Electromagnétisme 2", "Ondes", "Mathématiques pour la Physique 1", "Mécanique 3", "Outils et Méthodes 2"};
        String[] SDTS4 = { "Structure et dynamique de la terre", "Atmosphère, Océan, Climats", "Géologie Structurale et Tectonique", "Formation et Evolution des Bassins Sédimentaires", "Géomécanique", "Du paysage à la carte"};
        String[] SDVS4 = { "Physiologie. Neurologie. Enzymologie. Methodologie", "Diversite du Vivant"};
        String[] CLES4 = { "Enseignements fondamentaux à l'école primaire 2", "Méthodologie du concours et didactique - Géométrie", "Méthodologie du concours et didactique - Physique-Chimie", "Outils et Méthodes 2D"};
        String[] UEProS4 = { "Management de projet"};
        String[] FacultativeS4 = { "Projet FabLab"};

        ViewInteraction clicCommencer = onView(
                withId(R.id.btnpar)).perform(click());

        ViewInteraction clicGroupe;
        ViewInteraction verifMatiere;
        ViewInteraction scrollView;


        ////////// Semestre 1 //////////
        for (String s : strArray) {
            if (s == "UE facultative"){ break;}

            clicGroupe = onView(
                    withText(s)).perform(click());

            switch (s) {
                case "Informatique":
                    for (String matiere : InformatiqueS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Mathématiques":
                    for (String matiere : MathS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Chimie":
                    for (String matiere : ChimieS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Electronique":
                    for (String matiere : ElectroniqueS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Géographie":
                    for (String matiere : GeographieS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "MIASHS":
                    for (String matiere : MIASHSS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Physique":
                    for (String matiere : PhysiqueS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la Terre":
                    for (String matiere : SDTS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la vie":
                    for (String matiere : SDVS1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "CLE 1D (Continuum Licence Enseignement)":
                    for (String matiere : CLES1) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                default:
                    verifMatiere = onView(
                            withText("erreur")).check(matches(isDisplayed()));
            }
            clicGroupe = onView(
                    withText(s)).perform(click());
        }

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        ////////// Semestre 2 //////////
        for (String s : strArray) {

            clicGroupe = onView(
                    withText(s)).perform(click());

            switch (s) {
                case "Informatique":
                    for (String matiere : InformatiqueS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Mathématiques":
                    for (String matiere : MathS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Chimie":
                    for (String matiere : ChimieS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Electronique":
                    for (String matiere : ElectroniqueS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Géographie":
                    for (String matiere : GeographieS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "MIASHS":
                    for (String matiere : MIASHSS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Physique":
                    for (String matiere : PhysiqueS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la Terre":
                    for (String matiere : SDTS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la vie":
                    for (String matiere : SDVS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "CLE 1D (Continuum Licence Enseignement)":
                    for (String matiere : CLES2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }

                    break;
                case "UE facultative" :
                    for (String matiere : FacultativeS2) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                default:
                    verifMatiere = onView(
                            withText("erreur")).check(matches(isDisplayed()));
            }
            clicGroupe = onView(
                    withText(s)).perform(click());
            scrollView = onView(
                    withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        }

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        ////////// Semestre 3 //////////
        for (String s : strArray) {

            clicGroupe = onView(
                    withText(s)).perform(click());

            switch (s) {
                case "Informatique":
                    for (String matiere : InformatiqueS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Mathématiques":
                    for (String matiere : MathS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Chimie":
                    for (String matiere : ChimieS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Electronique":
                    for (String matiere : ElectroniqueS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Géographie":
                    for (String matiere : GeographieS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "MIASHS":
                    for (String matiere : MIASHSS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Physique":
                    for (String matiere : PhysiqueS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la Terre":
                    for (String matiere : SDTS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la vie":
                    for (String matiere : SDVS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "CLE 1D (Continuum Licence Enseignement)":
                    for (String matiere : CLES3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "UE facultative" :
                    for (String matiere : FacultativeS3) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                default:
                    verifMatiere = onView(
                            withText("erreur")).check(matches(isDisplayed()));
            }
            clicGroupe = onView(
                    withText(s)).perform(click());
            scrollView = onView(
                    withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        }

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());


        ////////// Semestre 4 //////////
        for (String s : strArray) {

            clicGroupe = onView(
                    withText(s)).perform(click());

            switch (s) {
                case "Informatique":
                    for (String matiere : InformatiqueS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Mathématiques":
                    for (String matiere : MathS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Chimie":
                    for (String matiere : ChimieS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Electronique":
                    for (String matiere : ElectroniqueS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Géographie":
                    for (String matiere : GeographieS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "MIASHS":
                    for (String matiere : MIASHSS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Physique":
                    for (String matiere : PhysiqueS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la Terre":
                    for (String matiere : SDTS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "Science de la vie":
                    for (String matiere : SDVS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "CLE 1D (Continuum Licence Enseignement)":
                    for (String matiere : CLES4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                case "UE facultative" :
                    for (String matiere : FacultativeS4) {
                        verifMatiere = onView(
                                withText(matiere)).check(matches(isDisplayed()));
                    }
                    break;
                default:
                    verifMatiere = onView(
                            withText("erreur")).check(matches(isDisplayed()));
            }
            clicGroupe = onView(
                    withText(s)).perform(click());
            scrollView = onView(
                    withId(R.id.UE_list)).perform(ViewActions.swipeUp());
        }

        clicCommencer = onView(
                withId(R.id.buttonValider)).perform(click());
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
