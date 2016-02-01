package com.leetcode.anagram;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fang_j
 *
 */
public class ValidAnagram {
	/**
	 * Give two strings s and t, use isAnagram to determine if t is an anagram of s.
	 * @return boolean {@code true} if t is an anagram of s, otherwise {@code false}.
	 */
	public boolean isAnagram(final String s, final String t) {
		boolean isAnagram = false;

		if (s == t) {
			isAnagram = true;
		} else if ((s != null) && (t != null) && (s.length() == t.length())) {
			final Map<Character, Integer> counter = new HashMap<Character, Integer>();

			for (final char c : s.toCharArray()) {
				if (counter.containsKey(c)) {
					counter.put(c, counter.get(c) + 1);
				} else {
					counter.put(c, 1);
				}
			}

			for (final char c : t.toCharArray()) {
				if (counter.containsKey(c)) {
					final int count = counter.get(c);
					if (count > 1) {
						counter.put(c, count - 1);
					} else {
						counter.remove(c);
					}
				} else {
					break;
				}
			}

			isAnagram = counter.size() == 0;
		}

		return isAnagram;
	}
}