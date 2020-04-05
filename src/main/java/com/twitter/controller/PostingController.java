package com.twitter.controller;

import com.twitter.dto.PostDto;
import com.twitter.service.PostingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posting")
public class PostingController {

    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @ApiOperation(value = "Creating message")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created message and user if didn't exist"),
            @ApiResponse(code = 400, message = "Validation error of postDto, cannot create message"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void message(@RequestBody PostDto postDto) {
        postingService.createMessage(postDto);
    }
}
