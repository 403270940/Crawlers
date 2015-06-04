package com.extendbrain.beans;

import java.util.HashMap;
import java.util.Map;

public class District {
	/*
	 * 朝阳 海淀 东城 西城 崇文 
	 * 宣武 丰台 通州 石景山 房山 
	 * 昌平 大兴 顺义 密云 怀柔 
	 * 延庆 平谷 门头沟 燕郊 北京周边 
	 */
	public static Map<String, String> districtMap = new HashMap<String, String>();
	
	static{
		districtMap.put("朝阳", "chaoyang");
		districtMap.put("海淀", "haidian");
		districtMap.put("东城", "dongcheng");
		districtMap.put("西城", "xicheng");
		districtMap.put("崇文", "chongwen");
		districtMap.put("宣武", "xuanwu");
		districtMap.put("丰台", "fengtai");
		districtMap.put("通州", "tongzhou");
		districtMap.put("石景山", "shijingshan");
		districtMap.put("房山", "fangshan");
		districtMap.put("昌平", "changping");
		districtMap.put("大兴", "daxing");
		districtMap.put("顺义", "shunyi");
		districtMap.put("密云", "miyun");
		districtMap.put("怀柔", "huairou");
		districtMap.put("延庆", "yanqing");
		districtMap.put("平谷", "pinggu");
		districtMap.put("门头沟", "mentougou");
		districtMap.put("燕郊", "bjyanjiao");
		districtMap.put("北京周边", "beijingzhoubian");
	}
	
	public static void getChaoyang(){
		
	}

}
