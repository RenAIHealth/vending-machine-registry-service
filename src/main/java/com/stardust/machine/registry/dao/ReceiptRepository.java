package com.stardust.machine.registry.dao;

import com.stardust.machine.registry.models.Receipt;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends DataRepository<Receipt, Long> {

}