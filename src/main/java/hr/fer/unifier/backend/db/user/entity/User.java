package hr.fer.unifier.backend.db.user.entity;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.enums.VolunteerCenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobilePhone;

    @Column(length = 1000)
    private String profileDescription;

    @Column(nullable = false)
    private String password;

    @Lob
    private byte[] file;

    private boolean blocked = false;

    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private VolunteerCenter volunteerCenter;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Request> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advert> adverts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
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
