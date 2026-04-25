package com.botiga.com_botiga.DTO;

import java.util.List;

public class OrderRequestDTO {
    private List<OrderItemResquestDTO> items;

    public List<OrderItemResquestDTO> getItems() { 
        return items; 
    }
    public void setItems(List<OrderItemResquestDTO> items) { 
        this.items = items; 
    }
    public OrderRequestDTO() {
    }

    
}
