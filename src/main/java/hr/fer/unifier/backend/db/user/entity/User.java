package hr.fer.unifier.backend.db.user.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

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

    @Type(StringArrayType.class)
    @Column(columnDefinition = "text[]")
    private String[] workArea;

    @Lob
    private byte[] certificateOfGoodConduct;

    @Lob
    private byte[] image;

    private boolean blocked = false;

    private boolean isApproved;

    private String volunteerCenter;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Request> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advert> adverts;

    @ManyToMany
    private Set<Advert> subAdverts;
}
