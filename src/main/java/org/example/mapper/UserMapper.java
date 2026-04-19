package org.example.mapper;

import org.example.dto.user.UserCreateRequestDTO;
import org.example.dto.user.UserResponseDTO;
import org.example.dto.user.UserUpdateRequestDTO;
import org.example.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserUpdateRequestDTO dto, @MappingTarget User entity);

    @Mapping(source = "organization.id", target = "organizationId")
    UserResponseDTO toResponse(User user);
}