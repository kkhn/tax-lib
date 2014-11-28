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

public class PricingEntry {

	private PricingTransaction transaction;
	private PricingAccount account;
	private double price;

	public PricingEntry(PricingTransaction t, PricingAccount a, double price) {
		this.transaction = t;
		this.account = a;
		this.price = price;
		
		t.addEntries(this);
	}
	
	public PricingTransaction getTransaction() {
		return transaction;
	}
	
	public PricingAccount getAccount() {
		return account;
	}
	
	public double getPrice() {
		return price;
	}

}
