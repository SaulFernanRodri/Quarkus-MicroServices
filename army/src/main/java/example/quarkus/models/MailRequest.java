package example.quarkus.models;

import lombok.Data;

import java.util.List;

@Data
public class MailRequest {
    private List<String> names;
    private String article;
}