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

import java.util.Date;

import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.contract.RoundingMethod;
import jp.co.isken.tax.domain.contract.TaxTerm;
import jp.co.isken.tax.domain.contract.ConsumptionCalculationBase;
import jp.co.isken.tax.domain.party.Party;

public class PartyContract {

	private Party party;
	private Contract contract;

	public PartyContract(int id) {
		this.party = Party.$find(id);
	}
	
	public PartyContract(Party party) {
		this.party = party;
	}
	
	public Contract getContract() {
		return contract;
	}

	public void makeContract(ContractType type, Date date, RoundingMethod priceRoundingMethod, RoundingMethod consumptionTaxRoundingMethod, ConsumptionCalculationBase calculationBase) {
		contract = new Contract(type.getContractType(), party.getId(), date, new TaxTerm(priceRoundingMethod, consumptionTaxRoundingMethod, calculationBase));
	}
}
