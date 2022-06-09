package io.github.vanessaeich.quarkussocial.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Entity
@Table(name = "followers")
@Getter
@Setter
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;
}
