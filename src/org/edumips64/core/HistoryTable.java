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
    private Map<Register, ShiftRegister> historyTableEntries;
    ShiftRegister currentShiftRegister;
    private double numberOfEntries;

    private static final Logger logger = Logger.getLogger(HistoryTable.class.getName());

    public HistoryTable(int bitSize) {
        // HistoryTable Constructor with bit-size parameter
        // Initialize the current shift register
        currentShiftRegister = new ShiftRegister(bitSize);
        // Initialize the hashMap
        historyTableEntries = new HashMap<Register, ShiftRegister>();
        logger.setLevel(Level.ALL);
        logger.info("History Table Initialized");
        numberOfEntries = Math.pow(2, (double) bitSize);
    }

    public void updateEntryToTable(Register pc) {
        // Gets called whenever the 10 bit shift register gets updated
        // Adds a new entry if the key i.e. the pc, is not present with the current shift register value
        // Updates an existing key i.e. the pc, with the new value of the current shift register value
        if (historyTableEntries.isEmpty()) {
            // Create a new key-value pair
            historyTableEntries.put(pc, currentShiftRegister);
        }
        else {
            if (historyTableEntries.containsKey(pc)) {
                // Update existing key with the current Shift register value
                if (!(historyTableEntries.get(pc).toBinString() == currentShiftRegister.toBinString())) {
                    logger.info("Updating History Table");
                    historyTableEntries.put(pc, currentShiftRegister);
                }
                else {
                    logger.info("History table entry already updated with latest register/shift register pair");
                }
            }
            else {
                // Warning History table doesn't contain any entry
                logger.warning("Failed to update History Table, as it doesn't contain any entry");
            }
        }
    }

    public void addDecisionToShiftRegister(ShiftRegister.branchDecision decision) {
        currentShiftRegister.addDecision(decision);
    }

    public void printHistoryTable() {
        if (!(historyTableEntries.isEmpty())) {
            logger.info("Printing History Table");
            Set<Map.Entry<Register, ShiftRegister>> set = historyTableEntries.entrySet();
            Iterator<Map.Entry<Register, ShiftRegister>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<Register, ShiftRegister> mEntry = iterator.next();
                System.out.print(mEntry.getKey().toString() + " --> ");
                System.out.println(mEntry.getValue().toBinString());
            }
        }
        else {
            logger.warning("History table is empty, couldn't print the table");
        }
    }
}
