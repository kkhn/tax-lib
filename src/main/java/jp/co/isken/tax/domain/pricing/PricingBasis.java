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
package jp.co.isken.tax.domain.pricing;

import java.util.Date;

import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.product.Item;
import jp.co.isken.tax.domain.trading.TradingTransaction;

public interface PricingBasis {
	
	public RoundingMethod getRoundingMethod();
	public Item getItem();
	public Date getWhenCharged();
	public TradingTransaction getTransaction();

}
