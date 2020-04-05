package com.twitter.controller;

import com.twitter.dto.MessageDto;
import com.twitter.service.WallService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wall")
public class WallController {

    private final WallService wallService;

    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @ApiOperation(value = "Endpoint for fetching message of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched messages of user"),
            @ApiResponse(code = 400, message = "User with provided username doesn't exist"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> getUserMessages(@RequestParam("username") String username) {
        return wallService.getUserMessages(username);
    }
}
