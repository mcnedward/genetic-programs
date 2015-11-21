package com.gp.app.jgap;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.GPGenotype;

import com.gp.app.jgap.problem.AlbrechtProblem;
import com.gp.app.jgap.problem.BaseProblem;

public class Main {

	public static void main(String[] args) throws Exception {
		BaseProblem albrechtProblem = new AlbrechtProblem();
		testGP(albrechtProblem);
		BaseProblem.reset();

		// BaseProblem kemererProblem = new KemererProblem();
		// testGP(kemererProblem);
		// BaseProblem.reset();
		
		// BaseProblem chinaProblem = new ChinaProblem();
		// testGP(chinaProblem);
	}

	private static void testGP(GPProblem problem) throws InvalidConfigurationException {
		GPGenotype gp = problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(50);

		System.out.println("********************");

		gp.outputSolution(gp.getAllTimeBest());
	}

}
