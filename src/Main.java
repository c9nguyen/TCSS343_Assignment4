import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

//	static int n;
//	static int min = 999999999;
//	static int[][] input;
	static int[] dp;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] input = {{0, 2, 3, 7}, {99, 0, 2, 4}, {99, 99, 0, 2}, {99, 99, 99, 0}};
		dp = new int[input[0].length];
		
		long time1 = System.nanoTime();
		System.out.println(BruteForce(input, 0, 4));
		long time2 = System.nanoTime();
		System.out.println(Dynamic(input, 0, 4));
		long time3 = System.nanoTime();
		System.out.println(time2 - time1);
		System.out.println(time3 - time2);
//		System.out.println(DivideAndConquer(input, 0, 4));
	}
	
	public static int BruteForce(int[][] input, int srs, int des) {
		int min = input[srs][input.length - 1];
		int n = input.length;
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			int b = BruteForce(input, i, input.length);
			min = Math.min(a + b, min);
		}
		
		return min;
	}
	
	public static int Dynamic(int[][] input, int srs, int des) {
		int min = input[srs][input.length - 1];
		int n = input.length;
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			
			if (dp[i] == 0) {
				dp[i] = Dynamic(input, i, input.length);
			}
	
			min = Math.min(a + dp[i], min);
		}
		
		dp[srs] = min;
		
		return min;
	}
	
	public static BestPath[] DivideAndConquer(int[][] input, int srs, int des) {
		BestPath[] pathsCost = new BestPath[des - srs];
		
		if (des - srs > 1 ) {
			BestPath[] half1 = DivideAndConquer(input, srs, des/2);
			BestPath[] half2 = DivideAndConquer(input, des/2, des);
	
			for (int i = 0; i < half1.length; i++) {
				pathsCost[i] = half1[i];
			}
			
			for (int i =  0; i < half2.length; i++) {
//				BestPath bestOne = new BestPath();
				pathsCost[des/2 + i] = half2[i];
				if (pathsCost[des/2 + i].cost == 0) {
					pathsCost[des/2 + i].cost = (int) Double.POSITIVE_INFINITY;
				}
				
				for (int j = 0; j < half1.length; j++) {
					BestPath currentPath = pathsCost[des/2 + i];
					
					currentPath.paths.addAll(half1[i].paths);
					
					int newCost = input[srs + j][des/2 + i];
					
					currentPath.cost = newCost + half1[i].cost + half2[j].cost;
					
					if (currentPath.cost < bestOne.cost) {
						bestOne = currentPath;
					}
				}
			}
			
			
		} else {
			Set<Integer> oneSet = new HashSet<Integer>();
			oneSet.add(srs);
			pathsCost[0].paths = oneSet;
			pathsCost[0].cost = input[srs][des-1];
		}
		
		return pathsCost;
	}
	public static class BestPath {
		Set<Integer> paths;
		int cost;
		
		public BestPath() {
			paths = new HashSet<Integer>();
			cost = (int) Double.POSITIVE_INFINITY;
		}
	}
	

}
