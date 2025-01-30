package example.quarkus.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ResponseDto(Long id, String name, String type, Integer quantity) {
}