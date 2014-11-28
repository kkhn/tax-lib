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
package jp.co.isken.tax.domain.trading;

import java.util.Date;

import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.pricing.PricingBasis;
import jp.co.isken.tax.domain.product.Item;
import jp.co.isken.tax.domain.product.UnitPrice;

public class TradingEntry implements PricingBasis {

	private TradingTransaction transaction;
	private TradingAccount account;
	private double quanitity;
	private Unit unit;
	
	public TradingEntry(TradingTransaction t, TradingAccount a, double quantity, Unit unit) {
		this.transaction = t;
		this.account = a;
		this.quanitity = quantity;
		this.unit = unit;
		// TODO 双方向参照を解消する
		t.addEntry(this);
	}
	
	@Override
	public TradingTransaction getTransaction() {
		return transaction;
	}
	
	public TradingAccount getAccount() {
		return account;
	}
	
	public double getQuantity() {
		return quanitity;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public UnitPrice getUnitPrice() {
		return account.getProduct().getUnitPrice();
	}

	@Override
	public RoundingMethod getRoundingMethod() {
		return transaction.getRoundingMethod();
	}

	@Override
	public Item getItem() {
		// XXX 例外を返したほうがいいかな
		if (account.getProduct() == null) {
			return null;
		}
		return account.getProduct().getItem();
	}

	@Override
	public Date getWhenCharged() {
		return transaction.getWhenCharged();
	}

}
