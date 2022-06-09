package io.github.vanessaeich.quarkussocial.rest.dto;

import io.github.vanessaeich.quarkussocial.domain.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Getter
@Setter
public class PostResponse {

    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post post){
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDateTime(post.getDateTime());

        return response;
    }
}
