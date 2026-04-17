package org.example.mapper;

import org.example.dto.OrganizationCreateRequestDTO;
import org.example.dto.OrganizationResponseDTO;
import org.example.entity.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization toEntity(OrganizationCreateRequestDTO dto);

    OrganizationResponseDTO toResponse(Organization entity);
}