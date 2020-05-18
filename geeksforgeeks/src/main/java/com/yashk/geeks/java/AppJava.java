package com.yashk.geeks.java;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppJava {
    public static void main(String[] args) {
        System.out.println(new AppJava().missingNumber(5,new Integer[]{1,2 ,3, 5}));
        System.out.println( Arrays.asList(
                new AppJava().subarrayOrderOfNSquare(new Integer[]{15, 2, 4, 8, 9, 5, 10, 23},23)
        ));

    }


    // https://practice.geeksforgeeks.org/problems/missing-number-in-array/0
    // Missing number in array
    // return the diffrence between sum upto N and sum of all elements of array
    // assumes no numbers are repeated in array.
    public int missingNumber(int n,Integer[] input){
        int sum = (n * (n+1)) / 2;
        int arraySum = Arrays.asList(input).stream().collect(Collectors.summingInt(t -> t.intValue()));
        return sum - arraySum;
    }

    // https://practice.geeksforgeeks.org/problems/subarray-with-given-sum/0
    // O(n2) solution
    // returns empty if no such subaary is found
    //
    public Integer[] subarrayOrderOfNSquare(Integer[] input, int k){
        Integer[] result = new Integer[1];
        int currentSum =0;
        for(int i=0;i<input.length;i++){
            currentSum = input[i];
            for(int j=i+1;j<input.length;j++){
                currentSum = currentSum + input[j];
                if(currentSum == k){
                    return new Integer[] {i,j};
                }
            }
        }
        return new Integer[]{};
    }
}
