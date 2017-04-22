package com.stardust.machine.registry.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.io.Serializable;

public interface DataRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}