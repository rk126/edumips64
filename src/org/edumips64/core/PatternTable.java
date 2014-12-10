package org.edumips64.core;

import java.util.*;
import java.lang.Math;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.edumips64.core.ShiftRegister;
import java.util.Iterator;
import java.util.Set;

class PatternTable {
	private HashMap<String, SaturatingCounter> patternTableEntries;
	private double numberOfEntries;
	private int saturatingCounterBitSize;
	private int threshold;
	private int decisionBufferBitSize;
	private SaturatingCounter sc;
	private static final Logger logger = Logger.getLogger(PatternTable.class.getName());

	public PatternTable(int bitSize, int bufferSize) {
		saturatingCounterBitSize = bitSize;
		decisionBufferBitSize = bufferSize;
		numberOfEntries = Math.pow(2, (double) bitSize);
		threshold = (int) (((Math.pow(2, (double) saturatingCounterBitSize))/2) - 1);
		patternTableEntries = new HashMap<String, SaturatingCounter>();
		logger.info("Pattern Table Initialized");
	}

	// public void addEntryToPatternTable(String decisionBuffer) {
	// 	if (patternTableEntries.isEmpty()) {
	// 		logger.info("Adding First Entry to Pattern Table");
	// 		// If the decision buffer of size n contains n - 1 Unknown decisions we use a default saturating counter value
	// 		int count = 0;
	// 		String defaultValue = new String("");
	// 		String historyValue;
	// 		for (int i = 0; i < saturatingCounterBitSize; i++) {
	// 			defaultValue = defaultValue.concat('0');
	// 			if (decisionBuffer.charAt(decisionBufferBitSize - 1 - i) == 'X') {
	// 				count++;
	// 			}
	// 		}
	// 		if (count == 0) {
	// 			historyValue = new String(decisionBuffer.subString((decisionBufferBitSize - saturatingCounterBitSize), decisionBufferBitSize));
	// 			sc = new SaturatingCounter(saturatingCounterBitSize, historyValue);
	// 			patternTableEntries.put(decisionBuffer, sc);
	// 		} else {
	// 			sc = new SaturatingCounter(saturatingCounterBitSize, defaultValue);
	// 			patternTableEntries.put(decisionBuffer, sc);
	// 		}
	// 	} else {
	// 		if (!patternTableEntries.containsKey()) {
	// 			logger.info("Adding new Entry to Pattern Table");
	// 			// If the decision buffer of size n contains n - 1 Unknown decisions we use a default saturating counter value
	// 			int count = 0;
	// 			String defaultValue = new String("");
	// 			String historyValue;
	// 			for (int i = 0; i < saturatingCounterBitSize; i++) {
	// 				defaultValue = defaultValue.concat('0');
	// 				if (decisionBuffer.charAt(decisionBufferBitSize - 1 - i) == 'X') {
	// 					count++;
	// 				}
	// 			}
	// 			if (count == 0) {
	// 				historyValue = new String(decisionBuffer.subString((decisionBufferBitSize - saturatingCounterBitSize), decisionBufferBitSize));
	// 				sc = new SaturatingCounter(saturatingCounterBitSize, historyValue);
	// 				patternTableEntries.put(decisionBuffer, sc);
	// 			} else {
	// 				sc = new SaturatingCounter(saturatingCounterBitSize, defaultValue);
	// 				patternTableEntries.put(decisionBuffer, sc);
	// 			}
	// 		}
	// 	}
	// }

	public void updateEntryToPatternTable(String decisionBuffer, ShiftRegister.branchDecision decision) {
		if (!patternTableEntries.isEmpty()) {
			if (patternTableEntries.containsKey(decisionBuffer)) {
				sc = patternTableEntries.get(decisionBuffer);
				if (sc.getSaturatingCounter() > threshold) {
					ShiftRegister.branchDecision predictedDecision = ShiftRegister.branchDecision.Taken;
					if (predictedDecision == decision) {
						sc.incrementSaturatingCounter();
						patternTableEntries.put(decisionBuffer, sc);
					} else {
						sc.decrementSaturatingCounter();
						patternTableEntries.put(decisionBuffer, sc);
					}
				} else {
					ShiftRegister.branchDecision predictedDecision = ShiftRegister.branchDecision.NotTaken;
					if (predictedDecision == decision) {
						sc.decrementSaturatingCounter();
						patternTableEntries.put(decisionBuffer, sc);
					} else {
						sc.incrementSaturatingCounter();
						patternTableEntries.put(decisionBuffer, sc);
					}
				}
			} else {
				logger.info("Adding new Entry to Pattern Table");
				// If the decision buffer of size n contains n - 1 Unknown decisions we use a default saturating counter value
				int count = 0;
				String defaultValue = new String("");
				String historyValue;
				for (int i = 0; i < saturatingCounterBitSize; i++) {
					defaultValue = defaultValue + '0';
					if (decisionBuffer.charAt(decisionBufferBitSize - 1 - i) == 'X') {
						count++;
					}
				}
				if (count == 0) {
					historyValue = new String(decisionBuffer.substring((decisionBufferBitSize - saturatingCounterBitSize), decisionBufferBitSize));
					sc = new SaturatingCounter(saturatingCounterBitSize, historyValue);
					patternTableEntries.put(decisionBuffer, sc);
				} else {
					sc = new SaturatingCounter(saturatingCounterBitSize, defaultValue);
					patternTableEntries.put(decisionBuffer, sc);
				}
			}
		} else {
			logger.info("Adding First Entry to Pattern Table");
			// If the decision buffer of size n contains n - 1 Unknown decisions we use a default saturating counter value
			int count = 0;
			String defaultValue = new String("");
			String historyValue;
			for (int i = 0; i < saturatingCounterBitSize; i++) {
				defaultValue = defaultValue + '0';
				if (decisionBuffer.charAt(decisionBufferBitSize - 1 - i) == 'X') {
					count++;
				}
			}
			if (count == 0) {
				historyValue = new String(decisionBuffer.substring((decisionBufferBitSize - saturatingCounterBitSize), decisionBufferBitSize));
				sc = new SaturatingCounter(saturatingCounterBitSize, historyValue);
				patternTableEntries.put(decisionBuffer, sc);
			} else {
				sc = new SaturatingCounter(saturatingCounterBitSize, defaultValue);
				patternTableEntries.put(decisionBuffer, sc);
			}
		}
	}

	public void printPatternTable() {
        if (!(patternTableEntries.isEmpty())) {
            logger.info("Printing Pattern Table");
            Set<Map.Entry<String, SaturatingCounter>> set = patternTableEntries.entrySet();
            Iterator<Map.Entry<String, SaturatingCounter>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, SaturatingCounter> mEntry = iterator.next();
                System.out.print(mEntry.getKey() + " --> ");
                System.out.println(mEntry.getValue().getSaturatingCounter());
            }
        }
        else {
            logger.warning("Pattern table is empty, couldn't print the table");
        }
    }

	public ShiftRegister.branchDecision predictBranchDecision(String decisionBuffer) {
		if (!patternTableEntries.isEmpty()) {
			if (patternTableEntries.containsKey(decisionBuffer)) {
				sc = patternTableEntries.get(decisionBuffer);
				if (sc.getSaturatingCounter() > threshold) {
					return ShiftRegister.branchDecision.Taken;
				} else {
					return ShiftRegister.branchDecision.NotTaken;
				}
			}
		}
		return ShiftRegister.branchDecision.Unknown;
	}
}