package org.example.processor.document.xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.document.invoice.InvoiceDTO;
import org.example.dto.document.invoice.InvoicePayloadDTO;
import org.example.dto.document.order.OrderDTO;
import org.example.dto.document.order.OrderPayloadDTO;
import org.example.entity.DocumentType;
import org.example.util.DocumentFormat;
import org.example.processor.document.DocumentProcessor;
import org.example.service.document.validation.XmlValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class XmlDocumentProcessor implements DocumentProcessor {

    private final ObjectMapper objectMapper;

    @Override
    public DocumentFormat supports() {
        return DocumentFormat.XML;
    }

    @Override
    public void validate(String data, DocumentType type) {
        XmlValidationService.validate(data, type.getXsdSchema());
    }

    private String generateOrderXml(OrderPayloadDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("<order>");
        sb.append("<orderNumber>").append(dto.getOrderNumber()).append("</orderNumber>");
        sb.append("<senderGLN>").append(dto.getSenderGln()).append("</senderGLN>");
        sb.append("<receiverGLN>").append(dto.getReceiverGln()).append("</receiverGLN>");
        sb.append("<items>");

        for (OrderDTO item : dto.getItems()) {
            sb.append("<item>");
            sb.append("<name>").append(item.getName()).append("</name>");
            sb.append("<quantity>").append(item.getQuantity()).append("</quantity>");
            sb.append("</item>");
        }

        sb.append("</items>");
        sb.append("</order>");

        return sb.toString();
    }

    private String generateInvoiceXml(InvoicePayloadDTO dto) {
        StringBuilder sb = new StringBuilder();

        sb.append("<invoice>");

        sb.append("<invoiceNumber>")
                .append(dto.getInvoiceNumber())
                .append("</invoiceNumber>");

        sb.append("<orderNumber>")
                .append(dto.getOrderNumber())
                .append("</orderNumber>");

        sb.append("<senderGLN>")
                .append(dto.getSenderGln())
                .append("</senderGLN>");

        sb.append("<receiverGLN>")
                .append(dto.getReceiverGln())
                .append("</receiverGLN>");

        sb.append("<totalAmount>")
                .append(dto.getTotalAmount())
                .append("</totalAmount>");

        sb.append("<items>");

        for (InvoiceDTO item : dto.getItems()) {
            sb.append("<item>");

            sb.append("<name>")
                    .append(item.getName())
                    .append("</name>");

            sb.append("<quantity>")
                    .append(item.getQuantity())
                    .append("</quantity>");

            sb.append("<unitPrice>")
                    .append(item.getUnitPrice())
                    .append("</unitPrice>");

            sb.append("<totalPrice>")
                    .append(item.getTotalPrice())
                    .append("</totalPrice>");

            sb.append("</item>");
        }

        sb.append("</items>");
        sb.append("</invoice>");

        return sb.toString();
    }

    public String generate(DocumentType type, JsonNode payload) {

        if ("ORDER".equals(type.getCode())) {
            OrderPayloadDTO dto = objectMapper.convertValue(payload, OrderPayloadDTO.class);
            return generateOrderXml(dto);
        }

        if ("INVOICE".equals(type.getCode())) {
            InvoicePayloadDTO dto = objectMapper.convertValue(payload, InvoicePayloadDTO.class);
            for (InvoiceDTO item : dto.getItems()) {
                BigDecimal expected = item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

                if (expected.compareTo(item.getTotalPrice()) != 0) {
                    throw new RuntimeException("Invalid totalPrice for item: " + item.getName());
                }
            }
            return generateInvoiceXml(dto);
        }

        throw new RuntimeException("Unsupported document type");
    }
}