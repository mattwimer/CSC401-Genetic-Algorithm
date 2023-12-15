public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    // Methods for initialization, selection, crossover, mutation, and creating new generation
    // These are more complex and depend on your chosen approach for each step

    public Population evolvePopulation(Population population){
        // get n = elitismCount of the fittest from population
        Individual[] fittestFromLastGen = population.getNFittest(elitismCount);
        
        
        Individual[] newGeneration = new Individual[populationSize - elitismCount];
        // SELECTION
        for(int i = elitismCount; i < populationSize ; i++){
            // CROSSOVER
            Individual child;
            if(Math.random() <= crossoverRate)
            // i wonder if its critical that the parents are not the same...
            child = crossover(population.tournament(), population.tournament());
            else
            child = population.getIndividuals()[i]; // if didn't roll chance to crossover, use old generation member
            
            newGeneration[i] = child;
            // MUTATION
            for(int j = 0 ; j < child.getGenes().length ; j++)
            if(Math.random() <= mutationRate)
            child.setGene(j, child.generateGene());
        }
        
        
        // REPLACEMENT 
        // Once children have been bred, replace the previous population (minus the fittest) with newGeneration
        // for(int i = 0 ; i < elitismCount ; i++)
        //     population.getIndividuals()[i] = fittestFromLastGen[i];
        for(int i = 0 ; i < populationSize ; i++)
            population.getIndividuals()[i] = i < elitismCount ? fittestFromLastGen[i] : newGeneration[i];
        
        
        
        
        
        
        
        
        return population;
    }

    // uniform crossover
    public static Individual crossover(Individual p1, Individual p2){
        Individual offspring = new Individual(p1.getGenes().length);
        for(int i = 0 ; i < p1.getGenes().length ; i++)
            offspring.setGene(i, (int)(Math.random() * 2) == 0 ? p1.getGenes()[i] : p2.getGenes()[i]);
        return offspring;
    }
}