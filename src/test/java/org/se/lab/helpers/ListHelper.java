package org.se.lab.helpers;

import java.util.List;

public class ListHelper {
	
	public static Boolean AnyContains(List<String> list, String lookup) {
		
		for(int i = 0; i < list.size(); i++) {
			String element = list.get(i);
			
			if(element == null || element.isEmpty())
				continue;
			
			if(element.toLowerCase().contains(lookup.toLowerCase()))
				return true;
		}
		
		return false;
	}
}
