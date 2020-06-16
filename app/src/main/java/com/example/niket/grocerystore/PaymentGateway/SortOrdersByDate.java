package com.example.niket.grocerystore.PaymentGateway;

import java.util.Comparator;

public class SortOrdersByDate implements Comparator<OrderPojo> {
    @Override
    public int compare(OrderPojo orderPojo, OrderPojo t1) {

        return t1.getCurrentDate().compareTo(orderPojo.getCurrentDate());
    }
}


