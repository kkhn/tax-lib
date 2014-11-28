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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.builder.TradingTransactionBuilder;
import jp.co.isken.tax.builder.PricingTransactionBuilder;
import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.party.Party;
import jp.co.isken.tax.domain.pricing.PricingTransaction;
import jp.co.isken.tax.domain.trading.TradingTransaction;

public class Order {

	private DealType dealType;
	private Contract contract;
	private NonTaxType nonTaxType;
	private TaxablesType taxablesType;
	private Date whenDeal;
	private List<OrderDetail> details = new ArrayList<OrderDetail>();

	public Order(Party client, DealType dealType, NonTaxType nonTaxType,
			TaxablesType taxablesType, Date whenDeal, OrderDetail... details) {
		this.contract = Contract.$find(client.getId(), dealType.getContractType());
		this.dealType = dealType;
		this.nonTaxType = nonTaxType;
		this.taxablesType = taxablesType;
		this.whenDeal = whenDeal;		
		for (OrderDetail detail : details) {
			this.details.add(detail);
		}
	}
	
	public Contract getContract() {
		return contract;
	}

	public List<PricingTransaction> calcPrice() {
		// 商流取引の作成
		TradingTransaction ct = createCommercialTransaction();
		
		// 商品代金の作成
		return createPaymentTransaction(ct);
	}

	/**
	 * 商流取引・移動の作成
	 */
	private TradingTransaction createCommercialTransaction() {
		TradingTransactionBuilder builder = new TradingTransactionBuilder(
				dealType.getDealType(), contract, whenDeal, null);
		
		for (OrderDetail d : details) {
			builder.buildEntry(d.getProduct(), d.getAmount(), d.getUnit());
		}
		
		return builder.getTransaction();
	}
	
	/**
	 * 代金取引・移動（商品、消費税）の作成
	 */
	private List<PricingTransaction> createPaymentTransaction(TradingTransaction ct) {
		PricingTransactionBuilder builder = new PricingTransactionBuilder(ct, whenDeal, null);
		builder.buildEntry();
		return builder.getTransactions();
	}
	
	public void commit(List<PricingTransaction> list) {
		// TODO コミットのタイミングでvalidationする
		// TODO 永続化の処理
	}
}
