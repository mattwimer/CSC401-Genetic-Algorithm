public class Population {
    private Individual[] individuals;
    private int geneLength;

    public int getGeneLength() {
        return geneLength;
    }

    public Population(int populationSize, int geneLength) {
        this.geneLength = geneLength;
        individuals = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Individual(geneLength);
        }
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 1; i < individuals.length; i++) {
            if (fittest.getFitness() > individuals[i].getFitness()) {
                fittest = individuals[i];
            }
        }
        return fittest;
    }

    /**
     * @param n number of the best individuals to return
     * @return array of size n containing the n most fit individuals in this class's arr of individuals
     */
    public Individual[] getNFittest(int n){
        if(n >= individuals.length)
            return getIndividuals();
        Individual[] fittest = new Individual[n];
        for(int i = 0 ; i < n ; i++)
            fittest[i] = individuals[i];
        for(int i = n ; i < individuals.length ; i++)
            for(int j = 0 ; j < n ; j++)
                if(fittest[j].getFitness() < individuals[i].getFitness()){
                    Individual temp = fittest[j]; // if a previously fittest individual is swapped out, it is then compared to the rest of the fittest to vie for a spot
                    fittest[j] = individuals[i];
                    individuals[i] = temp;
                }

        return fittest;
    }


    public Individual[] getIndividuals() {
        return individuals;
    }
}