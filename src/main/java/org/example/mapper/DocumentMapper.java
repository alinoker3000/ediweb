package org.example.mapper;

import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.dto.document.DocumentResponseDTO;
import org.example.entity.Document;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "header.number", target = "number")
    @Mapping(source = "header.type.code", target = "type")
    @Mapping(source = "header.type.format", target = "format")
    @Mapping(source = "header.sender.id", target = "senderId")
    @Mapping(source = "header.receiver.id", target = "receiverId")
    DocumentResponseDTO toResponse(Document document);

}