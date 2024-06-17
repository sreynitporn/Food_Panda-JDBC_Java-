package model.service;

import model.dao.CustomerDao;
import model.dao.CustomerDaoImpl;
import model.dto.CustomerDto;
import model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomers();
    int deleteCustomer(Integer id);
    int updateCustomer(Integer id);
    int addCustomer(Customer customer);

}