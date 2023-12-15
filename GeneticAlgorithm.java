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
        for(int i = 0 ; i < elitismCount ; i++)
            population.getIndividuals()[i] = fittestFromLastGen[i];
        
        
        Individual[] newGeneration = new Individual[populationSize - elitismCount];
        // SELECTION
        for(int i = elitismCount; i < populationSize ; i++){
            // CROSSOVER
            // i wonder if its critical that the parents are not the same...
            Individual child = crossover(population.tournament(), population.tournament());
            newGeneration[i] = child;
            // MUTATION
            for(int j = 0 ; j < child.getGenes().length ; j++)
                if(Math.random() <= mutationRate)
                    child.setGene(j, child.generateGene());
        }
        
        
        // REPLACEMENT 
        // Once children have been bred, replace the previous population (minus the fittest) with newGeneration
        
        
        


        
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