package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "GALLERY")
public class Gallery {
    @Id
    @Column(name = "GALLERY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Lob
    @Column(name = "IMAGE")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
