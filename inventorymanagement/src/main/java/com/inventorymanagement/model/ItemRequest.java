package com.inventorymanagement.model;


import lombok.Data;

@Data
public class ItemRequest {

	private int itemId;
	private String itemName;
	private String itemEnteredByUser;
	private double itemBuyingPrice;
	private double itemSellingPrice;
	private ItemStatus itemStatus;
	
	
	public double getItemBuyingPrice()
	{
		int scale = (int) Math.pow(10, 1);
		return (double)Math.round(itemBuyingPrice * scale) / scale;
	}
	
	public double getItemSellingPrice()
	{
		int scale = (int) Math.pow(10, 1);
		return (double)Math.round(itemSellingPrice * scale) / scale;
	}
}

