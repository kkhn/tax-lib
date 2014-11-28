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
package jp.co.isken.tax.domain.contract;

public class TaxTerm {
	
	private RoundingMethod priceRoundingMethod;
	private RoundingMethod consumptionRoundingMethod;
	private ConsumptionCalculationBase calculationBase;

	public TaxTerm(RoundingMethod priceRoundingMethod, RoundingMethod consumptionRoundingMethod, ConsumptionCalculationBase taxableUnit) {
		this.priceRoundingMethod = priceRoundingMethod;
		this.consumptionRoundingMethod = consumptionRoundingMethod;
		this.calculationBase = taxableUnit;
	}
	
	public RoundingMethod getPriceRoundingMethod() {
		return priceRoundingMethod;
	}
	
	public RoundingMethod getConsumptionRoundingMethod() {
		return consumptionRoundingMethod;
	}
	
	public ConsumptionCalculationBase getTaxableUnit() {
		return calculationBase;
	}
}
