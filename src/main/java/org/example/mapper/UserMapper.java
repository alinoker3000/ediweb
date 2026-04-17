package org.example.mapper;

import org.example.dto.UserCreateRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequestDTO dto);

    @Mapping(source = "organization.id", target = "organizationId")
    UserResponseDTO toResponse(User user);
}