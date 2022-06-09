package io.github.vanessaeich.quarkussocial.rest.dto;

import io.github.vanessaeich.quarkussocial.domain.model.Follower;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Getter
@Setter
public class FollowerResponse {

    private Long id;
    private String name;

    public FollowerResponse() {
    }

    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getName());
    }

    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
