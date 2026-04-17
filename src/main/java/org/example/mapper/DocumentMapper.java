package org.example.mapper;

import org.example.dto.DocumentResponseDTO;
import org.example.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "header.number", target = "number")
    @Mapping(source = "header.type", target = "type")
    @Mapping(source = "header.format", target = "format")
    @Mapping(source = "header.sender.id", target = "senderId")
    @Mapping(source = "header.receiver.id", target = "receiverId")
    DocumentResponseDTO toResponse(Document document);
}