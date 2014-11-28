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
import jp.co.isken.taxlib.domain.rate.TaxRate;

public enum CalculateConsumptionTax {
	外税 {
		@Override
		public BigDecimal calcConsumptionTax(Date effectiveDate,
				BigDecimal price, TaxItem item, boolean isTaxed) {
			TaxRate rate = findRate(item, effectiveDate);
			BigDecimal number = BigDecimal.valueOf(rate.getRate());
			BigDecimal p = BigDecimal.valueOf(100);
			
			return price.multiply(number).divide(p);
		}
	},
	内税 {
		@Override
		public BigDecimal calcConsumptionTax(Date effectiveDate,
				BigDecimal price, TaxItem item, boolean isTaxed) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	非課税 {
		@Override
		public BigDecimal calcConsumptionTax(Date effectiveDate,
				BigDecimal price, TaxItem item, boolean isTaxed) {
			return BigDecimal.ZERO;
		}
	};
	
	public TaxRate findRate(TaxItem item, Date effectiveDate) {
		return TaxRate.getTaxRate(item, effectiveDate);
	}
	
	public abstract BigDecimal calcConsumptionTax(Date effectiveDate, BigDecimal price, TaxItem item, boolean isTaxed);

}
