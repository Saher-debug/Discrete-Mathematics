package com.mycompany.primefactorization;

import java.util.*;

public class PrimeFactorization {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter a positive integer (n): ");
        int n = scanner.nextInt();

        if (n < 2) {
            System.out.println("Please enter an integer greater than or equal to 2.");
            return;
        }

        // Find prime numbers up to n
        ArrayList<Boolean> primes = primeChecker(n);
        System.out.println("Prime numbers up to " + n + ":");
        for (int i = 0; i <= n; i++) {
            if (primes.get(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // Perform prime factorization
        ArrayList<Integer> factors = primeFactorizer(n);
        System.out.println("Prime factors of " + n + ": " + factors);

        // Test GCD and LCM
        System.out.print("Enter the first positive integer (a): ");
        int a = scanner.nextInt();
        System.out.print("Enter the second positive integer (b): ");
        int b = scanner.nextInt();

        if (a < 1 || b < 1) {
            System.out.println("Please enter integers greater than or equal to 1.");
            return;
        }

        GcdAndLcm gcdAndLcm = new GcdAndLcm();
        int gcd = gcdAndLcm.gcdEuclid(a, b);
        int lcm = gcdAndLcm.lcmUseGcd(a, b, gcd);

        System.out.println("GCD of " + a + " and " + b + " using Euclid: " + gcd);
        System.out.println("LCM of " + a + " and " + b + ": " + lcm);

        // Using prime factorization
        gcdAndLcm.gcdUsingPrimeFactorization(a, b);
    }

    public static ArrayList<Boolean> primeChecker(int n) {
        ArrayList<Boolean> primes = new ArrayList<>(Collections.nCopies(n + 1, true));
        primes.set(0, false);
        primes.set(1, false);
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (primes.get(i)) {
                for (int j = i * i; j <= n; j += i) {
                    primes.set(j, false);
                }
            }
        }
        return primes;
    }

    public static ArrayList<Integer> primeFactorizer(int n) {
        ArrayList<Integer> factors = new ArrayList<>();
        ArrayList<Boolean> primes = primeChecker(n);

        for (int i = 2; i <= n / 2; i++) {
            if (primes.get(i)) {
                while (n % i == 0) {
                    factors.add(i);
                    n /= i;
                }
            }
        }

        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }
}

class GcdAndLcm {

    public void gcdUsingPrimeFactorization(int a, int b) {
        ArrayList<Integer> factorsA = PrimeFactorization.primeFactorizer(a);
        ArrayList<Integer> factorsB = PrimeFactorization.primeFactorizer(b);

        // Analyze factor occurrences
        HashMapPair factorAnalysis = FactorAnalysis.analyzeFactors(factorsA, factorsB);

        // Get max and min occurrences
        HashMapPair maxMinPairs = FactorAnalysis.maxAndMin(factorAnalysis);

        // Calculate GCD and LCM
        FactorAnalysis.calculateGCDAndLCM(maxMinPairs);
    }

    public int gcdEuclid(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int lcmUseGcd(int a, int b, int gcd) {
        return (a / gcd) * b;
    }
}

class HashMapPair {
    public HashMap<Integer, Integer> map1;
    public HashMap<Integer, Integer> map2;

    public HashMapPair(HashMap<Integer, Integer> map1, HashMap<Integer, Integer> map2) {
        this.map1 = map1;
        this.map2 = map2;
    }
}

class FactorAnalysis {
    public static HashMapPair analyzeFactors(List<Integer> factorsA, List<Integer> factorsB) {
        HashMap<Integer, Integer> occurrenceA = new HashMap<>();
        HashMap<Integer, Integer> occurrenceB = new HashMap<>();

        for (int factor : factorsA) {
            occurrenceA.put(factor, occurrenceA.getOrDefault(factor, 0) + 1);
        }
        for (int factor : factorsB) {
            occurrenceB.put(factor, occurrenceB.getOrDefault(factor, 0) + 1);
        }

        return new HashMapPair(occurrenceA, occurrenceB);
    }

    public static HashMapPair maxAndMin(HashMapPair occurrences) {
        HashMap<Integer, Integer> numberMax = new HashMap<>();
        HashMap<Integer, Integer> numberMin = new HashMap<>();

        for (int factor : occurrences.map1.keySet()) {
            if (occurrences.map2.containsKey(factor)) {
                int maxFreq = Math.max(occurrences.map1.get(factor), occurrences.map2.get(factor));
                int minFreq = Math.min(occurrences.map1.get(factor), occurrences.map2.get(factor));
                numberMax.put(factor, maxFreq);
                numberMin.put(factor, minFreq);
            }
        }

        return new HashMapPair(numberMax, numberMin);
    }

    public static void calculateGCDAndLCM(HashMapPair maxMinPairs) {
        long gcd = 1;
        for (Map.Entry<Integer, Integer> entry : maxMinPairs.map2.entrySet()) {
            gcd *= Math.pow(entry.getKey(), entry.getValue());
        }

        long lcm = 1;
        for (Map.Entry<Integer, Integer> entry : maxMinPairs.map1.entrySet()) {
            lcm *= Math.pow(entry.getKey(), entry.getValue());
        }

        System.out.println("GCD using Prime Factorization: " + gcd);
        System.out.println("LCM using Prime Factorization: " + lcm);
    }
}
