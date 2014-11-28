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

import jp.co.isken.tax.domain.contract.ContractType;

public enum DealType {
	販売 {
		@Override
		public jp.co.isken.tax.domain.trading.TransactionType getDealType() {
			return jp.co.isken.tax.domain.trading.TransactionType.販売;
		}
		@Override
		public jp.co.isken.tax.domain.contract.ContractType getContractType() {
			return jp.co.isken.tax.domain.contract.ContractType.販売;
		}
	}, 
	仕入 {
		@Override
		public jp.co.isken.tax.domain.trading.TransactionType getDealType() {
			return jp.co.isken.tax.domain.trading.TransactionType.仕入;
		}
		@Override
		public jp.co.isken.tax.domain.contract.ContractType getContractType() {
			return jp.co.isken.tax.domain.contract.ContractType.仕入;
		}
	};
	
	public jp.co.isken.tax.domain.trading.TransactionType getDealType() {
		return null;
	}

	public ContractType getContractType() {
		return null;
	}

}
