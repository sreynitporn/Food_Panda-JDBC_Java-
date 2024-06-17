package mapper;

import model.dto.CustomerDto;
import model.dto.OrderDto;
import model.dto.ProductDto;
import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;

public class Mapper {
    public static CustomerDto fromCutomerToCustomerDto(Customer customer){
        if(customer==null){
            return  null;
        }
        return CustomerDto.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .id(customer.getId())
                .build();

    }
    public static ProductDto fromProductToProductDto(Product product){
        if (product == null){
            return null;
        }else {
            return ProductDto.builder()
                    .id(product.getId())
                    .productCode(product.getProductCode())
                    .productName(product.getProductName())
                    .productDescription(product.getDescription())
                    .expiredDate(product.getExpireDate())
                    .build();
        }
    }

    public static OrderDto fromOrderToOrderDto(Order order) {
        if (order == null) {
            return null;
        } else {
            return OrderDto.builder()
                    .id(order.getId())
                    .customerName(order.getCustomer().getName())
                    .orderName(order.getOrderName())
                    .orderDescription(order.getOrderDescription())
                    .orderedAt(order.getOrderAt())
                    .build();
        }
    }
}
