package com.funwithactivity.recommendationservice.web.activity;

import java.util.stream.Collectors;

import com.funwithactivity.recommendationservice.activity.ActivityRecommendationService;
import com.funwithactivity.recommendationservice.person.Person;
import com.funwithactivity.recommendationservice.web.user.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ActivityRecommendationController implements ActivityRecommendationEndpoint {
    @NonNull
    private final ActivityRecommendationService activityRecommendationService;

    @Override
    public ActivityRecommendation recommendActivity() {
        Person currentUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getDetails();
        return new ActivityRecommendation(activityRecommendationService.recommendActivitiesFor(currentUser)
            .stream()
            .map(a -> a.getSummary() + a.getDetails().map(d -> " - " + d).orElse(""))
            .collect(Collectors.toList()));
    }

}
