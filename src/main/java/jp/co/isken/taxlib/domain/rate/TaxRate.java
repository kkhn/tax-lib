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
package jp.co.isken.taxlib.domain.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaxRate {
	private TaxItem taxItem;
	private Date startedDate;
	private Date endedDate;
	private int rate;
	private Unit unit;
	
	public static List<TaxRate> $instances = new ArrayList<TaxRate>();

	public TaxRate(TaxRateType type, TaxItem item, Date startedDate, Date endedDate, int rate, Unit unit) {
		this.taxItem = item;
		this.startedDate = startedDate;
		this.endedDate = endedDate;
		this.rate = rate;
		this.unit = unit;
		
		$instances.add(this);
	}
	
	public TaxItem getTaxItem() {
		return taxItem;
	}
	
	public Date getStartedDate() {
		return startedDate;
	}
	
	public Date getEndedDate() {
		return endedDate;
	}
	
	public int getRate() {
		return rate;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public static TaxRate getTaxRate(TaxItem item, Date date) {
		for (TaxRate t : $instances) {
			if (t.getTaxItem() == item &&
					t.getStartedDate().compareTo(date) <= 0 &&
						t.getEndedDate().compareTo(date) >= 0 ) {
				return t;
			}
		}
		return null;
	}
}
