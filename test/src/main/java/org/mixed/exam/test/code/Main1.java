package org.mixed.exam.test.code;

import java.util.Scanner;

public class Main1 {
    private static int dp[];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(climbStairs(n));
    }
    public static int climbStairs(int n) {
        if(n==1)
        {
            return 1;
        }
        dp=new int[n+1];
        dp[1]=1;
        dp[2]=2;
        for(int i=3;i<=n;i++)
        {
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }
}
