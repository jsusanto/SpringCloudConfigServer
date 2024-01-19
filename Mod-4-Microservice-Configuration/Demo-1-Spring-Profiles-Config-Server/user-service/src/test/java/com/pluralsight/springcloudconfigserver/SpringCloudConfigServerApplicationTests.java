package com.pluralsight.springcloudconfigserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringCloudConfigServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should be able to retrieve a user by username")
    void shouldBeAbleToRetrieveUserByUsername() throws Exception {

        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test-user");
        userEntity.setFirstName("John");
        userEntity.setLastName("Smith");
        userEntity.setEmail("johnsmith@pluralsight.com");
        userRepository.save(userEntity);

        mockMvc.perform(get("/users/test-user"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                   "username": "test-user",
                   "email": "johnsmith@pluralsight.com",
                   "firstName": "John",
                   "lastName": "Smith"
                }
                """));
    }

}
