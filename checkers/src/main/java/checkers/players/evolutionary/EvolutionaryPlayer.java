package checkers.players.evolutionary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import checkers.board.Action;
import checkers.board.Board;
import checkers.board.Piece;
import checkers.checkersGame.*;
import checkers.checkersGame.utils.Pair;

public class EvolutionaryPlayer implements GamePlayer {

	private Individual choosenPlayer;
	private int bestFitness;
	private static final int initialPopNumber = 10;
	private static final int numberOfEligiblePops = 5;
	
	@Override
	public Pair<Action, Piece> play(Board board, Player id) {
		if(choosenPlayer != null)
		{
			return choosenPlayer.getAction(board, id);
		}
		return null;
	}

	@Override
	public String getPlayerName() {
		return "Evolutionary Player";
	}
	
	public EvolutionaryPlayer(int iterations){
		super();
		computeBestPlayer(iterations);
	}
	/**
	 * Runs algorithm to find the best player
	 * @param iterations maximum number of iterations of the algorithm
	 */
	private void computeBestPlayer(int iterations){
		List<Individual> population = initializePop(); // initializes pop
		
		// computes value of pop
		List<Pair<Individual, Integer>> populationWFitness = new ArrayList<>();
		for(Individual ind : population){
			populationWFitness.add(new Pair<>(ind, ind.computeIndividualFitness()));
		}
		List<Individual> nextGen;
		for(int x=0; x<iterations; x++){
			System.out.println("Evolutionary Player: on iteration " + x);
			populationWFitness = computeEligibleToReproduce(populationWFitness); // choose individuals to reproduce
			nextGen = reproduceIndividuals(populationWFitness); // gets next gen individuals
			populationWFitness.addAll(computeFitness(nextGen)); // computes fitness and adds next gen individuals to list
		}
		
		choosenPlayer = getBestIndividual(populationWFitness);
		bestFitness = choosenPlayer.computeIndividualFitness();
	}
	
	/**
	 * @return list of initial random pops
	 */
	private List<Individual> initializePop()
	{
		List<Individual> initialPops = new ArrayList<>();
		for(int x = 0;x<initialPopNumber;x++){
			initialPops.add(new Individual());
		}
		return initialPops;
	}
	
	/**
	 * 
	 * @param population
	 * @return list of eligible individuals to reproduce
	 */
	private List<Pair<Individual, Integer>> computeEligibleToReproduce(List<Pair<Individual, Integer>> population){
		List<Pair<Individual, Integer>> eligible = new ArrayList<>();
		population.sort(new Comparator<Pair<Individual, Integer>>() {

			@Override
			public int compare(Pair<Individual, Integer> o1, Pair<Individual, Integer> o2) {
				return o1.second - o2.second;
			}
		});
		for(int x=0; x<numberOfEligiblePops; x++){
			eligible.add(population.get(x));
		}

		return eligible;
	}
	
	/**
	 * Creates next gen population
	 */
	private List<Individual> reproduceIndividuals(List<Pair<Individual, Integer>> population){
		Iterator<Pair<Individual, Integer>> it = population.iterator();
		List<Individual> newIndividuals = new ArrayList<>();
		while(it.hasNext()){
			newIndividuals.add(it.next().first);
		}
		int max = newIndividuals.size();
		List<Individual> nextGen = new ArrayList<>();
		for(int x=0; x<max;x++){
			for(int y=0; y<max;y++){
				if(x != y){
					nextGen.add(Individual.reproduce(newIndividuals.get(x), newIndividuals.get(y)));
				}
			}
		}
		return nextGen;
		
	}
	
	/**
	 * Computes fitness of a list of individuas
	 * @param populations
	 * @return
	 */
	private List<Pair<Individual, Integer>> computeFitness(List<Individual> populations){
		List<Pair<Individual, Integer>> individuals = new ArrayList<>();
		for(Individual ind : populations){
			individuals.add(new Pair<>(ind, ind.computeIndividualFitness()));
		}
		return individuals;
	}
	
	/**
	 * @param populations
	 * @return most fit individual
	 */
	private Individual getBestIndividual(List<Pair<Individual, Integer>> populations){
		Individual higher = null;
		int maxFitness = -1;
		for(Pair<Individual, Integer> ind : populations){
			if(ind.second > maxFitness){
				higher = ind.first;
				maxFitness = ind.second;
			}
		}
		return higher;
	}
}
