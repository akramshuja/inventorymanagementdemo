package com.demo.inventorymanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.demo.inventorymanagement.entity.ItemResponse;
import com.demo.inventorymanagement.error.ItemExistenceException;
import com.inventorymanagement.model.ItemRequest;
import com.inventorymanagement.model.ItemStatus;

public interface IItemService {

	public  ItemResponse saveItem(ItemRequest item) throws ItemExistenceException;
	
	public ItemResponse updateItem(int itemId, ItemRequest item) throws ItemExistenceException;
	
	public ResponseEntity<Void> deleteItemById(int id) throws ItemExistenceException;
	
	public ResponseEntity<Void> deleteAllItems();
	
	public List<ItemResponse> getAllItems();
	
	public ItemResponse getItemById(int id) throws ItemExistenceException;
	
	public List<ItemResponse> getItemByStatusAndEnteredBy(ItemStatus status, String user);
	
	public List<ItemResponse> getItemsWithPaginationAndSorting(int pageSize, int page, String sortBy);
	
}
