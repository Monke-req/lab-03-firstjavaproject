package by.bseu.pp.lab03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order and protects the allowed order state transitions.
 */
public class Order {
    private final Customer customer;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Payment payment;

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getItemsCount() {
        return items.size();
    }

    public Payment getPayment() {
        return payment;
    }

    public void addItem(Product product, int quantity) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Items can be added only to a CREATED order"
            );
        }

        items.add(new OrderItem(product, quantity));
    }

    public double total() {
        double total = 0.0;

        for (OrderItem item : items) {
            total += item.getAmount();
        }

        return total;
    }

    public void confirm() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED order can be confirmed"
            );
        }

        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot confirm an empty order"
            );
        }

        if (!customer.isActive()) {
            throw new IllegalStateException(
                    "Inactive customer cannot confirm an order"
            );
        }

        if (!customer.canPlaceOrder(total())) {
            throw new IllegalStateException(
                    "Order exceeds customer's credit limit"
            );
        }

        status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != OrderStatus.CREATED
                && status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only CREATED or CONFIRMED order can be cancelled"
            );
        }

        status = OrderStatus.CANCELLED;
    }

    public void pay(Payment payment) {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only CONFIRMED order can be paid"
            );
        }

        if (payment == null) {
            throw new IllegalArgumentException(
                    "Payment cannot be null"
            );
        }

        if (!payment.isSuccessful()) {
            throw new IllegalStateException(
                    "Payment must be successful"
            );
        }

        if (payment.getAmount() != total()) {
            throw new IllegalArgumentException(
                    "Payment amount must equal order total"
            );
        }

        this.payment = payment;
        this.status = OrderStatus.PAID;
    }

    public boolean isPaid() {
        return status == OrderStatus.PAID;
    }
}