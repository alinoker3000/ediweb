package org.example.mapper;

import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.entity.DocumentHeader;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentHeaderMapper {

    DocumentHeader toEntity(DocumentCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(DocumentUpdateRequestDTO dto, @MappingTarget DocumentHeader entity);
}