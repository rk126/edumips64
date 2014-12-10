package org.edumips64.core;

// import org.edumips64.core.*;
import java.util.*;
import java.lang.Math;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.edumips64.core.Register;
import org.edumips64.core.ShiftRegister;
import java.util.Iterator;
import java.util.Set;

public class HistoryTable {
    private HashMap<String, ShiftRegister> historyTableEntries;
    ShiftRegister currentShiftRegister;
    private double numberOfEntries;
    private int bufferSize;
    private static final Logger logger = Logger.getLogger(HistoryTable.class.getName());

    public HistoryTable(int bitSize) {
        // HistoryTable Constructor with bit-size parameter
        bufferSize = bitSize;
        // Initialize the hashMap
        historyTableEntries = new HashMap<String, ShiftRegister>();
        logger.setLevel(Level.ALL);
        logger.info("History Table Initialized");
        numberOfEntries = Math.pow(2, (double) bitSize);
    }

    public void updateEntryToLocalHistoryTable(String pc, ShiftRegister.branchDecision decision) {
        // Gets called whenever the 10 bit shift register gets updated
        // Adds a new entry if the key i.e. the pc, is not present with the current shift register value
        // Updates an existing key i.e. the pc, with the new value of the current shift register value
        if (historyTableEntries.isEmpty()) {
            // Create a new key-value pair
            logger.info("History Table empty, adding first entry");
            currentShiftRegister = new ShiftRegister(bufferSize);
            currentShiftRegister.addDecision(decision);
            historyTableEntries.put(pc, currentShiftRegister);
        }
        else {
            if (historyTableEntries.containsKey(pc)) {
                // Update existing key with the current Shift register value
                logger.info("Updating History Table");
                currentShiftRegister = historyTableEntries.get(pc);
                currentShiftRegister.addDecision(decision);
                historyTableEntries.put(pc, currentShiftRegister);
            }
            else {
                currentShiftRegister = new ShiftRegister(bufferSize);
                currentShiftRegister.addDecision(decision);
                historyTableEntries.put(pc, currentShiftRegister);
            }
        }
    }

    public String getDecisionBufferOf(String pc) {
        if (!historyTableEntries.isEmpty()) {
            if (historyTableEntries.containsKey(pc)) {
                String decisionBuffer = new String(historyTableEntries.get(pc).toBinString());
                return decisionBuffer;
            }
        }
        // Shouldn't reach here
        // String ret_str = new String("");
        // for (int i = 0; i < bufferSize; i++) {
        //     ret_str = ret_str.concat('X');
        // }
        return Character.toString('X');
    }

    public void printHistoryTable() {
        if (!(historyTableEntries.isEmpty())) {
            logger.info("Printing History Table");
            Set<Map.Entry<String, ShiftRegister>> set = historyTableEntries.entrySet();
            Iterator<Map.Entry<String, ShiftRegister>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, ShiftRegister> mEntry = iterator.next();
                System.out.print(mEntry.getKey() + " --> ");
                System.out.println(mEntry.getValue().toBinString());
            }
        }
        else {
            logger.warning("History table is empty, couldn't print the table");
        }
    }

    public int getSize() {
        return historyTableEntries.size();
    }

    public ShiftRegister getCurrentShiftRegister() {
        return currentShiftRegister;
    }

    // public String getLastTwoDecisionFromTable(String pc) {
    // if (!historyTableEntries.isEmpty()) {
    //         if (historyTableEntries.containsKey(pc)) {
    //             String sr = historyTableEntries.get(pc).toBinString();
    //             String ret_str = "";
    //             if (sr.length() > 2) {
    //                 ret_str = sr.substring((sr.length() - 2), sr.length());
    //                 return ret_str;
    //             }
    //         }
    //     }
    //     return "XX";
    // }
}
