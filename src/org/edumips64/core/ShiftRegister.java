/* The shift register forms the first level in storing the actual history of branch.
 * This value is found is then stored in the second level indexed by the PC of that
 * branch instruction */
/* Author - Ramakrishnan Kalyanaraman & Jigar Patel */
import java.util.LinkedList;

public class ShiftRegister {
    public enum branchDecision {Taken, NotTaken, Unknown}

    private LinkedList<branchDecision> shiftRegisterValue;

    public ShiftRegister(int bitSize) {
        shiftRegisterValue = new LinkedList<branchDecision>();
        ShiftRegisterInit (bitSize);
        printShiftRegister();
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
        System.out.println("Removing Element " + shiftRegisterValue.removeLast());
        printShiftRegister();
    }
}
