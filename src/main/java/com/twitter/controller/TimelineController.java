package com.twitter.controller;

import com.twitter.dto.MessageDto;
import com.twitter.service.TimelineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @ApiOperation(value = "Endpoint for fetching message of followed users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched messages of followed users"),
            @ApiResponse(code = 400, message = "User with provided username doesn't exist"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> messages(@RequestParam("username") String username) {
        return timelineService.getFollowedUsersMessages(username);
    }
}
