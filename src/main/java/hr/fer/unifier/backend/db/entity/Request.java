package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Category;
import hr.fer.unifier.backend.enums.HelpType;
import hr.fer.unifier.backend.enums.Town;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
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
@Table(name = "REQUEST")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean association;

    @Column(nullable = false)
    private String requestTitle;

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

    @Column(nullable = false)
    private Integer volunteerNum;

    @Column
    private boolean active;

    @Column
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Deal> deals;

}
