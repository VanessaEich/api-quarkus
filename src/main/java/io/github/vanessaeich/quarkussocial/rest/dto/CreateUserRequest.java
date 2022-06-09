package io.github.vanessaeich.quarkussocial.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Vanessa Eich
 * @created 07/06/2022
 */
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Campo nome é obrigatório")
    private String name;
    @NotNull(message = "Campo idade é obrigatório")
    private Integer age;

}
