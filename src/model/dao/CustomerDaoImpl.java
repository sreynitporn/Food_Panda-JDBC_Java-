package model.dao;

import model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public List<Customer> queryAllCustomer() {
        String sql = """
                SELECT * FROM "customer"
                """;
        try( Connection connection= DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "072072"
        );
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
        ){
            List<Customer> customerList=new ArrayList<>();
            while (resultSet.next()){
                customerList.add(
                        new Customer(
                                resultSet.getInt("Id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getBoolean("is_deleted"),
                                resultSet.getDate("created_date")
                        )
                );
           }return customerList;
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }return new ArrayList<>();
    }

    @Override
    public int updateCustomerById(Integer id) {
        String sql = """
                UPDATE "customer"
                SET name = ? , email = ?
                WHERE id = ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            Customer customer = searchCustomerById(id);
            if(customer!=null){
                System.out.println("[+] Insert name:");
                preparedStatement.setString(1,new Scanner(System.in).next());
                System.out.println("[+] Insert email");
                preparedStatement.setString(2,new Scanner(System.in).next());
                preparedStatement.setInt(3,id);
                int rowAffected = preparedStatement.executeUpdate();
                String message = rowAffected>0 ? "update successfully": "update failed";
                System.out.println(message);
            }else {
                System.out.println("Customer not found");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteCustomerById(Integer id) {
        String sql = """
                DELETE FROM "customer"
                WHERE id= ?
                """;
        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "072072"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            Customer customer= searchCustomerById(id);
            if(customer==null){
                System.out.println("can't delete");
            }else {
                preparedStatement.setInt(1,id);
                int rowAffected=preparedStatement.executeUpdate();
                System.out.println("delete successfully!");
            }

        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return 0;
    }

    @Override
    public int addNewCustomer(Customer customer) {
        String sql = """
                INSERT INTO "customer" (name,email,password,is_deleted,created_date)
                VALUES (?,?,?,?,?);
                """;
        try( Connection connection= DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "072072"
        );
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getEmail());
            preparedStatement.setString(3,customer.getPassword());
            preparedStatement.setBoolean(4,customer.getIsDeleted());
            preparedStatement.setDate(5,customer.getCreatDate());
            int rowAffect =preparedStatement.executeUpdate();
            if(rowAffect>0){
                System.out.println("Customer has Been Create successfully");

            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public Customer searchCustomerById(Integer id) {
        String sql = """
                SELECT * FROM "customer"
                WHERE id =? 
                """;
        try( Connection connection= DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "072072");
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
                 preparedStatement.setInt(1,id);
                 ResultSet resultSet=preparedStatement.executeQuery();
                 Customer customer=null;
                 while (resultSet.next()){
                     customer=Customer
                             .builder()
                             .id(resultSet.getInt("id"))
                             .name(resultSet.getString("name"))
                             .email(resultSet.getString("email"))
                             .password(resultSet.getString("password"))
                             .isDeleted(resultSet.getBoolean("is_deleted"))
                             .creatDate(resultSet.getDate("created_date"))
                             .build();
                 }
                 return customer;
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }
}
