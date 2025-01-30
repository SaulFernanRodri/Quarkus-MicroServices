package example.quarkus;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateMail {
    private String message;
    private List<String> names;
}