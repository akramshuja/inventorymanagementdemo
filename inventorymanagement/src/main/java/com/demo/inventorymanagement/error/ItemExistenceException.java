package com.demo.inventorymanagement.error;

public class ItemExistenceException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public ItemExistenceException(String message)
	{
		super(message);
	}
}
