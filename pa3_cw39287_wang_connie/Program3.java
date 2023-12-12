/*
 * Name: Connie Wang
 * EID: cw39276
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {
  
/*
      final float max = 10000.00f;
    
    public float opt(float[] houses, int k) {
        int n = houses.length;
        Arrays.sort(houses);
        Float[][] memo = new Float[n][n + 1];
        
        float[][][] cost = new float[n][n][n];
        for (int i = 0; i < n; i ++) {
            for (int j = i; j < n; j ++) {
                for (int m = i; m <= j; m ++) {
                    cost[i][j][m] = Math.abs(houses[(i + j) / 2] - houses[m]);
                }
            }
        }
        return helper(houses, cost, memo, 0, k, n);
    }
   
    private float helper(float[] houses, float[][][] cost, Float[][] memo, int i, int k, int n) {
        if (k == 0 && i == n) return 0;
        float c =0 ;
        for (int j = i; j < n; j ++) {
            for(int m = 0; m<n; m++){
                c = Math.max(c,Math.min( helper(houses, cost, memo, j + 1, k - 1, n) ,cost[i][j][m]));
            }
        }
        return memo[i][k] = c;
    }
   */

    @Override   
    public TownPlan OptimalResponse(TownPlan town) {
        /* TODO implement this function */
        ArrayList <Float> arr = town.getPositionHouses();
        float stations[] = new float [town.getHouseCount()];
        for(int i = 0; i< town.getHouseCount(); i++){
            stations[i] = arr.get(i);
        }
        int K = town.getStationCount();
        //float x = opt(stations, k);
         int N = stations.length;

         /*

        float[][][] cost = new float[N][N][N];
        for (int i = 0; i < N; i ++) {
            for (int j = i; j < N; j ++) {
                for (int m = i; m <= j; m ++) {
                    cost[i][j][m] = Math.abs(stations[(i + j) / 2] - stations[m]);
                }
            }
        }
        for (int i = 0; i < N; i ++) {
            System.out.println();
            for (int j = i; j < N; j ++) {
                System.out.println("_______________________");
                for (int m = i; m <= j; m ++) {
                    System.out.print(cost[i][j][m] +" ");
                }
            }
        }
        Float[][] memo = new Float[N][N + 1];
        float i = helper(stations, cost, memo, 0, K, N);
        System.out.println(i);
        

         */
        float[] deltas = new float[N-1];
        for (int i = 0; i < N-1; ++i){
            deltas[i] = stations[i+1] - stations[i];         
        }
        for(int j = 0; j<K-1; j++){
             int ind = 999;
            float m = -1;
            for (int i = 0; i < N-1; ++i){
              if(deltas[i]>m){
                  m = deltas[i]; 
                  ind = i;
              }
            }
            deltas[ind] = -1;
         }
        float[][] dp = new float[N-1][K+1];
        for (int i = 0; i < N-1; ++i){
            for (int j = 0; j < K; ++j){
                dp[i][j] = 0;
            }
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < K; ++i){
            for (int j = 0; j < N-1; ++j){
                if(j>=end){
                if(deltas[j] == -1 || j == N-2){
                    if(j == N-2){
                        dp[0][i] = (stations[j+1]-stations[start])/2;
                       break;
                    }
                    else{
                    dp[0][i] = (stations[j]-stations[start])/2;
                    start = j+1; 
                    end = start;
                    break;
                    }          
                }
                }
            }
        }  
        for (int p = 0; p < N-2; ++p){
            for (int k = 0; k <= K-1; ++k) {
                float bns = -1;
                    bns = Math.max(dp[p][k+1], dp[p][k]);
                    dp[p+1][k] = bns;
            }
        }
            town.setResponse( dp[N-2][0]);
            
    return town;
    }
    /**
     * Determines the solution of the set of food station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "position_food_stations" field set to the optimal food station positions
     */
    @Override
    public TownPlan OptimalPosFoodStations(TownPlan town) {
        ArrayList <Float> arr = town.getPositionHouses();
        float stations[] = new float [town.getHouseCount()];
        for(int i = 0; i< town.getHouseCount(); i++){
            stations[i] = arr.get(i);
        }
        int K = town.getStationCount();
         int N = stations.length;
        float[] deltas = new float[N-1];
        for (int i = 0; i < N-1; ++i){
            deltas[i] = stations[i+1] - stations[i];         
        }
        for(int j = 0; j<K-1; j++){
             int ind = 999;
            float m = -1;
            for (int i = 0; i < N-1; ++i){
              if(deltas[i]>m){
                  m = deltas[i]; 
                  ind = i;
              }
            }
            deltas[ind] = -1;
         }
        float[][] dp = new float[N-1][K+1];
        for (int i = 0; i < N-1; ++i){
            for (int j = 0; j < K; ++j){
                dp[i][j] = 0;
            }
        }
        int start = 0;
        int end = 0;
        ArrayList<Float> ret = new ArrayList<Float>();
        for (int i = 0; i < K; ++i){
            for (int j = 0; j < N-1; ++j){
                if(j>=end){
                if(deltas[j] == -1 || j == N-2){
                    if(j == N-2){
                        ret.add((stations[j+1]+stations[start])/2);
                       break;
                    }
                    else{
                    ret.add((stations[j]+stations[start])/2);
                    start = j+1; 
                    end = start;
                    break;
                    }          
                }
                }
            }
        }  
        town.setPositionFoodStations(ret);
        return town; /* TODO remove this line */
    }
}
