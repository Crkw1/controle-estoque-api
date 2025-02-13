package br.com.controleestoqueapi.users.domain.service;

import br.com.controleestoqueapi.users.domain.exception.UserInUseException;
import br.com.controleestoqueapi.users.domain.exception.UserNotFoundException;
import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserInUseException("Já existe um usuário com o email: " + user.getEmail());
        }
        //user.setPassword(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User userAtualizado) {
        User usuarioExistente = findById(id);
        usuarioExistente.setName(userAtualizado.getName());
        usuarioExistente.setEmail(userAtualizado.getEmail());
        usuarioExistente.setPhoneNumber(userAtualizado.getPhoneNumber());
        usuarioExistente.setAddress(userAtualizado.getAddress());
        // A senha normalmente NÃO deve ser atualizada aqui. Crie um método separado para isso.
        return userRepository.save(usuarioExistente);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}