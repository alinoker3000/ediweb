package org.example.dto.document.invoice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class InvoicePayloadDTO {

    private String invoiceNumber;
    private String orderNumber;

    private String senderGln;
    private String receiverGln;

    private BigDecimal totalAmount;

    private List<InvoiceDTO> items;
}