package br.com.controleestoqueapi.users.infrastructure.dto;

import br.com.controleestoqueapi.users.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public static UserDtoResponse fromDomain(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        return dto;
    }
}
