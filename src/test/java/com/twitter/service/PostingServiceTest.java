package com.twitter.service;


import com.twitter.dto.PostDto;
import com.twitter.exception.MessageValidationError;
import com.twitter.exception.UserException;
import com.twitter.repository.MessageRepository;
import com.twitter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostingServiceTest {

    public static final String StringWith141Characters = "xvOTf0OBDk9DNunOKQ4FOcPn2iusmuyfOQREw74yclVJ3Kpn521yqV8NHUa6uCEmxEWgn0kK8QzT02s19puprBEI3y49JYSGQyQBkEzFBCI58liqlv4h0CWvArwPw2t4dUyjbw1E09LI4";
    public static final String TEST = "TEST";
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PostingService postingService;

    @Test
    public void shouldThrowErrorForEmptyMessage() {
        Assertions.assertThrows(MessageValidationError.class,
                () -> postingService.createMessage(new PostDto()),
                "Message cannot be empty");
    }
    @Test
    public void shouldThrowErrorForTooLongMessage() {
        PostDto postDto = new PostDto();
        postDto.setText(StringWith141Characters);
        Assertions.assertThrows(MessageValidationError.class,
                () -> postingService.createMessage(postDto),
                "Message is too long");
    }
    @Test
    public void shouldThrowErrorForEmtpyUsername() {
        PostDto postDto = new PostDto();
        postDto.setText(TEST);
        Assertions.assertThrows(UserException.class,
                () -> postingService.createMessage(postDto),
                "Username not valid");
    }

    @Test
    public void shouldPassForValidData() {
        PostDto postDto = new PostDto();
        postDto.setText(TEST);
        postDto.setUsername(TEST);
        Assertions.assertDoesNotThrow(() -> postingService.createMessage(postDto));
    }

}
