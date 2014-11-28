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

import java.util.Date;

import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.product.Product;
import jp.co.isken.tax.domain.trading.TradingAccount;
import jp.co.isken.tax.domain.trading.TradingEntry;
import jp.co.isken.tax.domain.trading.TradingTransaction;
import jp.co.isken.tax.domain.trading.TransactionType;
import jp.co.isken.tax.domain.trading.Unit;

public class TradingTransactionBuilder {

	private Contract contract;
	private TradingTransaction transaction;

	public TradingTransactionBuilder(
			TransactionType dealType, Contract contract, Date effectiveDate, Date registeredDate) {
		this.contract = contract;
		this.transaction = new TradingTransaction(dealType, contract, effectiveDate, registeredDate);
	}
	
	public TradingTransaction getTransaction() {
		return transaction;
	}

	/**
	 * 商流移動の作成
	 * 商流勘定はパーティ×商品の関連で検索し商流移動にリンクをつける
	 */
	public void buildEntry(Product product, double amount, Unit unit) {
		TradingAccount a = TradingAccount.getAccount(contract.getParty().getId(), product);
		new TradingEntry(transaction, a, amount, unit);
	}

}
