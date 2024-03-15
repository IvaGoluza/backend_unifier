package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADVERT")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADVERT_ID")
    private Long advertId;

    @Column(name = "ADVERT_TITLE",nullable = false)
    private String advertTitle;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column(name = "HELP_TYPE", nullable = false)
    private String helpType;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "DESCRIPTION", nullable = false, length = 750)
    private String description;

    @Column(name = "TIME", nullable = false)
    private String time;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Lob
    @Column(name = "ADVERT_IMAGE")
    private byte[] advertImage;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Deal> deals;

    @ManyToMany
    @JoinTable(
            name = "VOLUNTEER_HELPERS",
            joinColumns = @JoinColumn(name = "ADVERT_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private Set<User> helperVolunteers;
}
