/*
 * Copyright (C) 2014 KKHM Project
 *
 * Licensed under the Creative Commons Attribution 4.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by/4.0/
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.isken.tax.domain.product;

import java.util.ArrayList;
import java.util.List;



public class Product {

	private int id;
	private Item item;
	private String name;
	private UnitPrice unitPrice;
	
	private static int $count = 0;
	private static List<Product> $instances = new ArrayList<Product>();

	public Product(Item item, String name, UnitPrice unitPrice) {
		this.id = $count++;
		this.item = item;
		this.name = name;
		this.unitPrice = unitPrice;
		
		$instances.add(this);
	}

	public int getId() {
		return id;
	}
	
	public Item getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	
	public UnitPrice getUnitPrice() {
		return unitPrice;
	}

	public static Product $find(int id) {
		for (Product p : $instances) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

}
