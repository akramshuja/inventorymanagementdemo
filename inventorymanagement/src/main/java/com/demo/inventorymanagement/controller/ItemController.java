package com.demo.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.inventorymanagement.entity.ItemResponse;
import com.demo.inventorymanagement.error.ItemExistenceException;
import com.demo.inventorymanagement.service.IItemService;
import com.inventorymanagement.model.ItemRequest;
import com.inventorymanagement.model.ItemStatus;

@RestController
@RequestMapping("/app/item")
public class ItemController {

	private final IItemService itemService;
	
	@Autowired
	public ItemController(IItemService service)
	{
		this.itemService = service;
	}
	
	@PostMapping
	public ResponseEntity<ItemResponse> saveItem(@RequestBody ItemRequest item) throws ItemExistenceException
	{
		ItemResponse response = itemService.saveItem(item);
		return new ResponseEntity<ItemResponse>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/{itemId}")
	public ResponseEntity<ItemResponse> updateItem(@PathVariable int itemId, @RequestBody ItemRequest item) throws ItemExistenceException
	{
		ItemResponse response = itemService.updateItem(itemId, item);
		return new ResponseEntity<ItemResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteItem(@PathVariable int itemId) throws ItemExistenceException
	{
		return itemService.deleteItemById(itemId);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteAllItems() throws ItemExistenceException
	{
		return itemService.deleteAllItems();
	}
	
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemResponse> getItem(@PathVariable int itemId) throws ItemExistenceException
	{
		return new ResponseEntity<ItemResponse>(itemService.getItemById(itemId), HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<List<ItemResponse>> getAllItems()
	{
		return new ResponseEntity<List<ItemResponse>>(itemService.getAllItems(), HttpStatus.OK);
		
	}
	
	@GetMapping(params = {"status", "itemEnteredByUser"})
	public ResponseEntity<List<ItemResponse>> getItems(@RequestParam ItemStatus status, @RequestParam String itemEnteredByUser) throws ItemExistenceException
	{
		return new ResponseEntity<List<ItemResponse>>(itemService.getItemByStatusAndEnteredBy(status, itemEnteredByUser), HttpStatus.OK);
	}
	
	@GetMapping(params = {"pageSize", "page", "sortBy"})
	public ResponseEntity<List<ItemResponse>> getItems(@RequestParam int pageSize, @RequestParam int page, @RequestParam String sortBy) throws ItemExistenceException
	{
		return new ResponseEntity<List<ItemResponse>>(itemService.getItemsWithPaginationAndSorting(pageSize, page, sortBy), HttpStatus.OK);
	}
}
