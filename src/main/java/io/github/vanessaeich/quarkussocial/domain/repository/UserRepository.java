package io.github.vanessaeich.quarkussocial.domain.repository;

import io.github.vanessaeich.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Vanessa Eich
 * @created 07/06/2022
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
