package br.com.controleestoqueapi.users.infrastructure.dto;

import br.com.controleestoqueapi.users.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoRequest {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public User toDomain() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        user.setAddress(this.address);
        return user;
    }
}
