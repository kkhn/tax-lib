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
package jp.co.isken.tax.domain.trading;

import java.util.ArrayList;
import java.util.List;

import jp.co.isken.tax.domain.party.Party;
import jp.co.isken.tax.domain.product.Product;

public class TradingAccount {
	
	private Party party;
	private Product product;
	
	public static List<TradingAccount> $instances = new ArrayList<TradingAccount>();

	public TradingAccount(int partyId, Product product) {
		this.party = Party.$find(partyId);
		this.product = product;
		
		$instances.add(this);
	}
	
	public Party getParty() {
		return party;
	}
		
	public Product getProduct() {
		return product;
	}

	public static TradingAccount getAccount(int partyId, Product product) {
		TradingAccount find = $find(partyId, product);
		if (find == null) {
			return new TradingAccount(partyId, product);
		}
		return find;
	}

	private static TradingAccount $find(int partyId, Product product) {
		for (TradingAccount a : $instances) {
			if (a.getParty().getId() == partyId && 
					a.getProduct().getId() == product.getId()) {
				return a;
			}
		}
		return null;
	}

}
