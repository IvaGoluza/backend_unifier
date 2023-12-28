package hr.fer.unifier.backend.model;

import hr.fer.unifier.backend.model.enums.Category;
import hr.fer.unifier.backend.model.enums.HelpType;
import hr.fer.unifier.backend.model.enums.Town;
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
    private Long id;

    @Column(nullable = false)
    private String advertTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Town town;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HelpType helpType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, length = 750)
    private String description;

    @Column
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL)
    private List<Deal> deals;
}
