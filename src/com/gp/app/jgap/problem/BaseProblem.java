package com.gp.app.jgap.problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import com.gp.app.jgap.data.DataSet;
import com.gp.app.jgap.data.DataVariable;
import com.gp.app.jgap.test.fitness.AlbrechtFitnessFunction;

public abstract class BaseProblem extends GPProblem {

	protected List<double[]> targets = new ArrayList<>();
	protected List<String> annotations = new ArrayList<>();
	protected List<Variable> variables = new ArrayList<>();

	private String fileName;

	public BaseProblem(String fileName) throws InvalidConfigurationException {
		super(new GPConfiguration());
		this.fileName = fileName;
		variables = new ArrayList<>();

		GPConfiguration config = getGPConfiguration();
		filterAttributes();
		for (int x = 0; x < annotations.size(); x++) {
			variables.add(Variable.create(config, annotations.get(x), CommandGene.IntegerClass));
		}

		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(10);
		config.setPopulationSize(1000);
		config.setMaxCrossoverDepth(10);
		config.setFitnessFunction(new AlbrechtFitnessFunction(getDataset(), variables));
		config.setStrictProgramCreation(true);
	}
	
	public static void reset() {
		Configuration.reset();
	}

	protected abstract String[] getAnnotationFilters();

	@SuppressWarnings("rawtypes")
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration config = getGPConfiguration();

		Class[] types = { CommandGene.IntegerClass };
		Class[][] argTypes = { {} };

		CommandGene[] terminalSet = new CommandGene[variables.size()];
		for (int x = 0; x < variables.size(); x++) {
			terminalSet[x] = variables.get(x);
		}

		CommandGene[] functionSet = { new Add(config, CommandGene.IntegerClass),
				new Subtract(config, CommandGene.IntegerClass), new Multiply(config, CommandGene.IntegerClass),
				new Divide(config, CommandGene.IntegerClass), new Terminal(config, CommandGene.IntegerClass) };
		CommandGene[] set = new CommandGene[terminalSet.length + functionSet.length];
		for (int x = 0; x < terminalSet.length; x++)
			set[x] = terminalSet[x];
		int index = terminalSet.length;
		for (int x = 0; x < functionSet.length; x++)
			set[index++] = functionSet[x];

		CommandGene[][] nodeSets = { set };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);
		return result;
	}

	protected List<DataSet> getDataset() {
		List<DataSet> dataSet = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = "";
			boolean readingData = false;

			while ((line = in.readLine()) != null) {
				if (readingData) {
					String[] params = line.split(",");
					List<DataVariable> variables = new ArrayList<>();
					double effort = 0;
					for (int x = 0; x < params.length; x++) {
						if (x == params.length - 1)
							effort = Double.parseDouble(params[x]);
						else
							variables.add(new DataVariable(annotations.get(x), Double.parseDouble(params[x])));
					}
					dataSet.add(new DataSet(variables, effort));
				} else {
					String[] annotationDef = getAnnotationDefinition(line);
					if (annotationDef != null && annotationDef[0].equals("@attribute")) {
						annotations.add(annotationDef[1]);
					} else if (annotationDef != null && annotationDef[0].equals("@data"))
						readingData = true;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file at: " + fileName + "...");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSet;
	}

	protected void filterAttributes() {
		List<String> filteredAnnotations = new ArrayList<>();
		for (String original : annotations) {
			for (String annotation : getAnnotationFilters()) {
				if (original.equals(annotation))
					filteredAnnotations.add(annotation);
			}
		}
		annotations.clear();
		annotations.addAll(filteredAnnotations);
	}

	private static String[] getAnnotationDefinition(String line) {
		String[] annotation = null;
		if (!line.equals("")) {
			String[] words = line.split(" ");
			if (words[0].charAt(0) == '@')
				annotation = words;
		}
		return annotation;
	}
}
