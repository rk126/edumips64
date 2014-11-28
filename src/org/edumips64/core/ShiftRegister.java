/* The shift register forms the first level in storing the actual history of branch.
 * This value is found is then stored in the second level indexed by the PC of that
 * branch instruction */
/* Author - Ramakrishnan Kalyanaraman & Jigar Patel */

package org.edumips64.core;

import java.util.LinkedList;

public class ShiftRegister {

    public enum branchDecision {Taken, NotTaken, Unknown}
    private LinkedList<branchDecision> shiftRegisterValue;
    private int shiftRegisterSize;

    public ShiftRegister(int bitSize) {
        shiftRegisterValue = new LinkedList<branchDecision>();
        shiftRegisterSize = bitSize;
        ShiftRegisterInit (bitSize);
        // printShiftRegister();
    }

    private void ShiftRegisterInit (int bitSz) {
        for (int i = 0; i < bitSz; i++) {
            shiftRegisterValue.add(i, branchDecision.Unknown);
        }
    }

    void printShiftRegister() {
        int shiftRegisterSize = shiftRegisterValue.size();
        System.out.println("Size of the shift register is " + shiftRegisterSize);

        for (int i = 0; i < shiftRegisterSize; i++)
            System.out.println(shiftRegisterValue.get(i));
    }

    void addDecision(branchDecision decision) {
        if (!shiftRegisterValue.isEmpty()) {
            shiftRegisterValue.push(decision);
        }
        shiftRegisterValue.removeLast();
        // printShiftRegister();
    }

    void removeDecision() {
        if (!shiftRegisterValue.isEmpty()) {
            shiftRegisterValue.pop();
        }
        shiftRegisterValue.addLast(branchDecision.Unknown);
        // printShiftRegister();
    }

    String toBinString() {
        String binString = new String("");
        for (int i = 0; i < shiftRegisterSize; i++) {
            if (shiftRegisterValue.get(i) == branchDecision.Unknown) {
                binString = "X".concat(binString);
            }
            else if (shiftRegisterValue.get(i) == branchDecision.Taken) {
                binString = "1".concat(binString);
            }
            else if (shiftRegisterValue.get(i) == branchDecision.NotTaken) {
                binString = "0".concat(binString);
            }
        }
        return binString;
    }
}
