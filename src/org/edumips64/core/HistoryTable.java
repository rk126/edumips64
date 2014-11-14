import java.util.*;
import java.lang.Math;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.edumips64.core.Register;

public class HistoryTable {
    private Map<Register, ShiftRegister> historyTableEntries;
    ShiftRegister currentShiftRegister;
    private double numberOfEntries;

    private static final Logger logger = Logger.getLogger(HistoryTable.class.getName());

    public HistoryTable(int bitSize) {
        // HistoryTable Constructor with bit-size parameter
        // Initialize the current shift register
        currentShiftRegister = new ShiftRegister(bitSize);
        // Initialize the hashTable
        historyTableEntries = new HashMap<Register, ShiftRegister>();
        logger.setLevel(Level.ALL);
        logger.info("History Table Initialized");
        numberOfEntries = Math.pow(2, (double) bitSize);
    }

    private void updateEntryToTable(Register pc) {
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
                // if !(historyTableEntries.get(pc).toBinString() == currentShiftRegister.toBinString()) {

                // }
            }
        }
    }
}
