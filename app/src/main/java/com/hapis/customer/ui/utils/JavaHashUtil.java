package com.hapis.customer.ui.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JavaHashUtil {
	
	public static int getHashKey(String selectedItem, Hashtable<String, String> hashtable)
	{
		Enumeration<String> keys = hashtable.keys();
		 int key = -1; 
		while( keys.hasMoreElements() ) {
			String keyStr = keys.nextElement();
			String value = hashtable.get(keyStr);
			if(value.trim().equalsIgnoreCase(selectedItem.trim())){
				key = Integer.parseInt(keyStr);
				break;
			}
		}
		
		return key;
	}
	
	public static String getStringHashKey(String selectedItem, Hashtable<String, String> hashtable)
	{
		Enumeration<String> keys = hashtable.keys();
		 String key = ""; 
		while( keys.hasMoreElements() ) {
			String keyStr = keys.nextElement();
			String value = hashtable.get(keyStr);
			if(value.trim().equalsIgnoreCase(selectedItem.trim())){
				key = keyStr;
				break;
			}
		}
		return key;
	}
	
	public static int getHashIntegerKey(String selectedItem, HashMap<Integer, String> hashMap)
	{
		Integer selectedProductTypeId = Integer.valueOf(0);

		for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
			if (entry.getValue().equals(selectedItem)) {
				selectedProductTypeId = entry.getKey();
			}
		}

		return selectedProductTypeId;
	}
	
	public static int getSelectedItemIndex(List<String> listArray, String selectedItem) {
		
		int selectedIndex = 0;
		
		if(listArray != null && listArray.size() != 0 && selectedItem != null)
		{
			if(listArray.contains(selectedItem))
			{
				for (int i = 0; i < listArray.size(); i++) {
					if(listArray.get(i).equals(selectedItem))
					{
						selectedIndex = i;
						break;
					}
				}	
			}
		}
		
		return selectedIndex;
	}


	public static Long getHashMapKey(HashMap<Long, String> hashMap, String selectedValue) {
		Long selectedProductTypeId = Long.valueOf(0);

		for (Map.Entry<Long, String> entry : hashMap.entrySet()) {
			if (entry.getValue().equals(selectedValue)) {
				selectedProductTypeId = entry.getKey();
			}
		}

		return selectedProductTypeId;
	}

	public static String getHashMapStringKey(HashMap<String, String> hashMap, String selectedValue) {
		String selectedProductTypeId = "";

		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
			if (entry.getValue().equals(selectedValue)) {
				selectedProductTypeId = entry.getKey();
			}
		}

		return selectedProductTypeId;
	}

	public static int getIndexForHashMap(String[] valuesArray, String selectedValue) {

		int selectedIndex = 0;

		for(int i = 0; i < valuesArray.length; i++)
		{
			if(valuesArray[i].equals(selectedValue))
			{
				selectedIndex = i;
				break;
			}
		}
		return selectedIndex;
	}
}
