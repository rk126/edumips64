/*
 * BEQ.java
 *
 * 8th may 2006
 * Instruction BEQ of the MIPS64 Instruction Set
 * (c) 2006 EduMips64 project - Trubia Massimo, Russo Daniele
 *
 * This file is part of the EduMIPS64 project, and is released under the GNU
 * General Public License.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.edumips64.core.is;
import org.edumips64.core.*;
import org.edumips64.utils.*;
/** <pre>
 *         Syntax: BEQ rs, rt, offset
 *    Description: if rs = rt then branch
 *                 To compare GPRs then do a PC-relative conditional branch
 *</pre>
  * @author Trubia Massimo, Russo Daniele
 */

public class BEQ extends FlowControl_IType {
  final String OPCODE_VALUE = "000100";

  /** Creates a new instance of BEQ */
  public BEQ() {
    super.OPCODE_VALUE = OPCODE_VALUE;
    syntax = "%R,%R,%B";
    name = "BEQ";
  }

  public void ID() throws RAWException, IrregularWriteOperationException, IrregularStringOfBitsException, JumpException, TwosComplementSumException {
    if (cpu.getRegister(params.get(RS_FIELD)).getWriteSemaphore() > 0 || cpu.getRegister(params.get(RT_FIELD)).getWriteSemaphore() > 0) {
      throw new RAWException();
    }

    //getting registers rs and rt
    String rs = cpu.getRegister(params.get(RS_FIELD)).getBinString();
    String rt = cpu.getRegister(params.get(RT_FIELD)).getBinString();
    //converting offset into a signed binary value of 64 bits in length
    BitSet64 bs = new BitSet64();
    bs.writeHalf(params.get(OFFSET_FIELD));
    String offset = bs.getBinString();
    boolean condition = rs.equals(rt);

    if (condition) {
      // Updating Local Shift Register

      cpu.updateLocalHistoryTable(instPC, ShiftRegister.branchDecision.Taken);
      cpu.updateGlobalHistoryRegister(ShiftRegister.branchDecision.Taken);

      if (predictedDecision != ShiftRegister.branchDecision.Unknown) {
          cpu.predictionKnown++;
          if (predictedDecision == ShiftRegister.branchDecision.Taken) {
              cpu.predictionSuccessful++;
              if (cpu.getSelectGlobalFlag() == true) {
                cpu.decrementPredictorSelectionCounter();
              } else {
                cpu.incrementPredictorSelectionCounter();
              }
          } else {
              cpu.predictionUnsuccessful++;
              if (cpu.getSelectGlobalFlag() == true) {
                cpu.incrementPredictorSelectionCounter();
              } else {
                cpu.decrementPredictorSelectionCounter();
              }
          }
      } else {
          cpu.predictionUnknown++;
      }

      String pc_new = "";
      Register pc = cpu.getPC();
      String pc_old = cpu.getPC().getBinString();

      // System.out.println("PC is " + pc.toString());

      // System.out.println("Old PC is " + cpu.getLastPC().toString());

      //subtracting 4 to the pc_old temporary variable using bitset64 safe methods
      BitSet64 bs_temp = new BitSet64();
      bs_temp.writeDoubleWord(-4);
      pc_old = InstructionsUtils.twosComplementSum(pc_old, bs_temp.getBinString());

      //updating program counter
      pc_new = InstructionsUtils.twosComplementSum(pc_old, offset);
      pc.setBits(pc_new, 0);
      // System.out.println("PC after adding offset is " + pc.toString());

      throw new JumpException();
    }
    else {
        // Updating Local Shift Register
        cpu.updateLocalHistoryTable(instPC, ShiftRegister.branchDecision.NotTaken);
        cpu.updateGlobalHistoryRegister(ShiftRegister.branchDecision.NotTaken);
        // Checking if the predicted decision is in accordance of what got taken
        if (predictedDecision != ShiftRegister.branchDecision.Unknown) {
          cpu.predictionKnown++;
          if (predictedDecision == ShiftRegister.branchDecision.NotTaken) {
              cpu.predictionSuccessful++;
              if (cpu.getSelectGlobalFlag() == true) {
                cpu.decrementPredictorSelectionCounter();
              } else {
                cpu.incrementPredictorSelectionCounter();
              }
          } else {
              cpu.predictionUnsuccessful++;
              if (cpu.getSelectGlobalFlag() == true) {
                cpu.incrementPredictorSelectionCounter();
              } else {
                cpu.decrementPredictorSelectionCounter();
              }
          }
      } else {
          cpu.predictionUnknown++;
      }
    }
    // Adding decision to the Local History Table
    }
}
