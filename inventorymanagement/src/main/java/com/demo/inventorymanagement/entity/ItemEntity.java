package com.demo.inventorymanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.inventorymanagement.model.ItemStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="tbl_item")
public class ItemEntity {

	@Id
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
