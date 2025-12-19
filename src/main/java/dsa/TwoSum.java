package dsa;

import java.util.*;

public class TwoSum {
    public static List<List<Integer>> twoSum(int[] arr, int target) {
        Set<List<Integer>> uniquePairs = new HashSet<>();
        Set<Integer> seen = new HashSet<>();

        for (int num : arr) {
            int diff = target - num;

            if (seen.contains(diff)) {
                List<Integer> pair = Arrays.asList(Math.min(num, diff), Math.max(num, diff));
                uniquePairs.add(pair);
            }
            seen.add(num);
        }
        return new ArrayList<>(uniquePairs);
    }

    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15, 3, 6};
        int target = 9;
        System.out.println(twoSum(arr, target));

        int[] arr2 = {1, 5, 3, 7, 2, 8};
        int target2 = 10;
        System.out.println(twoSum(arr2, target));

        int[] arr3 = {1, 1, 1, 1};
        int target3 = 2;
        System.out.println(twoSum(arr3, target));


        int[] arr4 = {5,5,5,5};
        int target4 = 10;
        System.out.println(twoSum(arr4, target));
    }
}