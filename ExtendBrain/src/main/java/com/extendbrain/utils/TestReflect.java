package com.extendbrain.utils;

import java.lang.reflect.Field;


public class TestReflect {
	public final static String id = "123";
	
	public static void printId(){
		System.out.println(id);
	}
	public static void main(String[] args) {
		Class<?> v = TestReflect.id.getClass();
		try {
			Field vField = v.getDeclaredField("value");
//			Field lField = v.getDeclaredField("count");
			vField.setAccessible(true);
//			lField.setAccessible(true);
			
			Object object = vField.get(TestReflect.id);
			
			char [] charValue = {'s','u','c','c','e','s','s'};

//			lField.set(TestReflect.id, charValue.length);
			vField.set(TestReflect.id, charValue);
			
			TestReflect.printId();
			TestReflect instance = new TestReflect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
