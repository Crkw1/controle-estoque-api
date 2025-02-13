package br.com.controleestoqueapi.users.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String address;

    /*@ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuer_papers", joinColumns = @JoinColumn(name = "usuer_id"))
    @Column(name = "papers")
    private Set<String> UserType = new HashSet<>();*/
}
