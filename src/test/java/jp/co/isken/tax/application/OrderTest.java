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

import static jp.co.isken.tax.application.TestUtils.date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import jp.co.isken.tax.domain.contract.ConsumptionCalculationBase;
import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.ContractType;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.contract.TaxTerm;
import jp.co.isken.tax.domain.party.Party;
import jp.co.isken.tax.domain.pricing.PricingEntry;
import jp.co.isken.tax.domain.pricing.PricingTransaction;
import jp.co.isken.tax.domain.product.Item;
import jp.co.isken.tax.domain.product.Product;
import jp.co.isken.tax.domain.product.UnitPrice;
import jp.co.isken.tax.domain.trading.TradingTransaction;
import jp.co.isken.tax.domain.trading.Unit;
import jp.co.isken.taxlib.domain.rate.TaxRate;
import jp.co.isken.taxlib.domain.rate.TaxRateType;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	private static Party client1;
	private static Party client2;
	private static Party client3;
	private static Product product1;
	private static Product product2;

	@Before
	public void setUp() {
		// 商流・金流
		client1 = new Party("麺屋海神");
		new Contract(ContractType.販売, client1.getId(), date("2013/7/28"),
				new TaxTerm(RoundingMethod.切り捨て, RoundingMethod.切り捨て, ConsumptionCalculationBase.明細));
		
		client2 = new Party("新日本ハム");
		new Contract(ContractType.販売, client2.getId(), date("2013/7/28"),
				new TaxTerm(RoundingMethod.四捨五入, RoundingMethod.四捨五入, ConsumptionCalculationBase.安い方));
		
		client3 = new Party("ハモゴロフーズ");
		new Contract(ContractType.仕入, client3.getId(), date("2013/7/28"),
				new TaxTerm(RoundingMethod.切り捨て, RoundingMethod.切り捨て, ConsumptionCalculationBase.安い方));
		
		Item item = new Item("アラ");
		product1 = new Product(item, "鯛のアラ", new UnitPrice(273, Unit.KG));
		product2 = new Product(item, "ヒラマサのアラ", new UnitPrice(126, Unit.KG));
		
		// 消費税ライブラリ
		new TaxRate(TaxRateType.一般税率, null, date("2013/01/01"), date("2013/07/31"), 5, jp.co.isken.taxlib.domain.rate.Unit.PERCENT);
		new TaxRate(TaxRateType.一般税率, null, date("2012/08/01"), date("2014/01/01"), 8, jp.co.isken.taxlib.domain.rate.Unit.PERCENT);
	}

	@Test
	public void 課税単位が明細単位の場合() {
		// 注文内容の提示
		Order o = new Order(client1, DealType.販売, NonTaxType.課税, TaxablesType.社外, date("2013/7/30"),
				new OrderDetail(product1.getId(), 5.5, Unit.KG),
				new OrderDetail(product2.getId(), 8.3, Unit.KG));
		
		// 代金計算を行う
		List<PricingTransaction> ptList = o.calcPrice();
		
		// 商流取引の確認
		TradingTransaction ct = ptList.get(0).getBasis().getTransaction();		
		assertThat(ct.getEntries().size(), is(2));
		
		// 代金取引の確認
		assertThat(ptList.size(), is(2));
		
		PricingEntry pe = ptList.get(0).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(1501.0));
		
		pe = ptList.get(0).getEntries().get(1);
		assertThat(pe.getAccount().getAccount().getName(), is("消費税"));
		assertThat(pe.getPrice(), is(75.0));
				
		pe = ptList.get(1).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(1045.0));
		
		pe = ptList.get(1).getEntries().get(1);
		assertThat(pe.getAccount().getAccount().getName(), is("消費税"));
		assertThat(pe.getPrice(), is(52.0));
	}
	
	@Test
	public void 課税単位が安い方で合算の方が安いの場合() {
		// 注文内容の提示
		Order o = new Order(client2, DealType.販売, NonTaxType.課税, TaxablesType.社外, date("2013/8/1"),
				new OrderDetail(product1.getId(), 4.3, Unit.KG),
				new OrderDetail(product2.getId(), 6.7, Unit.KG));
		
		// 代金計算を行う
		List<PricingTransaction> ptList = o.calcPrice();
		
		// 商流取引の確認
		TradingTransaction ct = ptList.get(0).getBasis().getTransaction();		
		assertThat(ct.getEntries().size(), is(2));
		
		// 代金取引の確認
		assertThat(ptList.size(), is(3));
		
		PricingEntry pe = ptList.get(0).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(1174.0));
		
		pe = ptList.get(1).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(844.0));
		
		pe = ptList.get(2).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("消費税"));
		assertThat(pe.getPrice(), is(161.0));
	}
	
	@Test
	public void 課税単位が安い方で明細単位の方が安いの場合() {
		// 注文内容の提示
		Order o = new Order(client3, DealType.仕入, NonTaxType.課税, TaxablesType.社外, date("2013/8/1"),
				new OrderDetail(product1.getId(), 4.3, Unit.KG),
				new OrderDetail(product2.getId(), 6.7, Unit.KG));
		
		// 代金計算を行う
		List<PricingTransaction> ptList = o.calcPrice();
		
		// 商流取引の確認
		TradingTransaction ct = ptList.get(0).getBasis().getTransaction();		
		assertThat(ct.getEntries().size(), is(2));
		
		// 代金取引の確認
		assertThat(ptList.size(), is(2));
		
		PricingEntry pe = ptList.get(0).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(1173.0));
		
		pe = ptList.get(0).getEntries().get(1);
		assertThat(pe.getAccount().getAccount().getName(), is("消費税"));
		assertThat(pe.getPrice(), is(93.0));
		
		pe = ptList.get(1).getEntries().get(0);
		assertThat(pe.getAccount().getAccount().getName(), is("商品代金"));
		assertThat(pe.getPrice(), is(844.0));
		
		pe = ptList.get(1).getEntries().get(1);
		assertThat(pe.getAccount().getAccount().getName(), is("消費税"));
		assertThat(pe.getPrice(), is(67.0));
	}}
