/* The saturating counter */

// package org.edumips64.core;

import java.lang.Math;

class SaturatingCounter {
	private Integer saturatingCounterValue;
	private Integer saturatingCounterSize;
	private Integer mask;
	private Integer lowerBound;
	private Integer upperBound;

	public SaturatingCounter (int bitSize, String init_value) {
		saturatingCounterSize = new Integer(bitSize);
		mask = new Integer((1 << bitSize) - 1);
		lowerBound = new Integer((0 << bitSize) & mask);
		upperBound = new Integer(~lowerBound & mask);
		if (init_value.length() == bitSize) {
			saturatingCounterValue = new Integer(Integer.parseInt(init_value) & mask);
			System.out.println("Initial Saturating counter value: " + saturatingCounterValue.toString());
		}
		else {
			System.out.println("Please check the bit size of the initial value to be loaded in the saturating counter");
		}
		// System.out.println(lowerBound.toString());
		// System.out.println(upperBound.toString());
	}

	public Integer incrementSaturatingCounter() {
		if (saturatingCounterValue.compareTo(upperBound) == 0) {
			return new Integer(saturatingCounterValue & mask);
		} else if (saturatingCounterValue.compareTo(upperBound) < 0) {
			return new Integer(++saturatingCounterValue & mask); 
		} else {
			System.out.println("Control Shouldn't reach here: " + saturatingCounterValue.toString());
			return new Integer(-1);
		}
	}

	public Integer decrementSaturatingCounter() {
		if (saturatingCounterValue.compareTo(lowerBound) == 0) {
			return new Integer(saturatingCounterValue);
		} else if (saturatingCounterValue.compareTo(lowerBound) > 0) {
			return new Integer(--saturatingCounterValue);
		} else {
			System.out.println("Control Shouldn't reach here: " + saturatingCounterValue.toString());
			return new Integer(-1);
		}
	}
}