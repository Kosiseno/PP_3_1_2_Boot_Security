package ru.kata.spring.boot_security.demo.model;




import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "name")
    @Size(min = 2,max = 30, message = "Имя должно быть от 2 до 30 символов")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2,max = 30, message = "Фамилия должно быть от 2 до 30 символов")
    private String lastName;

    @Min(value = 0, message = "Возраст должен быть больше чем ноль")
    @Max(value = 120, message = "Возраст должен быть меньше чем 120")
    @Column(name = "age")
    private byte age;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;





    public User(String firstName, String lastName, byte age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
