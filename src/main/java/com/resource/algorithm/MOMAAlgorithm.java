package com.resource.algorithm;

import com.resource.entity.Microservice;
import com.resource.entity.Resource;
import com.resource.utility.KubernetesYamlGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MOMAAlgorithm {
    private List<Resource> resources;
    private List<Microservice> microservices;
    private int populationSize = 2000;
    private int generations = 10000;

    public MOMAAlgorithm(List<Resource> resources, List<Microservice> microservices) {
        this.resources = resources;
        this.microservices = microservices;
    }

    public String optimizeResourceAllocation() {
        // Step 1: Initialize Population
        List<int[]> population = initializePopulation();

        for (int i = 0; i < generations; i++) {
            // Step 2: Evaluate Fitness
            population.sort((a, b) -> Double.compare(calculateFitness(a), calculateFitness(b)));

            // Step 3: Apply Selection
            List<int[]> selected = selection(population);

            // Step 4: Apply Crossover & Mutation
            List<int[]> offspring = crossoverAndMutate(selected);

            // Step 5: Apply Local Search (Memetic Algorithm)
            applyLocalSearch(offspring);

            // Step 6: Update Population
            population = offspring;
        }

        // Return Best Solution Instead of Applying It
        return generateDeploymentPlan(population.get(0));
    }

    private List<int[]> initializePopulation() {
        List<int[]> population = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < populationSize; i++) {
            int[] allocation = new int[microservices.size()];
            for (int j = 0; j < microservices.size(); j++) {
                allocation[j] = rand.nextInt(resources.size()); // Assign each microservice to a random resource
            }
            population.add(allocation);
        }

        return population;
    }

    private double calculateFitness(int[] allocation) {
        double cpuUtilization = 0;
        double memoryUtilization = 0;
        double energyConsumption = 0;

        for (int i = 0; i < allocation.length; i++) {
            Resource rs = resources.get(allocation[i]);
            Microservice ms = microservices.get(i);

            cpuUtilization += (double) ms.getCpuRequirement() / rs.getAvailableCpu();
            memoryUtilization += (double) ms.getMemoryRequirement() / rs.getAvailableMemory();
            energyConsumption += 1.2 * rs.getAvailableCpu();  // Approximation
        }

        return cpuUtilization + memoryUtilization + energyConsumption;  // Minimize this value
    }

    private List<int[]> selection(List<int[]> population) {
        return population.subList(0, populationSize / 2); // Select top 50% best solutions
    }

    private List<int[]> crossoverAndMutate(List<int[]> selected) {
        List<int[]> offspring = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < selected.size(); i += 2) {
            int[] parent1 = selected.get(i);
            int[] parent2 = selected.get((i + 1) % selected.size());

            int crossoverPoint = rand.nextInt(microservices.size());
            int[] child1 = new int[microservices.size()];
            int[] child2 = new int[microservices.size()];

            for (int j = 0; j < crossoverPoint; j++) {
                child1[j] = parent1[j];
                child2[j] = parent2[j];
            }
            for (int j = crossoverPoint; j < microservices.size(); j++) {
                child1[j] = parent2[j];
                child2[j] = parent1[j];
            }

            offspring.add(mutate(child1));
            offspring.add(mutate(child2));
        }

        return offspring;
    }

    private int[] mutate(int[] chromosome) {
        Random rand = new Random();
        int mutationIndex = rand.nextInt(microservices.size());
        chromosome[mutationIndex] = rand.nextInt(resources.size());
        return chromosome;
    }

    private void applyLocalSearch(List<int[]> population) {
        for (int[] solution : population) {
            for (int i = 0; i < solution.length; i++) {
                Resource rs = resources.get(solution[i]);
                Microservice ms = microservices.get(i);

                if ((double) ms.getCpuRequirement() / rs.getAvailableCpu() > 0.9) {
                    solution[i] = (solution[i] + 1) % resources.size(); // Move to next available resource
                }
            }
        }
    }


    private String generateDeploymentPlan(int[] bestSolution) {
        StringBuilder deploymentPlan = new StringBuilder(" Optimal Deployment Plan:\n");

        for (int i = 0; i < bestSolution.length; i++) {
            deploymentPlan.append(" Deploy Microservice '")
                    .append(microservices.get(i).getName())
                    .append("' to Resource '")
                    .append(resources.get(bestSolution[i]).getNodeName())
                    .append("'\n");
        }

        return deploymentPlan.toString();
    }
    private void deployToMinikube(String microserviceName) {
        try {
            String command = "kubectl apply -f " + microserviceName + "-deployment.yaml";
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println(" " + microserviceName + " deployed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void applyBestSolution(int[] bestSolution) {
        for (int i = 0; i < bestSolution.length; i++) {
            String microserviceName = microservices.get(i).getName();
            String nodeName = resources.get(bestSolution[i]).getNodeName();


            KubernetesYamlGenerator.generateDeploymentYaml(microserviceName, nodeName);


            deployToMinikube(microserviceName);
        }
    }

}
