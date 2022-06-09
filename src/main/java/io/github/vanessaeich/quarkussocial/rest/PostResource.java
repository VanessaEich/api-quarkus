package io.github.vanessaeich.quarkussocial.rest;

import io.github.vanessaeich.quarkussocial.domain.model.Post;
import io.github.vanessaeich.quarkussocial.domain.model.User;
import io.github.vanessaeich.quarkussocial.domain.repository.FollowerRepository;
import io.github.vanessaeich.quarkussocial.domain.repository.PostRepository;
import io.github.vanessaeich.quarkussocial.domain.repository.UserRepository;
import io.github.vanessaeich.quarkussocial.rest.dto.CreatePostRequest;
import io.github.vanessaeich.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vanessa Eich
 * @created 08/06/2022
 */
@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;
    private PostRepository repository;
    private FollowerRepository followerRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository, FollowerRepository followerRepository){

        this.userRepository = userRepository;
        this.repository = repository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);
        repository.persist(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts( @PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId ){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(followerId == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Você esqueceu de passar o header").build();
        }

        User follower = userRepository.findById(followerId);
        if(follower == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Follower inexistente").build();
        }


        boolean follows = followerRepository.follows(follower, user);

        if(!follows){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Somente seguidores podem ver as postagens").build();
        }

        var query = repository.find("user",
                Sort.by("dateTime", Sort.Direction.Descending) , user);

        var list = query.list();
        var postResponseList = list.stream()
                //.map(post -> PostResponse.fromEntity(post))
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());
        return Response.ok(postResponseList).build();
    }

}
