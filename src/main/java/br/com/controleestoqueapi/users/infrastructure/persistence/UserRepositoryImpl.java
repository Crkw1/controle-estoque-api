package br.com.controleestoqueapi.users.infrastructure.persistence;

import br.com.controleestoqueapi.users.domain.model.User;
import br.com.controleestoqueapi.users.domain.model.UserId;
import br.com.controleestoqueapi.users.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Interface JPA (Spring Data JPA) - *Não* é no domínio!
interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;


    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saveEntity = jpaRepository.save(entity);
        return toDomain(saveEntity);

    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.value()).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(UserId id) {
        jpaRepository.deleteById(id.value());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }


    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    //Converte User(Dominio) para UserEntity(JPA)
    private UserEntity toEntity(User user){
        UserEntity entity = new UserEntity();

        //Se o id for diferente de nulo, é um update e vamos buscar a entity no banco e setar os novos valores.
        if(user.getId() != null){
            entity = jpaRepository.findById(user.getId().value()).orElse(new UserEntity());
        }
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setAddress(user.getAddress());
        entity.setRoles(user.getRoles()); // Copia os papéis

        return entity;
    }
    //Converte UserEntity(JPA) para User(Dominio)
    private User toDomain(UserEntity entity){
        //Cria um novo User com os dados da entity
        return new User(new UserId(entity.getId()), entity.getName(), entity.getEmail(), entity.getPassword(),
                entity.getPhoneNumber(), entity.getAddress(), entity.getRoles());
    }
}