package example.quarkus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDto {
    @NotNull
    @Size(min = 3, max = 50)
    public String username;

    @NotNull
    @Size(min = 8, max = 50)
    public String password;
}
