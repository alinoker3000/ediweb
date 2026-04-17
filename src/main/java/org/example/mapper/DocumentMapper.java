package org.example.mapper;

import org.example.dto.DocumentCreateRequestDTO;
import org.example.dto.DocumentUpdateRequestDTO;
import org.example.dto.DocumentResponseDTO;
import org.example.entity.Document;
import org.mapstruct.*;

@Mapper(componentModel = "spring",  uses = DocumentHeaderMapper.class)
public interface DocumentMapper {

    Document toEntity(DocumentCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(DocumentUpdateRequestDTO dto, @MappingTarget Document entity);

    @Mapping(source = "header.number", target = "number")
    @Mapping(source = "header.type", target = "type")
    @Mapping(source = "header.format", target = "format")
    @Mapping(source = "header.sender.id", target = "senderId")
    @Mapping(source = "header.receiver.id", target = "receiverId")
    DocumentResponseDTO toResponse(Document document);
}