package com.androidapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.androidapp.vue.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class GraphicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void graphicTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.text_view), withText("Système 1 : Unix et progra shell"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        2),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.text_view), withText("Programmation imperative"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        3),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.text_view), withText("Structures de données et programmation C"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        4),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.text_view), withText("Programmation imperative"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        3),
                                0),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.text_view), withText("Structures de données et programmation C"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        4),
                                0),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.text_view), withText("Système 2: mécanismes internes des systèmes d'exploitation"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        9),
                                0),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonValider), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatTextView7 = onView(
                allOf(withId(R.id.text_view), withText("Bases de données"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        5),
                                0),
                        isDisplayed()));
        appCompatTextView7.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.buttonValider), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());
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
