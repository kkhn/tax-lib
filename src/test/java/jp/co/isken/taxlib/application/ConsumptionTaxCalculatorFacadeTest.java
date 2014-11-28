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
package jp.co.isken.taxlib.application;

import static jp.co.isken.taxlib.application.TestUtils.date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import jp.co.isken.taxlib.domain.calculator.ConsumptionTaxCalculator;
import jp.co.isken.taxlib.domain.rate.TaxRate;
import jp.co.isken.taxlib.domain.rate.TaxRateType;
import jp.co.isken.taxlib.domain.rate.Unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsumptionTaxCalculatorFacadeTest {
	
	@Before
	public void setUp() {
		new TaxRate(TaxRateType.一般税率, null, date("1997/4/1"), date("2014/3/31"), 5, Unit.PERCENT);
		new TaxRate(TaxRateType.一般税率, null, date("2014/4/1"), date("2015/9/30"), 8, Unit.PERCENT);
	}
	
	@After
	public void tearDown() {
		TaxRate.$instances.clear();
	}

	@Test
	public void 外税計算できること_プラスの場合() {
		BigDecimal price = BigDecimal.valueOf(2546);
		
		ConsumptionTaxCalculatorFacade facade = new ConsumptionTaxCalculatorFacade(null, price, date("2013/7/30"), true, "切り捨て", "外税");
		ConsumptionTaxCalculator calculator = facade.getCalculator();
		assertThat(calculator.getConsumptionTaxAmountRounded().doubleValue(), is(127.0));
		
		facade = new ConsumptionTaxCalculatorFacade(null, price, date("2014/4/1"), true, "切り捨て", "外税");
		calculator = facade.getCalculator();
		assertThat(calculator.getConsumptionTaxAmountRounded().doubleValue(), is(203.0));
	}
	
	@Test
	public void 外税計算できること_マイナスの場合() {
		BigDecimal price = BigDecimal.valueOf(-136);
		
		ConsumptionTaxCalculatorFacade facade = new ConsumptionTaxCalculatorFacade(null, price, date("2013/7/30"), true, "切り捨て", "外税");
		ConsumptionTaxCalculator calculator = facade.getCalculator();
		assertThat(calculator.getConsumptionTaxAmountRounded().doubleValue(), is(-6.0));
	}
	
	@Test
	public void 非課税の場合は0円であること() {
		BigDecimal price = BigDecimal.valueOf(100);
		
		ConsumptionTaxCalculatorFacade facade = new ConsumptionTaxCalculatorFacade(null, price, date("2013/7/30"), true, "切り捨て", "非課税");
		ConsumptionTaxCalculator calculator = facade.getCalculator();
		assertThat(calculator.getConsumptionTaxAmountRounded().doubleValue(), is(0.0));
	}
	
}
