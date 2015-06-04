package com.extendbrain.getDatas;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.junit.Test;

import com.extendbrain.meituan.Data;
import com.extendbrain.meituan.GetDatas;

public class getDatasTest {
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Test
	public void testGetAllItems() throws Exception {
		List<Object> resultList = GetDatas.getAllItems();
		TreeMap<Data, Integer> treeMap = new TreeMap<Data, Integer>();
		for(Object object : resultList){
			Data data = (Data)object;
			treeMap.put(data, Integer.valueOf(data.getDeal().getSales_num()));
		}
		Iterator iterator = treeMap.keySet().iterator();
		for(int i = 0; i < 10;i ++){
			if(iterator.hasNext()){
				Data data = (Data)iterator.next();
				logger.info(data.getDeal().getDeal_name() + ": " + data.getDeal().getSales_num());
			}
		}
	}
}
