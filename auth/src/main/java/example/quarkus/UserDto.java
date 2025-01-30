package example.quarkus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotNull
    @Size(min = 3, max = 50)
    public String username;

    @NotNull
    @Size(min = 8)
    public String password;

    @Email
    public String email;
}

