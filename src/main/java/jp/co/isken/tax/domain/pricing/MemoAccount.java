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


public class MemoAccount {

	private int id;
	private String name;
	
	private static int $count = 0;
	private static List<MemoAccount> $instances = new ArrayList<MemoAccount>();

	public MemoAccount(String name) {
		this.id = $count++;
		this.name = name;
		
		$instances.add(this);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public static MemoAccount getAccount(String name) {
		for (MemoAccount a : $instances) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return new MemoAccount(name);
	}

}
