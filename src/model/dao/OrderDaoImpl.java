package model.dao;

import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderDaoImpl implements OrderDao {
    @Override
    public int addNewOrder(Order order) {
        String sql = """
              INSERT INTO "order"(order_name,order_description, cus_id, ordered_at)
              VALUES (?,?,?,?)
                """;
        String sql1 = """
               INSERT INTO "product_order" 
                VALUES (?,?)

                """;
        try(
                Connection connection= DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement=connection.prepareStatement(sql);
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                Statement statement = connection.createStatement();
                ){
            preparedStatement.setString(1,order.getOrderName());
            preparedStatement.setString(2,order.getOrderDescription());
            preparedStatement.setInt(3,order.getCustomer().getId());
            preparedStatement.setDate(4,order.getOrderAt());
            int rowAffected = preparedStatement.executeUpdate();
            String message = rowAffected>0 ? "Insert Order successfully": "Insert Order Failed";
            System.out.println(message);
            // product order
            for(Product product:order.getProductList()){
                preparedStatement1.setInt(1,product.getId());
                preparedStatement1.setInt(2,order.getId());
            }
            int rowAffected1 = preparedStatement1.executeUpdate();
            if(rowAffected1>0){
                System.out.println("Product have been order");
            }else {
                System.out.println("product out of stock");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return 0;
    }

    @Override
    public int deletedOrderById(Integer id) {
        String sql = """
                DELETE FROM "order"
                WHERE id =?
                """;

        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            Order order = searchOrderById(id);
            if(order!=null){
                preparedStatement.setInt(1,id);
                int rowAffected = preparedStatement.executeUpdate();
                String message = rowAffected>0? "Deleted successfully" : "Deleted failed";
                System.out.println(message);
                return rowAffected;
            }else {
                System.out.println("Not found Order");
            }


        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int updateOrderById(Integer id) {
        String sql = """
                UPDATE "order" SET order_name=? , order_description=?
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement =  connection.prepareStatement(sql);
                ){
            Order order = searchOrderById(id);
            if(order!=null){
                System.out.println("[+] Insert order name:");
                preparedStatement.setString(1,new Scanner(System.in).nextLine());
                System.out.println("[+] Insert order description");
                preparedStatement.setString(2,new  Scanner(System.in).nextLine());
                preparedStatement.setInt(3,id);
                int rowAffected = preparedStatement.executeUpdate();
                if(rowAffected>0){
                    System.out.println(" >_< Update successfully");
                }else {
                    System.out.println(" :) Update failed");
                }
            }
            else {
                System.out.println("Not found Order");
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Order searchOrderById(Integer id) {
        String sql = """
                SELECT * FROM "order"
                WHERE id = ?
                """;
        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement= connection.prepareStatement(sql);

                ){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order =null;
            while (resultSet.next()){
                order = Order.builder()
                        .id(resultSet.getInt("id"))
                        .orderName(resultSet.getString("order_name"))
                        .orderDescription(resultSet.getString("order_description"))
                        .customer(Customer.builder()
                                .id(resultSet.getInt("cus_id"))
                                .build())
                        .orderAt(resultSet.getDate("ordered_at"))
                        .build();

            }
            return order;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Order> queryAllOrder() {
        String sql = """
                SELECT * FROM "order"
                INNER JOIN "customer" c ON "order" .cus_id = c.id
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                Statement statement = connection.createStatement();
                ){
            ResultSet resultSet = statement.executeQuery(sql);
            List<Order> oderList = new ArrayList<>();
            while (resultSet.next()){
                oderList.add(
                        Order.builder()
                                .id(resultSet.getInt("id"))
                                .orderName(resultSet.getString("order_name"))
                                .orderDescription(resultSet.getString("order_description"))
                                .orderAt(resultSet.getDate("ordered_at"))
                                .customer(Customer.builder()
                                        .id(resultSet.getInt("cus_id"))
                                        .name(resultSet.getString("name"))
                                        .email(resultSet.getString("email"))
                                        .password(resultSet.getString("password"))
                                        .isDeleted(resultSet.getBoolean("is_deleted"))
                                        .creatDate(resultSet.getDate("created_date"))
                                        .build())
                                .build()
                );
            }return oderList;
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return new ArrayList<>();
    }
}
