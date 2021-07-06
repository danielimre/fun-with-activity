package com.funwithactivity.recommendationservice.activity;

import java.util.List;

import com.funwithactivity.recommendationservice.person.Person;

/**
 * Provides list of recommended activities.
 */
public interface ActivityRecommendationProvider {

    /**
     * Gets list of recommended activities for a given person.
     *
     * @param person the person to retrieve activities for
     * @return the list of recommendations if any
     */
    List<Activity> getRecommendationsFor(Person person);
}
