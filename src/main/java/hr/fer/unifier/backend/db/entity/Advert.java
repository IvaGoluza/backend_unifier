package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ADVERT")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertId;

    @Column(nullable = false)
    private String advertTitle;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String helpType;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, length = 750)
    private String description;

    @Column(nullable = false)
    private String time;

    @Column
    private Boolean deleted;

    @Lob
    @Column
    private byte[] advertImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Deal> deals;
}
