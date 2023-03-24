package com.example.service;

import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.example.valueObject.Employee;
import com.example.valueObject.Product;
import com.example.valueObject.ResponseTemplateValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseTemplateValueObject getOrderWithProduct(String orderId) {
        ResponseTemplateValueObject ResponseValueObject = new ResponseTemplateValueObject();

        Order order = orderRepository.findOrderById(orderId);
        Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + order.getProductId(), Product.class);

        Employee employee = restTemplate.getForObject("http://Employee-SERVICE/employees/" + order.getEmployeeId(), Employee.class);




        ResponseValueObject.setProduct(product);
        ResponseValueObject.setEmployee(employee);
        ResponseValueObject.setOrder(order);

        return ResponseValueObject;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(String userId) {
        return orderRepository.findOrderById(userId);
    }
}