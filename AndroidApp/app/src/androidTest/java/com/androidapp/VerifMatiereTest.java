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

import java.util.ArrayList;

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

        final String[] InformatiqueS1 = {"Bases de l'informatique", "Introduction à l'informatique par le web"};
        final String[] MathS1 = { "Fondements 1", "Méthodes : approche continue", "Complements 1"};
        final String[] ChimieS1 = {"Structure Microscopique de la Matière"};
        final String[] ElectroniqueS1 = { "Electronique numerique - Bases"};
        final String[] GeographieS1 = { "Decouverte 1" ,"Decouverte 2", "Disciplinaire 1"};
        final String[] MIASHSS1 = { "Economie-Gestion S1"};
        final String[] PhysiqueS1 = {"Mécanique 1"};
        final String[] SDTS1 = { "Découverte des sciences de la terre"};
        final String[] SDVS1 = { "Org. Mécanismes Moléculaires Cellules Eucaryotes", "Génétique. Evolution. Origine Vie et Biodiversité"};
        final String[] CLES1 = { "Enseignements fondamentaux à l'école primaire 1"};


        final String[] InformatiqueS2 = {"System 1. Unix et progra shell", "Programmation impérative"};
        final String[] MathS2 = { "Fondements 2", "Méthodes : approche discrète", "Complements 2"};
        final String[] ChimieS2 = {"Réactions et reactivites chimiques","Thermodynamique chimique / Options"};
        final String[] ElectroniqueS2 = { "Electronique analogique", "Communication sans fil"};
        final String[] GeographieS2 = { "Découverte 3" ,"Découverte 4", "Disciplinaire 2"};
        final String[] MIASHSS2 = { "Economie-Gestion S2"};
        final String[] PhysiqueS2 = { "Optique 1", "Mécanique 2"};
        final String[] SDTS2 = { "Structure et dynamique de la terre", "Atmosphère; Océan; Climats"};
        final String[] SDVS2 = { "Physiologie. Neurologie. Enzymologie. Methodologie", "Diversité du Vivant"};
        final String[] CLES2 = { "Enseignements fondamentaux à l'école primaire 2", "Méthodologie du concours et didactique - Géométrie", "Méthodologie du concours et didactique - Physique-Chimie"};
        final String[] FacultativeS2 = { "Projet FabLab"};


        final String[] InformatiqueS3 = {"Bases de l'informatique", "Introduction à l'informatique par le web", "Structures de données et programmation C", "Bases de donnée", "Outils formels pour l'informatique"};
        final String[] MathS3 = { "Fondements 1", "Méthodes : approche continue", "Complements 1", "Fondements 3", "Compléments d'Analyse", "Compléments d'Algèbre", "Méthodes : Mathématiques et ingénierie", "Méthodes : approche géométrique"};
        final String[] ChimieS3 = {"Structure Microscopique de la Matière", "Chimie des solutions", "Chimie organique", "Matériaux 1"};
        final String[] ElectroniqueS3 = { "Electronique numerique - Bases", "Automatique : une introduction", "Système embarqué", "Physique des capteurs"};
        final String[] GeographieS3 = { "Decouverte 1" ,"Decouverte 2", "Disciplinaire 1", "Disciplinaire 3", "Disciplinaire 4", "Disciplinaire 5", "Approfondissement hors géographie 1"};
        final String[] MIASHSS3 = { "Economie-Gestion S1", "Economie-Gestion S3", "Intro R"};
        final String[] PhysiqueS3 = {"Mécanique 1", "Electromagnétisme 1", "Thermodynamique 1", "Outils et Méthodes 1"};
        final String[] SDTS3 = { "Découverte des sciences de la terre", "Atmosphère; Océan; Climats", "Le temps en Géosciences", "Physique de la Terre", "Matériaux terrestres"};
        final String[] SDVS3 = { "Org. Mécanismes Moléculaires Cellules Eucaryotes", "Génétique. Evolution. Origine Vie et Biodiversité"};
        final String[] CLES3 = { "Enseignements fondamentaux à l'école primaire 1"};
        final String[] FacultativeS3 = { "Projet FabLab"};


        final String[] InformatiqueS4 = {"System 1. Unix et progra shell", "Programmation impérative", "Algorithmique 1", "Réseaux et télécommunication", "Systèmes 2 : Mécanisme internes des systèmes d'exploitation", "Introduction aux systèmes intelligents", "Technologie du Web"};
        final String[] MathS4 = { "Fondements 2", "Méthodes : approche discrète", "Complements 2", "Analyse", "Probabilités et Introduction à la Statistiques", "Algèbre", "Résolution numérique des systèmes d'équations linéaires et non-linéaires", "Méthodes : approche aléatoire"};
        final String[] ChimieS4 = {"Reactions et reactivites chimiques", "Thermodynamique chimique / Options", "Vision macroscopique des molécules", "Matériaux 2", "Chimie Organique Fonctionnelle 2", "Bloc de Chimie Expérimentale"};
        final String[] ElectroniqueS4 = { "Electronique analogique", "Communication sans fil", "Système optimisé en énergie", "Electronique analogique avancée", "Architecture des processeurs", "Systèmes embarqués 2"};
        final String[] GeographieS4 = { "Découverte 3" ,"Découverte 4", "Disciplinaire 2", "Disciplinaire 6", "Disciplinaire 7", "Disciplinaire 8", "Approfondissement hors géographie 2"};
        final String[] MIASHSS4 = { "Economie-Gestion S2", "Economie-Gestion S4", "Mathématiques pour la finance"};
        final String[] PhysiqueS4 = { "Optique 1", "Mécanique 2", "Electromagnétisme 2", "Ondes", "Mathématiques pour la Physique 1", "Mécanique 3", "Outils et Méthodes 2"};
        final String[] SDTS4 = { "Structure et dynamique de la terre", "Atmosphère; Océan; Climats", "Géologie Structurale et Tectonique", "Formation et Evolution des Bassins Sédimentaires", "Géomécanique", "Du paysage à la carte"};
        final String[] SDVS4 = { "Physiologie. Neurologie. Enzymologie. Methodologie", "Diversite du Vivant"};
        final String[] CLES4 = { "Enseignements fondamentaux à l'école primaire 2", "Méthodologie du concours et didactique - Géométrie", "Méthodologie du concours et didactique - Physique-Chimie", "Outils et Méthodes 2D"};
        final String[] FacultativeS4 = { "Projet FabLab"};



        final ArrayList<String[]> listeMatiereS1 = new ArrayList<String[]>() {{
            add(MIASHSS1);
            add(GeographieS1);
            add(SDVS1);
            add(InformatiqueS1);
            add(MathS1);
            add(ChimieS1);
            add(SDTS1);
            add(CLES1);
            add(PhysiqueS1);
            add(ElectroniqueS1);
        }};

        final ArrayList<String[]> listeMatiereS2 = new ArrayList<String[]>() {{
            add(MIASHSS2);
            add(GeographieS2);
            add(SDVS2);
            add(InformatiqueS2);
            add(MathS2);
            add(ChimieS2);
            add(SDTS2);
            add(CLES2);
            add(PhysiqueS2);
            add(FacultativeS2);
            add(ElectroniqueS2);
        }};

        final ArrayList<String[]> listeMatiereS3 = new ArrayList<String[]>() {{
            add(MIASHSS3);
            add(GeographieS3);
            add(SDVS3);
            add(InformatiqueS3);
            add(MathS3);
            add(ChimieS3);
            add(SDTS3);
            add(CLES3);
            add(PhysiqueS3);
            add(FacultativeS3);
            add(ElectroniqueS3);
        }};

        final ArrayList<String[]> listeMatiereS4 = new ArrayList<String[]>() {{
            add(MIASHSS4);
            add(GeographieS4);
            add(SDVS4);
            add(InformatiqueS4);
            add(MathS4);
            add(ChimieS4);
            add(SDTS4);
            add(CLES4);
            add(PhysiqueS4);
            add(FacultativeS4);
            add(ElectroniqueS4);
        }};
        
        ArrayList<ArrayList<String[]>> listematiere = new ArrayList<ArrayList<String[]>>() {{
            add(listeMatiereS1);
            add(listeMatiereS2);
            add(listeMatiereS3);
            add(listeMatiereS4);
        }};

        ViewInteraction clicCommencer = onView(
                withId(R.id.btnpar)).perform(click());

        ViewInteraction clicGroupe;
        ViewInteraction clicMatiere;
        ViewInteraction verifMatiere;
        ViewInteraction scrollView;


        int i = 0;
        int NbMatiereChoisi = 0;
        for (ArrayList<String[]> semestre : listematiere) {

            for (String[] liste : semestre) {

                clicGroupe = onView(
                        withText(strArray[i])).perform(click());

                for (String s : liste) {

                    if (i == 9 && semestre == listeMatiereS1) { break; }
                    verifMatiere = onView(
                            withText(s)).check(matches(isDisplayed()));
                    if (NbMatiereChoisi < 3) {
                        clicMatiere = onView(
                                withText(s)).perform(click());
                        NbMatiereChoisi++;
                    }
                }
                clicGroupe = onView(
                        withText(strArray[i])).perform(click());
                scrollView = onView(
                        withId(R.id.UE_list)).perform(ViewActions.swipeUp());
                i++;
            }
            i = 0;
            NbMatiereChoisi = 0;
            clicCommencer = onView(
                    withId(R.id.buttonValider)).perform(click());
        }
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
