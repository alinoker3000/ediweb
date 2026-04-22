package org.example.dto.document.invoice;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceDTO {

    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}