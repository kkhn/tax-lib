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
package jp.co.isken.tax.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.application.TaxFacade;
import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.contract.ConsumptionCalculationBase;
import jp.co.isken.tax.domain.pricing.MemoAccount;
import jp.co.isken.tax.domain.pricing.PricingAccount;
import jp.co.isken.tax.domain.pricing.PricingEntry;
import jp.co.isken.tax.domain.pricing.PricingTransaction;
import jp.co.isken.tax.domain.trading.TradingEntry;
import jp.co.isken.tax.domain.trading.TradingTransaction;
import jp.co.isken.tax.util.CalcUtils;

public class PricingTransactionBuilder {

	private Contract contract;
	private TradingTransaction tt;
	private Date effectiveDate;
	private Date registeredDate;
	private List<PricingTransaction> transactions = new ArrayList<PricingTransaction>();

	public PricingTransactionBuilder(TradingTransaction tt, Date effectiveDate,
			Date registeredDate) {
		this.tt = tt;
		this.contract = tt.getContract();
		this.effectiveDate = effectiveDate;
		this.registeredDate = registeredDate;
	}
	
	public List<PricingTransaction> getTransactions() {
		return transactions;
	}

	/**
	 * 代金取引・移動の作成
	 * 契約の課税単位に合わせて作成する
	 */
	public void buildEntry() {
		ConsumptionCalculationBase taxableUnit = contract.getTaxTerm().getTaxableUnit();
		if (taxableUnit == ConsumptionCalculationBase.合計) {
			transactions = buildTotal(createProductTransaction());
		} else if (taxableUnit == ConsumptionCalculationBase.明細) {
			transactions = buildParticular(createProductTransaction());
		} else if (taxableUnit == ConsumptionCalculationBase.安い方) {
			List<PricingTransaction> total = buildTotal(createProductTransaction());
			List<PricingTransaction> particular = buildParticular(createProductTransaction());
			transactions = getLowerTransaction(total, particular);
		}
	}

	/**
	 * 商品代金取引・移動の作成
	 * 代金勘定はパーティ×商品の関連で検索し代金移動にリンクをつける（消費税も同様）
	 */
	private List<PricingTransaction> createProductTransaction() {
		ArrayList<PricingTransaction> result = new ArrayList<PricingTransaction>();
		
		RoundingMethod roundingRule = tt.getContract().getTaxTerm().getPriceRoundingMethod();
		for (TradingEntry ce : tt.getEntries()) {
			PricingTransaction t = new PricingTransaction(ce, effectiveDate, registeredDate);
			PricingAccount a = PricingAccount.getAccount(
					ce.getTransaction().getContract().getParty().getId(), MemoAccount.getAccount("商品代金"));
			new PricingEntry(t, a, roundingRule.calc(CalcUtils.multiply(ce.getUnitPrice().getPrice(), ce.getQuantity())));
			
			result.add(t);
		}
		return result;
	}
		
	/**
	 * 消費税代金取引・移動の作成（課税単位が合計の場合）
	 */
	private List<PricingTransaction> buildTotal(List<PricingTransaction> pts) {
		// 商品代金の合算
		BigDecimal total = BigDecimal.ZERO;
		for (PricingTransaction t : pts) {
			total = total.add(BigDecimal.valueOf(t.getTotalPrice()));
		}
		TaxFacade facade = new TaxFacade(tt, total.doubleValue());
		
		PricingAccount a = PricingAccount.getAccount(contract.getParty().getId(), 
				MemoAccount.getAccount("消費税"));
		PricingTransaction t = new PricingTransaction(tt, effectiveDate, null);
		new PricingEntry(t, a, facade.doCalcTax());
		pts.add(t);
		
		return pts;
	}
	
	/**
	 * 消費税代金取引・移動の作成（課税単位が明細単位の場合）
	 */
	private List<PricingTransaction> buildParticular(List<PricingTransaction> pts) {
		PricingAccount a = PricingAccount.getAccount(contract.getParty().getId(), 
				MemoAccount.getAccount("消費税"));

		for (PricingTransaction pt : pts) {
			TaxFacade facade = new TaxFacade(pt.getBasis(), pt.getTotalPrice());
			new PricingEntry(pt, a, facade.doCalcTax());
		}
		
		return pts;
	}
	
	private List<PricingTransaction> getLowerTransaction( 
			List<PricingTransaction> pts1,
			List<PricingTransaction> pts2) {
		
		BigDecimal r1 = calcTotalTax(pts1);
		BigDecimal r2 = calcTotalTax(pts2);
		
		return r1.compareTo(r2) < 0 ? pts1 : pts2;
	}

	private BigDecimal calcTotalTax(List<PricingTransaction> pts) {
		BigDecimal result = BigDecimal.ZERO;
		for (PricingTransaction t : pts) {
			for (PricingEntry e : t.getEntries()) {
				if (e.getAccount().getAccount().getName().equals("消費税")) {
					result = result.add(BigDecimal.valueOf(e.getPrice()));
				}
			}
		}
		return result;
	}
	
}
