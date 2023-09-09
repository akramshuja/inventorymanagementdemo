package com.demo.inventorymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.inventorymanagement.entity.ItemEntity;
import com.demo.inventorymanagement.entity.ItemResponse;
import com.demo.inventorymanagement.error.ItemExistenceException;
import com.demo.inventorymanagement.repository.ItemRepository;
import com.inventorymanagement.model.ItemRequest;
import com.inventorymanagement.model.ItemStatus;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ItemService implements IItemService{
	
	private final ItemRepository itemRepository;
	
	@Autowired
	public ItemService(ItemRepository repo)
	{
		this.itemRepository = repo;
	}

	@Override
	public ItemResponse saveItem(ItemRequest item) throws ItemExistenceException {
		
		if(itemRepository.existsById(item.getItemId()))
		{
			log.error("Item with id " + item.getItemId() + " already exists.");
			throw new ItemExistenceException("Item with id " + item.getItemId() + " already exists.");
		}
		ItemEntity entity = buildItemEntity(item);
		entity.setItemEnteredDate(LocalDateTime.now());
		entity.setItemEnteredByUser(item.getItemEnteredByUser());
		
		itemRepository.save(entity);
		ItemResponse response = new ItemResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	@Override
	public ItemResponse updateItem(int itemId, ItemRequest item) throws ItemExistenceException {
		if(!itemRepository.existsById(item.getItemId()))
		{
			log.error("Item with id " + item.getItemId() + " doesn't exist.");
			throw new ItemExistenceException("Item with id " + item.getItemId() + " doesn't exist.");
		}
		ItemEntity inDbEntity = itemRepository.findById(itemId).get();
		ItemEntity entity = buildItemEntity(item);
		
		// Ensuring the id, entered by user and entered date remains same 
		entity.setItemId(itemId);
		entity.setItemEnteredByUser(inDbEntity.getItemEnteredByUser());
		entity.setItemEnteredDate(inDbEntity.getItemEnteredDate());
		
		itemRepository.save(entity);
		ItemResponse response = new ItemResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteItemById(int id) throws ItemExistenceException {
		if(!itemRepository.existsById(id))
		{
			log.error("Item with id " + id + " doesn't exist.");
			throw new ItemExistenceException("Item with id " + id + " doesn't exist.");
		}
		itemRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deleteAllItems() {
		itemRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public List<ItemResponse> getAllItems() {
		List<ItemEntity> allItems = itemRepository.findAll();
		return allItems.stream().map(this::convertToItemResponse).collect(Collectors.toList());
	}

	@Override
	public ItemResponse getItemById(int id) throws ItemExistenceException {
		ItemEntity entity = itemRepository.findById(id).orElseThrow(()-> // 
						new ItemExistenceException("Item with id " + id + " doesn't exist."));
		ItemResponse response = new ItemResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	@Override
	public List<ItemResponse> getItemByStatusAndEnteredBy(ItemStatus status, String user) {
		List<ItemEntity> entities = itemRepository.findByItemStatusAndItemEnteredByUser(status, user);
		return entities.stream().map(this::convertToItemResponse).collect(Collectors.toList());
	}

	@Override
	public List<ItemResponse> getItemsWithPaginationAndSorting(int pageSize, int page, String sortBy) {
		Sort sort = Sort.by(sortBy);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        Page<ItemEntity> allEntities = itemRepository.findAll(pageable);
        return allEntities.stream().map(this::convertToItemResponse).collect(Collectors.toList());
	}

	private ItemEntity buildItemEntity(ItemRequest item) {
		ItemEntity entity = ItemEntity.builder() //
				.itemId(item.getItemId()) //
				.itemName(item.getItemName()) //
				.itemBuyingPrice(item.getItemBuyingPrice()) //
				.itemSellingPrice(item.getItemSellingPrice()) //
				.itemLastModifiedDate(LocalDateTime.now()) //
				.itemLastModifiedByUser(item.getItemEnteredByUser()) //
				.itemStatus(item.getItemStatus()) //
				.build();
		return entity;
	}
	
	private ItemResponse convertToItemResponse(ItemEntity itemEntity)
	{
		ItemResponse response = new ItemResponse();
		BeanUtils.copyProperties(itemEntity, response);
		return response;
	}
}
