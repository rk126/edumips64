/*
 * FlowControlInstructions.java
 *
 * 15th may 2006
 * Subgroup of the MIPS64 Instruction Set
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
import org.edumips64.core.ShiftRegister;
import java.util.logging.Logger;

/**This is the base class for FlowControl instructions
 *
 * @author Trubia Massimo, Russo Daniele
 */
public abstract class FlowControlInstructions extends Instruction {
  protected static CPU cpu = CPU.getInstance();
  protected String instPC;
  protected ShiftRegister.branchDecision predictedDecision;
  private static final Logger logger = Logger.getLogger(FlowControlInstructions.class.getName());
  public void IF() {
    instPC = cpu.getLastPC().toString();
    // Predicting Whether this branch is taken or not
    if (cpu.getSelectGlobalFlag()) {
      logger.info("Predicting from Global Branch Predictor");
      predictedDecision = cpu.predictFromGlobalPatternTable();
    } else {
      logger.info("Predicting from Local Branch Predictor");
      predictedDecision = cpu.predictFromLocalPatternTable(instPC);
    }
    // cpu.printLocalTables()
    Dinero din = Dinero.getInstance();

    try {
      din.IF(Converter.binToHex(Converter.intToBin(64, cpu.getLastPC().getValue())));
    } catch (IrregularStringOfBitsException e) {
      e.printStackTrace();
    }
  }
  public abstract void ID() throws RAWException, IrregularWriteOperationException, IrregularStringOfBitsException, JumpException, TwosComplementSumException;
  public abstract void EX() throws IrregularStringOfBitsException, IntegerOverflowException, IrregularWriteOperationException;
  public abstract void MEM() throws IrregularStringOfBitsException, MemoryElementNotFoundException;
  public abstract void WB() throws IrregularStringOfBitsException;
  public abstract void pack() throws IrregularStringOfBitsException;

}
