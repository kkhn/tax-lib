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
package jp.co.isken.tax.domain.pricing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.domain.trading.TradingEntry;
import jp.co.isken.tax.domain.trading.TradingTransaction;

public class PricingTransaction {

	// TODO PaymentBasisの削除
	private PricingBasis basis;
	private TradingTransaction tradingTransaction;
	private TradingEntry tradingEntry;
	private Date effectiveDate;
	private Date registeredDate;
	private List<PricingEntry> entries = new ArrayList<PricingEntry>();

	public PricingTransaction(TradingTransaction basis, Date effectiveDate, Date registeredDate) {
		this.basis = basis;
		this.tradingTransaction = basis;
		this.effectiveDate = effectiveDate;
		this.registeredDate = registeredDate;
	}
	
	public PricingTransaction(TradingEntry basis, Date effectiveDate, Date registeredDate) {
		this.basis = basis;
		this.tradingEntry = basis;
		this.effectiveDate = effectiveDate;
		this.registeredDate = registeredDate;
	}
	
	public PricingBasis getBasis() {
		return basis;
	}
	
	public TradingTransaction getTradingTransaction() {
		return tradingTransaction;
	}
	
	public TradingEntry getTradingEntry() {
		return tradingEntry;
	}
	
	public List<PricingEntry> getEntries() {
		return entries;
	}

	public void addEntries(PricingEntry e) {
		entries.add(e);
	}

	public double getTotalPrice() {
		BigDecimal result = new BigDecimal("0.00");
		for (PricingEntry e : entries) {
			result = result.add(new BigDecimal(e.getPrice()));
		}
		return result.doubleValue();
	}
	
	// TODO 残高計算

}
