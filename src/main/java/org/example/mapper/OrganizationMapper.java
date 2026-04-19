package org.example.mapper;

import org.example.dto.organization.OrganizationCreateRequestDTO;
import org.example.dto.organization.OrganizationResponseDTO;
import org.example.dto.organization.OrganizationUpdateRequestDTO;
import org.example.entity.Organization;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization toEntity(OrganizationCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(OrganizationUpdateRequestDTO dto, @MappingTarget Organization entity);

    OrganizationResponseDTO toResponse(Organization entity);
}