package com.clearpicture.platform.product.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * UserRequest
 * Created by Buddhi on 11/28/18.
 */
@Data
public class UserCreateRequest {

    @NotBlank(message = "clientCreateRequest.email.empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "clientCreateRequest.password.empty")
    @Size(min=8, max = 12, message="Password should have atleast 8 characters")
    @Pattern(
            regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[$@£!%*#?&])[A-Za-z0-9$@£!%*#?&]{8,}$",
            message="Password must contains one lowercase character, one uppercase character, " +
                    "one special symbols and length at least 8 characters and maximum of 12"
    )
    private String password;

    @NotBlank(message = "clientCreateRequest.name.empty")
    @Length(max = 100, message = "userCreateRequest.name.lengthExceeds")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Name can contain letters and numbers only")
    private String name;

    private String status;


}
