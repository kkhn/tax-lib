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

import java.math.BigDecimal;
import java.util.Date;

import jp.co.isken.taxlib.domain.calculator.RoundingAmount;
import jp.co.isken.taxlib.domain.calculator.ConsumptionTaxCalculator;
import jp.co.isken.taxlib.domain.calculator.CalculateConsumptionTax;
import jp.co.isken.taxlib.domain.rate.TaxItem;

public class ConsumptionTaxCalculatorFacade {
	
	private ConsumptionTaxCalculator calclator;

	public ConsumptionTaxCalculatorFacade(TaxItem item, BigDecimal price, Date targetDate, boolean isTaxed,
			String roundingRule, String calculationType) {
		RoundingAmount rounder = RoundingAmount.valueOf(roundingRule);
		CalculationType type  = CalculationType.valueOf(calculationType);
		
		// TODO それぞれがNULLの場合のエラー処理
		CalculateConsumptionTax calculateConsumptionTax = type.getCalculateConsumptionTax();
		
		this.calclator = new ConsumptionTaxCalculator(price, targetDate, item, isTaxed, calculateConsumptionTax, rounder);
	}
	
	public ConsumptionTaxCalculator getCalculator() {
		return calclator;
	}
	
}
