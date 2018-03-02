package com.stardust.machine.registry.components;


import com.stardust.machine.registry.models.Order;
import com.stardust.machine.registry.models.Receipt;

public interface ReceiptBuilder {
    Receipt createFromOrder(Order order);
}
