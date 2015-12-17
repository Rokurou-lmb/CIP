package praktikum3;

import choco.Options;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;
import choco.Choco;

public class ChocoSymbolraetselSpaltenweiseProgram {
	public static void main(String args[]) {
		// Build model
		Model model = new CPModel();
		// Declare every letter as a variable
		IntegerVariable d = Choco.makeIntVar("d", 0, 9, Options.V_ENUM);
		IntegerVariable o = Choco.makeIntVar("o", 0, 9, Options.V_ENUM);
		IntegerVariable n = Choco.makeIntVar("n", 0, 9, Options.V_ENUM);
		IntegerVariable a = Choco.makeIntVar("a", 0, 9, Options.V_ENUM);
		IntegerVariable l = Choco.makeIntVar("l", 0, 9, Options.V_ENUM);
		IntegerVariable g = Choco.makeIntVar("g", 0, 9, Options.V_ENUM);
		IntegerVariable e = Choco.makeIntVar("e", 0, 9, Options.V_ENUM);
		IntegerVariable r = Choco.makeIntVar("r", 0, 9, Options.V_ENUM);
		IntegerVariable b = Choco.makeIntVar("b", 0, 9, Options.V_ENUM);
		IntegerVariable t = Choco.makeIntVar("t", 0, 9, Options.V_ENUM);
		// Declare every name as a variable
		IntegerVariable donald = Choco.makeIntVar("donald", 0, 1000000,Options.V_BOUND);
		IntegerVariable gerald = Choco.makeIntVar("gerald", 0, 1000000,Options.V_BOUND);
		IntegerVariable robert = Choco.makeIntVar("robert", 0, 1000000,Options.V_BOUND);
		// Array of coefficients
		int[] c = new int[]{100000, 10000, 1000, 100, 10, 1};
		// Declare every combination of letter as an integer expression
		IntegerExpressionVariable donaldLetters = Choco.scalar(new IntegerVariable[]{d,o,n,a,l,d}, c);
		IntegerExpressionVariable geraldLetters = Choco.scalar(new IntegerVariable[]{g,e,r,a,l,d}, c);
		IntegerExpressionVariable robertLetters = Choco.scalar(new IntegerVariable[]{r,o,b,e,r,t}, c);
		// Add equality between name and letters combination
		model.addConstraint(Choco.eq(donaldLetters, donald));
		model.addConstraint(Choco.eq(geraldLetters, gerald));
		model.addConstraint(Choco.eq(robertLetters, robert));
		// Add constraint name sum
		model.addConstraint(Choco.eq(Choco.plus(donald, gerald), robert));
		// Add constraint of all different letters.
		model.addConstraint(Choco.allDifferent(new IntegerVariable[]{d,o,n,a,l,g,e,r,b,t}));
		// Build a solver, read the model and solve it
		Solver solver = new CPSolver();
		solver.read(model);
		solver.solve();
		// Print name value
		System.out.println("donald = " + solver.getVar(donald).getVal());
		System.out.println("gerald = " + solver.getVar(gerald).getVal());
		System.out.println("robert = " + solver.getVar(robert).getVal());
	}
}
