package model.dao;

import model.entity.Order;

import java.util.List;

public interface OrderDao {
    int addNewOrder (Order order);
    int deletedOrderById(Integer id);
    int updateOrderById(Integer id);
    Order searchOrderById(Integer id);
    List<Order>queryAllOrder();
}
