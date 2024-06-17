package view;

import controller.CustomerController;
import controller.OrderController;
import controller.ProductController;

import java.util.Scanner;

public class ViewUi {
    private final CustomerController customerController = new CustomerController();
    private final OrderController orderController = new OrderController();
    private final ProductController productController = new ProductController();
    public void ui(){
        while(true){
            System.out.println("========Customer==========");
            System.out.println("1. Show All Customers");
            System.out.println("2. Add new Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Update Customer");
            System.out.println("=========Product===========");
            System.out.println("5. Show All Product");
            System.out.println("6. Add new Product");
            System.out.println("7. Delete Product");
            System.out.println("8. Update Product");
            System.out.println("===========Order===========");
            System.out.println("9. Show All Orders");
            System.out.println("10. Add new Order");
            System.out.println("11. Delete Order");
            System.out.println("12. Update Order");
            System.out.println("13. Exit");
            System.out.print("[+] Insert Option: ");
            Integer option = new Scanner(System.in).nextInt();
            switch (option){
                case 1 -> customerController.getAllCustomers();
                case 2 -> customerController.insertCustomer();
                case 3 -> customerController.deleteCustomer();
                case 4 -> customerController.updateCustomer();
                case 5 -> productController.getAllProducts();
                case 6 -> productController.addNewProduct();
                case 7 -> productController.deleteProduct();
                case 8 -> productController.updateProduct();
                case 9 -> orderController.getAllOrder();
                case 10 -> orderController.addNewOrder();
                case 11 -> orderController.deleteOrder();
                case 12 -> orderController.updateOrder();
                case 13-> {
                    System.out.println("Exiting the program.");
                    System.exit(0);
                }
            }
        }
    }
}