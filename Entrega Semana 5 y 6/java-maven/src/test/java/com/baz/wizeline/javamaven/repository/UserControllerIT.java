package com.baz.wizeline.javamaven.repository;

import com.baz.wizeline.javamaven.model.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerIT {
    //Contiene los tests de las operaciones CRUD
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersRepository userRepository;

    @BeforeAll
    public void setup() {

        userRepository.save(Users.builder().userId(String.valueOf(1L)).name("James").user("Bond123").build());
        userRepository.save(Users.builder().userId(String.valueOf(2L)).name("James").user("Farley3456").build());
        userRepository.save(Users.builder().userId(String.valueOf(3L)).name("Marley").user("Hemp6789").build());
        userRepository.save(Users.builder().userId(String.valueOf(4L)).name("James").user("Bond234").build());

    }

    @Test
    public void test_obtener_usuariosporId_exitoso() throws Exception {
        mvc.perform(get("/").param("id", "1")).andExpect(status().isOk()).andExpect(content().string("{\"id\":1,\"name\":\"James\",\"user\":\"Bond123\"}"));
        mvc.perform(get("/").param("id", "2")).andExpect(status().isOk()).andExpect(content().string("{\"id\":2,\"name\":\"James\",\"user\":\"Farley3456\"}"));
        mvc.perform(get("/").param("id", "3")).andExpect(status().isOk()).andExpect(content().string("{\"id\":3,\"name\":\"Marley\",\"user\":\"Hemp6789\"}"));
        mvc.perform(get("/").param("id", "4")).andExpect(status().isOk()).andExpect(content().string("{\"id\":4,\"name\":\"James\",\"user\":\"Bond234\"}"));
    }

    @Test
    public void test_obtener_usuariosporNombre_exitoso() throws Exception {

        mvc.perform(get("/").param("name", "James")).andExpect(status().isOk()).andExpect(content().string("[{\"id\":1,\"name\":\"James\",\"user\":\"Bond123\"},{\"id\":2,\"firstName\":\"James\",\"lastName\":\"Farley\"},{\"id\":4,\"firstName\":\"James\",\"lastName\":\"Bond\"}]"));

        mvc.perform(get("/").param("name", "Marley")).andExpect(status().isOk()).andExpect(content().string("[{\"id\":3,\"name\":\"Marley\",\"user\":\"Farley3456\"}]"));
    }
}