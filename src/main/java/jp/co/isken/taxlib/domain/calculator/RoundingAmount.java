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

public enum RoundingAmount {
	切り捨て {
		public BigDecimal round(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_DOWN);
		}
	},
	切り上げ {
		public BigDecimal round(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_UP);
		}
	},
	四捨五入 {
		public BigDecimal round(BigDecimal d) {
			return d.setScale(0, BigDecimal.ROUND_HALF_UP);
		}
	};
	
	public abstract BigDecimal round(BigDecimal d);
}
