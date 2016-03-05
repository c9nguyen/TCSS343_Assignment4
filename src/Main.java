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
		
//		long time1 = System.nanoTime();
//		System.out.println(BruteForce(input, 0, 4));
//		long time2 = System.nanoTime();
//		System.out.println(Dynamic(input, 0, 4));
//		long time3 = System.nanoTime();
//		System.out.println(time2 - time1);
//		System.out.println(time3 - time2);
//		System.out.println(DivideAndConquer(input, 0, 4));
		
		BestPath[] a = DivideAndConquer(input, 0, 4);
		
		for (BestPath b : a) {
			System.out.println(b.cost);
		}
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
		
//		System.out.println(srs + " " + des);
//		System.out.println(des/2);
		
		if (des - srs > 1 ) {
//			System.out.println(srs + " " + des/2);
//			System.out.println(des/2 + " " + des);
			
			BestPath[] half1 = DivideAndConquer(input, srs, (des + srs) / 2);
			BestPath[] half2 = DivideAndConquer(input, (des + srs) / 2, des);
	
			for (int i = 0; i < half1.length; i++) {
				pathsCost[i] = half1[i];
			}
			
			for (int i =  0; i < half2.length; i++) {
//				BestPath bestOne = new BestPath();
				
				int d = half2[i].port;
				half2[i].previous = null;
				
				for (int j = 0; j < half1.length + i ; j++) {
					int s = pathsCost[j].port;
					int cost =  pathsCost[j].cost + input[s][d];
					
					if (d == 3 && s == 0) {
						System.out.println("Here");
					}
					
					if (half2[i].cost > cost || half2[i].previous == null) {
						half2[i].previous = pathsCost[j];
						half2[i].cost = cost;
					}
				}
				
				pathsCost[i + half1.length] = half2[i];
			}
			
			
		} else {
			pathsCost[0] = new BestPath();
			pathsCost[0].port = srs;
		}
		
		return pathsCost;
	}
	public static class BestPath {
		BestPath previous;
		int port;
		int cost;
		
		public BestPath() {
			previous = null;
			port = -1;
//			cost = (int) Double.POSITIVE_INFINITY;
			cost = 0;
		}
	}
	

}
