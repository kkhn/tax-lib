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

import static jp.co.isken.tax.application.TestUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.contract.ConsumptionCalculationBase;
import jp.co.isken.tax.domain.party.Party;

import org.junit.Before;
import org.junit.Test;

public class UC001Test {
	@Before
	public void setUp() {
		Party.instances().clear();
		Contract.instances().clear();
	}
	
	@Test
	public void UC001ー01() {
		// 契約先の登録
		Party p = new Party("麺屋海神");
		
		// 契約の登録
		PartyContract pc = new PartyContract(p.getId());
		pc.makeContract(ContractType.販売, date("2013/07/28"), RoundingMethod.切り捨て, RoundingMethod.切り捨て, ConsumptionCalculationBase.合計);
		
		// テスト		
		Contract c = pc.getContract();
		assertThat(c.getParty().getId(), is(p.getId()));
		assertThat(c.getWhenAgreement(), is(date("2013/07/28")));
		assertThat(c.getTaxTerm().getPriceRoundingMethod(), is(RoundingMethod.切り捨て));
		assertThat(Contract.$getAll().size(), is(1));
	}
	
	@Test
	public void UC001ー02() {
		// 契約先の登録
		Party p = new Party("佐藤精肉");
		
		// 契約の登録
		PartyContract pc = new PartyContract(p.getId());
		pc.makeContract(ContractType.仕入, date("2013/07/29"), RoundingMethod.四捨五入, RoundingMethod.四捨五入, ConsumptionCalculationBase.明細);

		// テスト
		Contract c = pc.getContract();
		assertThat(c.getParty().getId(), is(p.getId()));
		assertThat(c.getWhenAgreement(), is(date("2013/07/29")));
		assertThat(c.getTaxTerm().getPriceRoundingMethod(), is(RoundingMethod.四捨五入));
		assertThat(Contract.$getAll().size(), is(1));
	}
}
