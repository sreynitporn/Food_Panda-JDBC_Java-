package model.service;

import mapper.Mapper;
import model.dao.OrderDao;
import model.dao.OrderDaoImpl;
import model.dto.OrderDto;
import model.entity.Order;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class OrderServiceImpl implements OrderService{
    private OrderDao orderDao = new OrderDaoImpl();
    @Override
    public List<OrderDto> getAllOrder() {
        return orderDao.queryAllOrder().stream().map(Mapper::fromOrderToOrderDto).toList();
    }

    @Override
    public int deleteOrder(Integer id) {
        return orderDao.deletedOrderById(id);
    }

    @Override
    public int updateOrder(Integer id) {
        return orderDao.updateOrderById(id);
    }

    @Override
    public int addOrder(Order order) {
        return orderDao.addNewOrder(order);
    }
}