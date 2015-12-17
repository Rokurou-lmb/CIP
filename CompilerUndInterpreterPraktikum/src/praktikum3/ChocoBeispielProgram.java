package praktikum3;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

public class ChocoBeispielProgram {
	public static void main(String[] args) {
		int powerOfMagicSquare = 3;
		int constantSumOfRowsColumnsAndDiagonals = powerOfMagicSquare*(powerOfMagicSquare*powerOfMagicSquare+1)/2;
	
		Model model = new CPModel();
		
		IntegerVariable[][] cells = new IntegerVariable[powerOfMagicSquare][powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++) {
			for(int j = 0; j < powerOfMagicSquare; j++) {
				cells[i][j] = Choco.makeIntVar("cell"+j, 1, powerOfMagicSquare*powerOfMagicSquare);
				model.addVariable(cells[i][j]);
			}
		}
		
		Constraint[] rows = new Constraint[powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++) {
			rows[i] = Choco.eq(Choco.sum(cells[i]), constantSumOfRowsColumnsAndDiagonals);
		}
		
		model.addConstraints(rows);
		
		IntegerVariable[][] cellsDual = new IntegerVariable[powerOfMagicSquare][powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++) {
			for(int j = 0; j < powerOfMagicSquare; j++) {
				cellsDual[i][j] = cells[j][i];
			}
		}
		
		Constraint[] columns = new Constraint[powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++) {
			columns[i] = Choco.eq(Choco.sum(cellsDual[i]), constantSumOfRowsColumnsAndDiagonals);
		}
		
		model.addConstraints(columns);
		
		IntegerVariable[][] diags = new IntegerVariable[2][powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++){
			diags[0][i] = cells[i][i];
			diags[1][i] = cells[i][(powerOfMagicSquare-1)-i];
		}
		
		model.addConstraint(Choco.eq(Choco.sum(diags[0]), constantSumOfRowsColumnsAndDiagonals));
		model.addConstraint(Choco.eq(Choco.sum(diags[1]), constantSumOfRowsColumnsAndDiagonals));
		
		IntegerVariable[] allVars = new IntegerVariable[powerOfMagicSquare*powerOfMagicSquare];
		for(int i = 0; i < powerOfMagicSquare; i++){
			for(int j = 0; j < powerOfMagicSquare; j++){
				allVars[i*powerOfMagicSquare+j] = cells[i][j];
			}
		}
		model.addConstraint(Choco.allDifferent(allVars));
		
		Solver solver = new CPSolver();
		
		solver.read(model);
		
		solver.solve();
		
		for(int i = 0; i < powerOfMagicSquare; i++){
			for(int j = 0; j < powerOfMagicSquare; j++){
				System.out.print(solver.getVar(cells[i][j]).getVal()+" ");
			}
			System.out.println();
		}
	}
}
