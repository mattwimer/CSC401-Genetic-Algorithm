import java.util.HashSet;

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

    // large tournament sizes are better I think? so pick largest n such that 2^n < popSize and create brackets
    public Individual tournament(){
        int n = 0;
        while((int)Math.pow(2, n) < individuals.length)
            n++;
        n--;
        Individual[] competitors = new Individual[(int)Math.pow(2,n)];
        HashSet<Integer> competing = new HashSet<Integer>((int)Math.pow(2,n));
        int i = 0;
        while(competing.size() != competitors.length){
            int index = (int)(Math.random() * individuals.length);
            if(competing.add(index))
                competitors[i++] = individuals[index];
        }

        return compete(competitors);
    }
    
    private Individual compete(Individual[] competitors){
        if(competitors.length == 1)
            return competitors[0];
        else if(competitors.length == 2)
            return competitors[0].getFitness() < competitors[1].getFitness() ? competitors[0] : competitors[1];
        else{
            Individual[] first = new Individual[competitors.length/2]; // kinda too lazy to do this in place, but this is 
            Individual[] last = new Individual[competitors.length/2]; // terrible for the space complexity of this function
            for(int i = 0 ; i < competitors.length/2 ; i++){
                first[i] = competitors[i];
                last[i] = competitors[i + competitors.length/2]; 
            }
            Individual[] finalists = {compete(first), compete(last)};
            return compete(finalists);
        }
    }
    
    public Individual[] getIndividuals() {
        return individuals;
    }

    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
    }
}