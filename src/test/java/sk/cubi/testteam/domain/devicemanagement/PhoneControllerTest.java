package sk.cubi.testteam.domain.devicemanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhoneControllerTest {

    @MockBean
    private PhoneService service;

    @Autowired
    private MockMvc client;

    @Test
    @DisplayName("given service returns list of phones on getPhones when GET /phones is called then such list is returned")
    void getPhonesReturnsList() throws Exception {
        doReturn(List.of(new Phone().setName("iphone"))).when(service).getPhones();

        client
                .perform(get("/phones").with(user("drwho").password("changeme")))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"iphone\",\"available\":true,\"bookedBy\":null,\"bookedOn\":null}]"));
    }

    @Test
    @DisplayName("given service returns iphone on getPhone(4L) when GET /phones/4 is called then iphone is returned")
    void getPhoneWhenExists() throws Exception {
        doReturn(new Phone().setName("iphone")).when(service).getPhone(4L);

        client
                .perform(get("/phones/4").with(user("drwho").password("changeme")))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"name\":\"iphone\",\"available\":true,\"bookedBy\":null,\"bookedOn\":null}"));
    }

    @Test
    @DisplayName("given service throw PhoneNotFoundException on getPhone(4L) when GET /phones/4 is called then 404 is returned")
    void getPhoneWhenDoesntExist() throws Exception {
        doThrow(new PhoneNotFoundException()).when(service).getPhone(4L);

        client
                .perform(get("/phones/4").with(user("drwho").password("changeme")))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Phone was not found"));
    }

    @Test
    @DisplayName("given service returns available iphone on bookPhone(4L, drwho) when POST /phones/book/4 is called then iphone is returned")
    void bookPhoneWhenAvailableExists() throws Exception {
        doReturn(new Phone().setName("iphone")).when(service).bookPhone(4L, "drwho");

        client
                .perform(post("/phones/book/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"name\":\"iphone\",\"available\":true,\"bookedBy\":null,\"bookedOn\":null}"));
    }

    @Test
    @DisplayName("given service throws PhoneAvailabilityException on bookPhone(4L, drwho) when  POST /phones/book/4 is called then 400 is returned")
    void bookPhoneWhenUnavailableExists() throws Exception {
        doThrow(new PhoneAvailabilityException("Test message")).when(service).bookPhone(4L, "drwho");

        client
                .perform(post("/phones/book/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Test message"));
    }

    @Test
    @DisplayName("given service throws PhoneNotFoundException on fon bookPhone(4L, drwho) when  POST /phones/book/4 is called then 404 is returned")
    void bookPhoneWhenDoesNotExist() throws Exception {
        doThrow(new PhoneNotFoundException()).when(service).bookPhone(4L, "drwho");

        client
                .perform(post("/phones/book/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Phone was not found"));
    }

    @Test
    @DisplayName("given service returns available iphone on returnPhone(4L, drwho) when POST /phones/return/4 is called then iphone is returned")
    void returnPhoneWhenAvailableExists() throws Exception {
        doReturn(new Phone().setName("iphone")).when(service).returnPhone(4L);

        client
                .perform(post("/phones/return/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"name\":\"iphone\",\"available\":true,\"bookedBy\":null,\"bookedOn\":null}"));
    }

    @Test
    @DisplayName("given service throws PhoneAvailabilityException on returnPhone(4L, drwho) when  POST /phones/return/4 is called then 400 is returned")
    void returnPhoneWhenUnavailableExists() throws Exception {
        doThrow(new PhoneAvailabilityException("Test message")).when(service).returnPhone(4L);

        client
                .perform(post("/phones/return/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Test message"));
    }

    @Test
    @DisplayName("given service throws PhoneNotFoundException on returnPhone(4L, drwho) when  POST /phones/return/4 is called then 404 is returned")
    void returnPhoneWhenDoesNotExist() throws Exception {
        doThrow(new PhoneNotFoundException()).when(service).returnPhone(4L);

        client
                .perform(post("/phones/return/4").with(user("drwho").password("changeme")).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Phone was not found"));
    }
}