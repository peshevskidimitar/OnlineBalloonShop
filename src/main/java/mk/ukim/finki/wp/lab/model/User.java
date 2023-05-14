package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.converters.UserFullNameConverter;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "shop_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Convert(converter =  UserFullNameConverter.class)
    private UserFullName fullName;
    private String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private Boolean isAccountNonExpired = true;
    private Boolean isAccountNonLocked = true;
    private Boolean isCredentialsNonExpired = true;
    private Boolean isEnabled = true;
    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> carts;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String username, String name, String surname, String password, LocalDate dateOfBirth, Role role) {
        this.username = username;
        fullName = new UserFullName(name, surname);
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        carts = new ArrayList<>();
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
