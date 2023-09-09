package com.demo.inventorymanagement.entity;

import java.time.LocalDateTime;

import com.inventorymanagement.model.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

	private int itemId;
	private String itemName;
	private String itemEnteredByUser;
	private LocalDateTime itemEnteredDate;
	private double itemBuyingPrice;
	private double itemSellingPrice;
	private LocalDateTime itemLastModifiedDate;
	private String itemLastModifiedByUser;
	private ItemStatus itemStatus;
}
