package io.github.vanessaeich.quarkussocial.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="post_text")
    private String text;
    @Column(name="dateTime")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        setDateTime(LocalDateTime.now());
    }
}
