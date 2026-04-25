package com.botiga.com_botiga.mapper;

import java.util.ArrayList;
import java.util.List;

import com.botiga.com_botiga.DTO.OrderItemResponseDTO;
import com.botiga.com_botiga.DTO.OrderResponseDTO;
import com.botiga.com_botiga.model.Order_Item;
import com.botiga.com_botiga.model.Order;

public class OrderMapper {


    public OrderResponseDTO toResponseDTO(Order order) {
        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();

        for (Order_Item item : order.getOrderItems()) {
            itemDTOs.add(new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice()
            ));
        }

        return new OrderResponseDTO(
            order.getId(),
            order.getCustomer().getId(),
            order.getOrderStatus().name(),
            order.getTotalAmount(),
            order.getOrderDate(),
            itemDTOs
        );
    }

}
