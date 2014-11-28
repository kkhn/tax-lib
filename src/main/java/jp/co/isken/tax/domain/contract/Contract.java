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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.domain.party.Party;

public class Contract {

	private int number;
	private Party party;
	private Date agreementDate;
	private TaxTerm taxTerm;
	private ContractType type;
	private static List<Contract> $instances = new ArrayList<Contract>();
	private static int $count = 0;

	public Contract(ContractType type, int partyId, Date agreementDate, TaxTerm term) {
		this.number = $count++;
		this.type = type;
		this.party = Party.$find(partyId);
		this.agreementDate = agreementDate;
		this.taxTerm = term;
		
		$instances.add(this);
	}
	
	public int getNumber() {
		return number;
	}
	
	public ContractType getType() {
		return type;
	}
	
	public Party getParty() {
		return party;
	}
	
	public Date getWhenAgreement() {
		return agreementDate;
	}
	
	public TaxTerm getTaxTerm() {
		return taxTerm;
	}
	
	public static List<Contract> instances() {
		return $instances;
	}
	
	public static List<Contract> $getAll() {
		return $instances;
	}

	public static Contract $find(int partyId, ContractType type) {
		for (Contract c : $instances) {
			if (c.getNumber() == partyId && c.getType() == type) {
				return c;
			}
		}
		return null;
	}

}
