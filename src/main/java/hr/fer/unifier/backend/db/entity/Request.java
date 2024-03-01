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
@Table(name = "REQUEST")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false)
    private String requestTitle;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String helpType;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer numOfVolunteers;

    @Column
    private String skillSet;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column
    private Boolean active;

    @Column
    private Boolean deleted;

    @Column
    private Boolean oneTime;

    @Lob
    @Column
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Deal> deals;

}
