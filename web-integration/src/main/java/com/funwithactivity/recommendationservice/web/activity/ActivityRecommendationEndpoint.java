package com.funwithactivity.recommendationservice.web.activity;

import com.funwithactivity.recommendationservice.web.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Activity", description = "Activity related services")
public interface ActivityRecommendationEndpoint {

    @Operation(description = "Provides activity recommendations for registered users.",
        parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "request-id", description = "Request correlation id.",
                schema = @Schema(implementation = String.class))
        })
    @ApiResponse(
        responseCode = "200",
        description = "Successful response with the authenticated user's recommended activities.")
    @ApiResponse(
        responseCode = "500", description = "Server side problem.",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping(path = "/activity/recommendation", produces = MediaType.APPLICATION_JSON_VALUE)
    ActivityRecommendation recommendActivity();
}
