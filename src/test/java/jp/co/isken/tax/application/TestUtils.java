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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.isken.tax.domain.contract.Contract;
import jp.co.isken.tax.domain.pricing.PricingEntry;
import jp.co.isken.tax.domain.pricing.PricingTransaction;
import jp.co.isken.tax.domain.trading.TradingEntry;
import jp.co.isken.tax.domain.trading.TradingTransaction;
import jp.co.isken.taxlib.domain.rate.TaxRate;

public class TestUtils {
	private static final String DATE_PATTERN = "yyyy年MM月dd日";

	public static Date date(String dateStr) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
	}
	
	public static void print(Order o) {
		Contract contract = o.getContract();
		
		String str = "";
		str += "== 契約 ==";
		str += "\n契約先：" + contract.getParty().getName();
		str += "\n代金条項_まるめ方法：" + contract.getTaxTerm().getPriceRoundingMethod();
		str += "\n消費税条項_まるめ方法：" + contract.getTaxTerm().getConsumptionRoundingMethod();
		str += "\n消費税条項_課税単位：" + contract.getTaxTerm().getTaxableUnit();
		
		System.out.println(str);
	}
	
	public static void print(TradingTransaction ct) {
		
		String str = "";
		str += "==注文==";
		str += "\n取引日：" + string(ct.getWhenCharged());
		
		str += "\n==注文明細==";
		int count = 1;
		for (TradingEntry e : ct.getEntries()) {
			str += "\n明細(" + count + ") -- ";
			str += e.getAccount().getProduct().getName() + " （単価）" + 
					e.getAccount().getProduct().getUnitPrice().getPrice() + "円";
			str += "\n注文数：" + e.getQuantity() + e.getUnit();
			
			count++;
		}
		
		System.out.println(str);
	}
	
	private static String string(Date date) {
		return (new SimpleDateFormat(DATE_PATTERN)).format(date);
	}

	public static void print(TaxRate rate) {
		String str = "";
		
		str += "==消費税率==";
		str += "\n" + rate.getRate() + "%";

		System.out.println(str);
	}
	
	public static void print(List<PricingTransaction> pts) {
		String str = "";
		
		str += "==代金計算結果==";
		for (PricingTransaction pt : pts) {
			String productName;
			if (pt.getTradingTransaction() != null) {
				productName = pt.getTradingTransaction().getEntries().get(0).getAccount().getProduct().getName();
			} else {
				productName = pt.getTradingEntry().getAccount().getProduct().getName();
			}
			for (PricingEntry pe : pt.getEntries()) {
				if (pe.getAccount().getAccount().getName().equals("商品代金")) {
					str += "\n" + productName + " 金額：" + pe.getPrice() + "円";
				} else if(pe.getAccount().getAccount().getName().equals("消費税")) {
					str += "\n消費税：" + pe.getPrice() + "円";
				}
			}
		}
		System.out.println(str);
	}
	
	public static void print(String str) {
		System.out.println(str);
	}
	
}
