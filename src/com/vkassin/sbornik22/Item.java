package com.vkassin.sbornik22;

public class Item {

	private int id;
	public int order;
	public String name;
	public String text;
	public int lock;

	public Item (int i) {
		
		id = i;
	}
	
	public int getId() {
		
		return id;
	}
}
