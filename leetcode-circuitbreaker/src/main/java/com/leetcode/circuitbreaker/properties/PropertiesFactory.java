package com.leetcode.circuitbreaker.properties;

import java.util.concurrent.ConcurrentHashMap;

import com.leetcode.key.Key;

public class PropertiesFactory {
	private static final ConcurrentHashMap<String, Properties> commandProperties = new ConcurrentHashMap<String, Properties>();

	public static Properties getCommandProperties(final Key commandKey, final PropertiesSetter setter) {
		if (commandKey == null) {
			throw new IllegalArgumentException("commandKey");
		}

		Properties properties = commandProperties.get(commandKey.getName());
		if (properties == null) {
			if (setter == null) {
				properties = new DefaultProperties();
			} else {
				properties = new CustomProperties(setter);
			}

			final Properties existedProperties =  commandProperties.putIfAbsent(commandKey.getName(), properties);

			if (existedProperties != null) {
				properties = existedProperties;
			}
		}

		return properties;
	}
}
