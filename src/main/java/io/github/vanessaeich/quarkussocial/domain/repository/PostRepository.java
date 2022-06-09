package io.github.vanessaeich.quarkussocial.domain.repository;

import io.github.vanessaeich.quarkussocial.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
