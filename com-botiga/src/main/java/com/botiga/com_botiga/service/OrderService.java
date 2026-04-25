package com.botiga.com_botiga.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.botiga.com_botiga.DTO.OrderItemResponseDTO;
import com.botiga.com_botiga.DTO.OrderItemResquestDTO;
import com.botiga.com_botiga.DTO.OrderRequestDTO;
import com.botiga.com_botiga.DTO.OrderResponseDTO;
import com.botiga.com_botiga.model.Customer;
import com.botiga.com_botiga.model.OrderStatus;
import com.botiga.com_botiga.model.Order_Item;
import com.botiga.com_botiga.model.Product;
import com.botiga.com_botiga.repository.CustomerRepository;
import com.botiga.com_botiga.repository.OrderRepository;
import com.botiga.com_botiga.repository.ProductRepository;

import com.botiga.com_botiga.model.Order;
import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public OrderResponseDTO createOrder(Long customerId, OrderRequestDTO orderRequest) {

        // 1. Busquem el customer
        Optional<Customer> opCus = customerRepository.findById(customerId);
        if (opCus.isEmpty()) return null;
        Customer customer = opCus.get();

        // 2. Creem l'order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.PENDENT);
        order.setTotalAmount(BigDecimal.ZERO);

        // 3. Creem els order_items i calculem el total
        BigDecimal total = BigDecimal.ZERO;
        List<Order_Item> items = new ArrayList<>();

        for (OrderItemResquestDTO itemReq : orderRequest.getItems()) {
            Optional<Product> opProd = productRepository.findById(itemReq.getProductId());
            if (opProd.isEmpty()) return null;
            Product product = opProd.get();

            Order_Item item = new Order_Item();
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());

            // preu * quantitat
            BigDecimal subtotal = product.getPrice()
                .multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(subtotal);

            items.add(item);
        }

        order.setTotalAmount(total);
        order.getOrderItems().addAll(items);
        orderRepository.save(order);

        return toResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO processOrder(Long orderId) {

        Optional<Order> opOrder = orderRepository.findById(orderId);
        if (opOrder.isEmpty()) return null;

        Order order = opOrder.get();

        // Només es pot processar si està PENDENT
        if (order.getOrderStatus() != OrderStatus.PENDENT) return null;

        order.setOrderStatus(OrderStatus.PROCESSAT);
        orderRepository.save(order);

        return toResponseDTO(order);
    }

    // Mètode auxiliar per convertir a DTO
    private OrderResponseDTO toResponseDTO(Order order) {
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
