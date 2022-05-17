package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users;

    public Role() {

    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    public Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName.replaceFirst("ROLE_","");
    }
}
