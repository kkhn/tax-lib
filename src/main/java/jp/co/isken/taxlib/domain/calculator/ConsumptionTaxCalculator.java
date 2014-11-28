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
package jp.co.isken.taxlib.domain.calculator;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.isken.taxlib.domain.rate.TaxItem;

public class ConsumptionTaxCalculator {
	
	private BigDecimal price;
	private Date effectiveDate;
	private TaxItem item;
	private boolean isTaxed;
	private CalculateConsumptionTax calculator;
	private RoundingAmount rounder;
	
	public ConsumptionTaxCalculator(BigDecimal price, Date effectiveDate, TaxItem item, boolean isTaxed, CalculateConsumptionTax calculator, RoundingAmount rounder) {
		this.price = price;
		this.effectiveDate = effectiveDate;
		this.item = item;
		this.isTaxed = isTaxed;
		this.calculator = calculator;
		this.rounder = rounder;
	}

	public BigDecimal getConsumptionTaxAmount() {
		return calculator.calcConsumptionTax(effectiveDate, price, item, isTaxed);
	}
	
	public BigDecimal getConsumptionTaxAmountRounded() {
		return rounder.round(getConsumptionTaxAmount());
	}
}
