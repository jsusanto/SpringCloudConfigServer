package com.pluralsight.springcloudconfigserver;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @RequestMapping("/users/{username}")
    public @ResponseBody GetUserResponse makePayment(@PathVariable final String username) {
        return new GetUserResponse(username, "test", "test", "test");
    }
}
