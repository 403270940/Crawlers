package com.extendbrain.meituan;

import java.util.ArrayList;
import java.util.List;

public class Data implements Comparable<Object>{
	// private String test;
	// public void setTest(String test){
	// this.test = test;
	// }
	// public String getTest(){
	// return test;
	// }
	private Deal deal;
	private List<Shop> shops = new ArrayList<Shop>();

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public void addShop(Shop shop) {
		shops.add(shop);
	}

	@Override
	public String toString() {
		return "Data [deal=" + deal + ", shops=" + shops + "]";
	}
	
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Data data = (Data)o;
		return Integer.valueOf(data.getDeal().getSales_num())
				.compareTo(Integer.valueOf(this.getDeal().getSales_num()));
	}


}
