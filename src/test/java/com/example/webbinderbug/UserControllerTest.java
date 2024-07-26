package com.example.webbinderbug;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserController userController;

    @Test
    public void testSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .param("name", "example")
                        .param("$size", "50")
                        .param("$page", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("example|1|50"));

        ArgumentCaptor<SearchCriteria> argumentCaptor = ArgumentCaptor.forClass(SearchCriteria.class);
        Mockito.verify(userController).search(argumentCaptor.capture());
        SearchCriteria capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument.getName()).isEqualTo("example");
        assertThat(capturedArgument.getSize()).isEqualTo(50);
        assertThat(capturedArgument.getPage()).isEqualTo(1);

    }

}