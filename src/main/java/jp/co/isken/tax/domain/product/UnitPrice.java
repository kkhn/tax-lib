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

import jp.co.isken.tax.domain.trading.Unit;

public class UnitPrice {

	private int price; //円
	private Unit unit;

	public UnitPrice(int price, Unit unit) {
		this.price = price;
		this.unit = unit;
	}
	
	public int getPrice() {
		return price;
	}

	public Unit getUnit() {
		return unit;
	}
}
