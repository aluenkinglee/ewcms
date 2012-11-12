package com.ewcms.plugin.visit.util;

import java.text.DecimalFormat;

/**
 * 
 * @author wu_zhijun
 *
 */
public class NumberUtil {
	/**
	 * 百分比
	 * 
	 * @param number 除数
	 * @param beNumber 被除数
	 * @return String
	 */
	public static String percentage(Integer number, Integer beNumber){
		String rate = "0.00%";
		try{
			if (beNumber != 0) {
				DecimalFormat df = new DecimalFormat("#0.00%");
				rate = df.format((number*1.0)/(beNumber*1.0));
			}
		} catch (Exception e){
		}
		return rate;
	}
}
