/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.primefactorization;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Saher
 */
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

        PrimeFactorization pf = new PrimeFactorization();

        // Perform prime check
        ArrayList<Boolean> primes = pf.primeChecker(n);
        System.out.println("Prime numbers up to " + n + ":");
        for (int i = 0; i <= n; i++) {
            if (primes.get(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // Perform prime factorization
        ArrayList<Integer> factors = pf.primeFactorizer(n);
        System.out.println("Prime factors of " + n + ": " + factors);
    
    }
    
    public ArrayList<Boolean> primeChecker(int n){
     ArrayList<Boolean> primes = new ArrayList<>(n);
     for (int i = 0; i <= n; i++) {
            primes.add(true);
        }
     primes.set(0, false); 
     primes.set(1, false);  
     for (int i = 2; i <= Math.sqrt(n); i++) {
        if(primes.get(i) == true){ 
            for(int j = 2; i*j <= n; j++){
               primes.set(i*j, false);
           }
        }    
     }
     return primes;
    }
    

    public ArrayList<Integer> primeFactorizer(int n){
    ArrayList<Boolean> primes = new ArrayList<>(n);
    ArrayList<Integer> factors = new ArrayList<>(n);
    for (int i = 0; i <= n; i++) {
            primes.add(true);
        }
    primes = primeChecker(n);
    
    for(int q = n/2; q >= 2; q--){
        if(primes.get(q) == true){
            
            int times = factorizationTimes(n, q);
            
            for(int i=0; i < times; i++){
                factors.add(q);
                n = n/q;
            }
           }
        }
    
    if (n > 1) {
        factors.add(n); 
    }
    return factors;
}
    public static int factorizationTimes(int n, int prime){
        if(n % prime == 0){
        
        return 1 + factorizationTimes(n / prime, prime);
        
        }
        return 0;
    }
    
}


