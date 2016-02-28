package org.elsys.homeworks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.System;

public class SystemPropertiesFetcher {
	List<String> fetch(String[] args, String sortExpression) {
		List<String> props = new ArrayList<String>();
		
		for (String s : args) {
			String currentProp = System.getProperty(s);
			if (currentProp != null) {
				props.add(currentProp);
			}
		}
		
		if (sortExpression.equals("down")) {
			Collections.sort(props, Collections.reverseOrder());
		} else {
			Collections.sort(props);
		}
		
		return props;
	}
}