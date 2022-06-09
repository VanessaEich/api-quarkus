package io.github.vanessaeich.quarkussocial.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Vanessa Eich
 * @created 07/06/2022
 */
@Entity
@Table(name="users")
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;
}
