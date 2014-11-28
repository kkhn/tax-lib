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
package jp.co.isken.tax.application;

import jp.co.isken.tax.domain.product.Product;
import jp.co.isken.tax.domain.trading.Unit;

public class OrderDetail {

	private Product product;
	private double amount;
	private Unit unit;

	public OrderDetail(int productId, double amount, Unit unit) {
		this.product = Product.$find(productId);
		this.amount = amount;
		this.unit = unit;
	}

	public Product getProduct() {
		return product;
	}

	public double getAmount() {
		return amount;
	}

	public Unit getUnit() {
		return unit;
	}
}
