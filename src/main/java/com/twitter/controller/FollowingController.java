package com.twitter.controller;

import com.twitter.dto.FollowingDto;
import com.twitter.service.FollowingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/following")
public class FollowingController {

    private final FollowingService followingService;

    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @ApiOperation(value = "Endpoint for following user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully followed"),
            @ApiResponse(code = 400, message = "Provided dto has validation error, cannot follow user"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void follow(@RequestBody FollowingDto followingDto) {
        followingService.follow(followingDto);
    }

    @ApiOperation(value = "Endpoint for unfollowing user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully unfollowed"),
            @ApiResponse(code = 400, message = "Provided dto has validation error, cannot unfollow user"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void unfollow(@RequestBody FollowingDto followingDto) {
        followingService.unfollow(followingDto);
    }
}
