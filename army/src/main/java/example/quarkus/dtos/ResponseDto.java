package example.quarkus.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ResponseDto(String name, String country, String type, String commander) {
}
