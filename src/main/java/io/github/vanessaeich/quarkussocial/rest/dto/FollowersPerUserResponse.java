package io.github.vanessaeich.quarkussocial.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Getter
@Setter
public class FollowersPerUserResponse {

    private Integer followersCount;
    private List<FollowerResponse> content;
}
