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

import java.math.BigDecimal;
import java.util.Date;

import jp.co.isken.tax.domain.pricing.PricingBasis;
import jp.co.isken.tax.domain.product.Item;
import jp.co.isken.taxlib.application.ConsumptionTaxCalculatorFacade;
import jp.co.isken.taxlib.domain.calculator.ConsumptionTaxCalculator;
import jp.co.isken.taxlib.domain.rate.TaxItem;

public class TaxFacade {

	private PricingBasis basis;
	private double price;

	public TaxFacade(PricingBasis basis, double price) {
		this.basis = basis;
		this.price = price;
	}

	/**
	 * 消費税計算の実施
	 */
	public double doCalcTax() {
		// 契約がない、商品がない場合は0円で返す
		if (basis.getRoundingMethod() == null) {
			return 0;
		}
		if (basis.getItem() == null) {
			return 0;
		}
		
		// 課税期間
		Date date = basis.getWhenCharged();
		// まるめ方法
		String rule = basis.getRoundingMethod().name();
		// 品目の読み替え
		TaxItem item = loadItem(basis.getItem());
		
		ConsumptionTaxCalculatorFacade facade = new ConsumptionTaxCalculatorFacade(item, BigDecimal.valueOf(price), date, true, rule, "外税");
		ConsumptionTaxCalculator calculator = facade.getCalculator();
		return calculator.getConsumptionTaxAmountRounded().doubleValue();
	}
	
	/**
	 * 品目の読み替え
	 */
	private TaxItem loadItem(Item item) {
		return null;
	}
	
}
