package org.example.dto.document.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPayloadDTO {

    private String orderNumber;

    private String senderGln;
    private String receiverGln;

    private List<OrderDTO> items;
}