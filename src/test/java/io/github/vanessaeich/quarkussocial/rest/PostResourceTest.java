package io.github.vanessaeich.quarkussocial.rest;

import io.github.vanessaeich.quarkussocial.domain.model.Follower;
import io.github.vanessaeich.quarkussocial.domain.model.Post;
import io.github.vanessaeich.quarkussocial.domain.model.User;
import io.github.vanessaeich.quarkussocial.domain.repository.FollowerRepository;
import io.github.vanessaeich.quarkussocial.domain.repository.PostRepository;
import io.github.vanessaeich.quarkussocial.domain.repository.UserRepository;
import io.github.vanessaeich.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

/**
 * @author Vanessa Eich
 * @created 09/06/2022
 */
@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    @Inject
    FollowerRepository followerRepository;
    @Inject
    PostRepository postRepository;

    Long userId;
    Long userNotFollowerId;
    Long userFollowerId;

    @BeforeEach
    @Transactional
    public void setUP(){
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();

        Post post = new Post();
        post.setText("Hello");
        post.setUser(user);
        postRepository.persist(post);

        var userNotFollower = new User();
        user.setAge(33);
        user.setName("Cicrano");
        userRepository.persist(userNotFollower);
        userNotFollowerId = userNotFollower.getId();

        var userFollower = new User();
        userFollower.setAge(31);
        userFollower.setName("Terceiro");
        userRepository.persist(userFollower);
        userFollowerId = userFollower.getId();

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollower(userFollower);
        followerRepository.persist(follower);
    }

    @Test
    @DisplayName("should create a post for a user")
    public void createPostTest(){
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", userId)
        .when()
                .post()
        .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("should return 404 when trying to make a post for an inexistent user")
    public void postForAnInexistentUserTest(){
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        var inexistentUserID = 999;

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", inexistentUserID)
        .when()
                .post()
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should return 404 when user doesn't exist")
    public void listPostUserNotFoundTest(){
        var inexistentUserId = 99;
        given()
                .pathParam("userId", inexistentUserId)
        .when()
                .get()
        .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should return 404 when followerId header is not present")
    public void listPostFollowerHeaderNotSendTest(){
        given()
                .pathParam("userId", userId)
        .when()
                .get()
        .then()
                .statusCode(400)
                .body(Matchers.is("Você esqueceu de passar o header"));
    }

    @Test
    @DisplayName("should return 404 when follower doesn't exist")
    public void listPostFollowerNotFoundTest(){
        var inexistentFollowerId = 999;

        given()
                .pathParam("userId", userId)
                .header("followerId", inexistentFollowerId)
        .when()
                .get()
        .then()
                .statusCode(400)
                .body(Matchers.is("Follower inexistente"));
    }

    @Test
    @DisplayName("should return 403 when follower isn't a follow")
    public void listPostNotAFollowerTest(){
        given()
                .pathParam("userId", userId)
                .header("followerId", userNotFollowerId)
        .when()
                .get()
        .then()
                .statusCode(403)
                .body(Matchers.is("Somente seguidores podem ver as postagens"));
    }

    @Test
    @DisplayName("should return posts")
    public void listPostsTest(){
        given()
                .pathParam("userId", userId)
                .header("followerId", userFollowerId)
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }


}