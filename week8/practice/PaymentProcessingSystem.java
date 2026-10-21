package week8.practice;

import java.util.*;

/**
 * PaymentProcessingSystem models e-commerce payment workflows utilizing polymorphic payment gateways,
 * order validation, and idempotent state transitions.
 */
public class PaymentProcessingSystem {

    public enum OrderStatus {
        PENDING,
        PAID,
        CANCELLED
    }

    public static class Product {
        private final String id;
        private final String name;
        private final double price;

        public Product(String id, String name, double price) {
            if (id == null || name == null || price < 0) {
                throw new IllegalArgumentException("Invalid product parameters");
            }
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static class OrderItem {
        private final Product product;
        private final int quantity;

        public OrderItem(Product product, int quantity) {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException("Invalid order item parameters");
            }
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return product.getPrice() * quantity;
        }
    }

    public static class Customer {
        private final String id;
        private final String name;

        public Customer(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public interface PaymentMethod {
        String getMethodName();
        boolean processPayment(double amount);
    }

    public static class CreditCardPayment implements PaymentMethod {
        private final String cardNumber;
        private final boolean simulateSuccess;

        public CreditCardPayment(String cardNumber, boolean simulateSuccess) {
            this.cardNumber = cardNumber;
            this.simulateSuccess = simulateSuccess;
        }

        @Override
        public String getMethodName() {
            return "Credit Card";
        }

        @Override
        public boolean processPayment(double amount) {
            return simulateSuccess;
        }
    }

    public static class PayPalPayment implements PaymentMethod {
        private final String email;
        private final boolean simulateSuccess;

        public PayPalPayment(String email, boolean simulateSuccess) {
            this.email = email;
            this.simulateSuccess = simulateSuccess;
        }

        @Override
        public String getMethodName() {
            return "PayPal";
        }

        @Override
        public boolean processPayment(double amount) {
            return simulateSuccess;
        }
    }

    public static class BankTransferPayment implements PaymentMethod {
        private final String accountNumber;
        private final boolean simulateSuccess;

        public BankTransferPayment(String accountNumber, boolean simulateSuccess) {
            this.accountNumber = accountNumber;
            this.simulateSuccess = simulateSuccess;
        }

        @Override
        public String getMethodName() {
            return "Bank Transfer";
        }

        @Override
        public boolean processPayment(double amount) {
            return simulateSuccess;
        }
    }

    public static class Order {
        private final String orderId;
        private final Customer customer;
        private final List<OrderItem> items = new ArrayList<>();
        private OrderStatus status = OrderStatus.PENDING;

        public Order(String orderId, Customer customer) {
            this.orderId = orderId;
            this.customer = customer;
            System.out.printf("Order created for %s.%n", customer.getName());
        }

        public void addItem(Product product, int quantity) {
            items.add(new OrderItem(product, quantity));
        }

        public List<OrderItem> getItems() {
            return Collections.unmodifiableList(items);
        }

        public double calculateTotal() {
            double sum = 0.0;
            for (OrderItem item : items) {
                sum += item.getTotalPrice();
            }
            return sum;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public String getOrderId() {
            return orderId;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void pay(PaymentMethod paymentMethod) {
            if (items.isEmpty()) {
                System.out.println("Cannot process payment for an empty order.");
                return;
            }
            double total = calculateTotal();
            System.out.printf("Payment initiated via %s for %s.%n",
                    paymentMethod.getMethodName(), orderId);

            boolean success = paymentMethod.processPayment(total);
            if (success) {
                this.status = OrderStatus.PAID;
                System.out.printf("Payment for %s successful.%n", orderId);
            } else {
                System.out.printf("Payment for %s failed.%n", orderId);
            }
            System.out.printf("Order status: %s.%n", status);
        }
    }

    public static void main(String[] args) {
        Customer customerX = new Customer("C-X", "Customer X");
        Customer customerY = new Customer("C-Y", "Customer Y");
        Customer customerZ = new Customer("C-Z", "Customer Z");

        Product prodA = new Product("P-A", "Product A", 25.0);
        Product prodB = new Product("P-B", "Product B", 50.0);
        Product prodC = new Product("P-C", "Product C", 40.0);

        // Customer X creates order with Product A (qty 2) and Product B (qty 1)
        Order orderX = new Order("Order X", customerX);
        orderX.addItem(prodA, 2);
        orderX.addItem(prodB, 1);
        PaymentMethod creditCard = new CreditCardPayment("411111111111", true);
        orderX.pay(creditCard);

        // Customer Y creates empty order and attempts payment
        Order orderY = new Order("Order Y", customerY);
        orderY.pay(creditCard);

        // Customer Z creates order with Product C (qty 1) and pays via PayPal (fails)
        Order orderZ = new Order("Order Z", customerZ);
        orderZ.addItem(prodC, 1);
        PaymentMethod payPal = new PayPalPayment("z@example.com", false);
        orderZ.pay(payPal);
    }
}
