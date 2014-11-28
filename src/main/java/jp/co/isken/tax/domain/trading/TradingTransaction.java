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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.pricing.PricingBasis;
import jp.co.isken.tax.domain.product.Item;

public class TradingTransaction implements PricingBasis {

	private Contract contract;
	private TransactionType type;
	private Date whenCharged;
	private List<TradingEntry> entries = new ArrayList<TradingEntry>();
	
	public TradingTransaction(TransactionType type, Contract contract, Date whenCharged, Date whenBooked) {
		this.type = type;
		this.contract = contract;
		this.whenCharged = whenCharged;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	@Override
	public Date getWhenCharged() {
		return whenCharged;
	}
	
	public void addEntry(TradingEntry e) {
		entries.add(e);
	}

	public List<TradingEntry> getEntries() {
		return entries;
	}

	@Override
	public RoundingMethod getRoundingMethod() {
		if (contract == null) {
			return null;
		}
		return contract.getTaxTerm().getPriceRoundingMethod();
	}

	@Override
	public Item getItem() {
		if (entries.size() == 0) {
			return null;
		}
		return entries.get(0).getItem();
	}

	@Override
	public TradingTransaction getTransaction() {
		return this;
	}

	// TODO 残数計算

}
