package br.com.controleestoqueapi.users.infrastructure.controller;

import br.com.controleestoqueapi.users.domain.service.UserService;
import br.com.controleestoqueapi.users.infrastructure.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @Transactional
//    void createUser_ComSucesso_RetornaStatusCreated() throws Exception {
//        // Cria um DTO de requisi��o
//        UserRequest userRequest = new UserRequest();
//        userRequest.setName("Teste");
//        userRequest.setEmail("teste@example.com");
//        userRequest.setPassword("senha123");
//        userRequest.setPhoneNumber("123456789");
//        userRequest.setAddress("Rua Teste, 123");
//
//        // Simula a requisi��o POST
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.email").value("teste@example.com")); //Verifica se email foi retornado
//    }

    /*@Test
    void createUser_ComDadosInvalidos_RetornaBadRequest() throws Exception {
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setEmail("teste");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void getUserById_UsuarioExistente_RetornaStatusOk() throws Exception {
        // 1. CRIAR um usu�rio no banco.
        User user = new User();
        user.setName("Nome Para Buscar");
        user.setEmail("buscar@example.com");
        user.setPassword("senha"); // Ser� criptografada
        user.setPhoneNumber("123");
        user.setAddress("Endereco");
        User userCriado = userService.create(user);
        Long idCriado = userCriado.getId();

        // 2. Buscar o usu�rio por ID (usando o ID *real* do banco).
        mockMvc.perform(get("/users/" + idCriado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(idCriado))
                .andExpect(jsonPath("$.name").value("Nome Para Buscar"));

    }

    @Test
    @Transactional
    void getUserById_UsuarioNaoExistente_RetornaNotFound() throws Exception {

        mockMvc.perform(get("/users/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void listUsers_ComSucesso_RetornaListaDeUsuarios() throws Exception {
        // 1. CRIAR usu�rios no banco
        User user1 = new User();
        user1.setName("Usuario 1");
        user1.setEmail("user1@example.com");
        user1.setPassword("senha");
        userService.create(user1);

        User user2 = new User();
        user2.setName("Usuario 2");
        user2.setEmail("user2@example.com");
        user2.setPassword("senha");
        userService.create(user2);

        // 2. Buscar a lista
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    @Transactional
    void listUsers_ListaVazia_RetornaOkComListaVazia() throws Exception {
        //Garante que n�o tem dados no banco.
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    void updateUser_ComSucesso_RetornaStatusOk() throws Exception {
        // 1. *CRIAR* um usu�rio no banco de dados.
        User user = new User();
        user.setName("Nome Inicial");
        user.setEmail("email@inicial.com");
        user.setPassword("senha");
        user.setPhoneNumber("123");
        user.setAddress("Endereco");

        User userCriado = userService.create(user);
        Long idCriado = userCriado.getId();

        // 2. *AGORA* voc� pode atualizar.
        UserRequest userDtoRequest = new UserRequest();
        userDtoRequest.setName("Nome Atualizado");
        userDtoRequest.setEmail("email@atualizado.com");
        userDtoRequest.setPhoneNumber("456");
        userDtoRequest.setAddress("Novo Endereco");


        mockMvc.perform(put("/users/" + idCriado)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(idCriado))
                .andExpect(jsonPath("$.name").value("Nome Atualizado"));

        // 3. (Opcional) Verifique se os dados foram realmente atualizados no banco.
        User userAtualizado = userService.findById(idCriado);
        assertEquals("Nome Atualizado", userAtualizado.getName());
        assertEquals("email@atualizado.com", userAtualizado.getEmail());
    }

    @Test
    @Transactional
    void updateUser_ComDadosInvalidos_RetornaBadRequest() throws Exception {
        //N�o precisa criar o usu�rio antes, pois nem vai chegar no service.
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setEmail("teste");
        invalidRequest.setPassword("senha");
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateUser_UsuarioNaoExistente_RetornaNotFound() throws Exception {
        // N�o cria usu�rio.  Tenta atualizar um ID que n�o existe.
        UserRequest userDtoRequest = new UserRequest();
        userDtoRequest.setName("Nome Atualizado");
        userDtoRequest.setEmail("email@atualizado.com");
        userDtoRequest.setPhoneNumber("456");
        userDtoRequest.setAddress("Novo Endereco");

        mockMvc.perform(put("/users/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDtoRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteUser_ComSucesso_RetornaStatusNoContent() throws Exception {
        // 1. *CRIAR* um usu�rio no banco de dados.
        User user = new User();
        user.setName("Nome Inicial");
        user.setEmail("email@inicial.com");
        user.setPassword("senha");
        user.setPhoneNumber("123");
        user.setAddress("Endereco");

        User userCriado = userService.create(user);
        Long idCriado = userCriado.getId();

        // 2. *Deleta*
        mockMvc.perform(delete("/users/" + idCriado))
                .andExpect(status().isNoContent());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(idCriado);
        });
    }

    @Test
    @Transactional
    void deleteUser_UsuarioNaoExistente_RetornaNotFound() throws Exception {
        mockMvc.perform(delete("/users/99999"))
                .andExpect(status().isNotFound());
    }*/
}