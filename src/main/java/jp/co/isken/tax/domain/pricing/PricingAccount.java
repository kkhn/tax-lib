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

import java.util.ArrayList;
import java.util.List;

import jp.co.isken.tax.domain.party.Party;

public class PricingAccount {
	
	private Party party;
	private MemoAccount account;
	
	private static List<PricingAccount> $instances = new ArrayList<PricingAccount>();

	public PricingAccount(int partyId, MemoAccount account) {
		this.party = Party.$find(partyId);
		this.account = account;
	}
	
	public Party getParty() {
		return party;
	}
	
	public MemoAccount getAccount() {
		return account;
	}

	public static PricingAccount getAccount(int partyId, MemoAccount account) {
		for (PricingAccount a : $instances) {
			if (a.getParty().getId() == partyId &&
					a.getAccount().getId() == account.getId()) {
				return a;
			}
		}
		return new PricingAccount(partyId, account);
	}

}
