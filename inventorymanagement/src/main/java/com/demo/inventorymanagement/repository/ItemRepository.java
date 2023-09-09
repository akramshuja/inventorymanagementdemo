package com.demo.inventorymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.inventorymanagement.entity.ItemEntity;
import com.inventorymanagement.model.ItemStatus;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>
{

	List<ItemEntity> findByItemStatusAndItemEnteredByUser(ItemStatus status, String user);
}
