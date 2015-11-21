package com.gp.app.jgap.test.fitness;

import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

import com.gp.app.jgap.data.DataSet;

public class AlbrechtFitnessFunction extends GPFitnessFunction {
	private static final long serialVersionUID = 1L;

    private static Object[] NO_ARGS = new Object[0];
    
	private List<DataSet> dataSet;
	private List<Variable> variables;
	
	public AlbrechtFitnessFunction(List<DataSet> dataSet, List<Variable> variables) {
		this.dataSet = dataSet;
		this.variables = variables;
	}

	@Override
	protected double evaluate(IGPProgram program) {
		double result = 0.0f;
		
		long longResult = 0;
		for (DataSet dataSet : dataSet) {
			// Set the variables
			Object[] args = new Object[variables.size()];
			for (int x = 0; x < variables.size(); x++) {
				variables.get(x).set(dataSet.getVariableByAttribute(variables.get(x).getName()));
				args[x] = variables.get(x);
			}
			double value = program.execute_double(0, NO_ARGS);
			longResult += Math.abs(value - dataSet.getEffort());
		}
		result = longResult;
		
		return result;
	}

}
