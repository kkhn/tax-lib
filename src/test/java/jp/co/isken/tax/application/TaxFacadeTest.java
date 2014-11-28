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

import static jp.co.isken.taxlib.application.TestUtils.date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.ContractType;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.contract.TaxTerm;
import jp.co.isken.tax.domain.contract.ConsumptionCalculationBase;
import jp.co.isken.tax.domain.party.Party;
import jp.co.isken.tax.domain.trading.TradingAccount;
import jp.co.isken.tax.domain.trading.TradingEntry;
import jp.co.isken.tax.domain.trading.TradingTransaction;
import jp.co.isken.tax.domain.trading.TransactionType;
import jp.co.isken.tax.domain.trading.Unit;

import org.junit.Test;

public class TaxFacadeTest {
	
	@Test
	public void 契約がないときは0円が返ること() {
		TradingTransaction t = new TradingTransaction(TransactionType.販売,
				null, date("2013/8/1"), null);
		TaxFacade facade = new TaxFacade(t, 100);
		
		assertThat(facade.doCalcTax(), is(0.0));
	}
	
	@Test
	public void 商品がないときは0円が返ること() {
		Party p = new Party("テスト会社");
		TradingTransaction t = new TradingTransaction(TransactionType.販売, 
				new Contract(ContractType.販売, p.getId(), date("2013/8/1"), 
						new TaxTerm(RoundingMethod.切り捨て, RoundingMethod.切り捨て, ConsumptionCalculationBase.合計)), 
				date("2013/8/1"), null);
		TradingAccount a = new TradingAccount(p.getId(), null);
		TradingEntry e = new TradingEntry(t, a, 50, Unit.KG);
		
		TaxFacade facade = new TaxFacade(e, 100);
		
		assertThat(facade.doCalcTax(), is(0.0));
	}

}
