package example.quarkus.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String name;
    private String type;
    private int quantity;
}
