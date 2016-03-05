import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Canoe {

//	static int n;
//	static int min = 999999999;
//	static int[][] input;
	static int[] dp;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] input = {{0, 2, 3, 7}, {99, 0, 2, 4}, {99, 99, 0, 2}, {99, 99, 99, 99, 0}};
		int size = input.length;
//		int[][] input = randomTestCase(size);
		
		for (int i = 0; i < size; i++) {
			
			for (int j = 0; j < size; j++) {
				if (j < i) {
					System.out.print("inf" + "\t");
				} else {
					System.out.print(input[i][j] + "\t");
				}
			}
			
			System.out.println();
		}
		
		dp = new int[input[0].length];
		
		
		System.out.println("BruteForce2:\t" + BruteForce2(input, 0).cost);
		
		System.out.println("Dynamic2:\t" + Dynamic2(input, 0));
		
//		long time1 = System.nanoTime();
		System.out.println("BruteForce:\t" + BruteForce(input, 0));
//		long time2 = System.nanoTime();
		System.out.println("Dynamic:\t" + Dynamic(input, 0));
//		long time3 = System.nanoTime();
//		System.out.println(time2 - time1);
//		System.out.println(time3 - time2);
		System.out.println("Divide:\t\t" + DivideAndConquer(input, 0, size)[size-1].cost);

	}
	
	public static int BruteForce(int[][] input, int srs) {
		int min = input[srs][input.length - 1];
		int n = input.length;
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			int b = BruteForce(input, i);
			min = Math.min(a + b, min);
		}
		
		return min;
	}
	
	public static BestPath BruteForce2(int[][] input, int srs) {
		BestPath bestOne = new BestPath();
		
//		int min = input[srs][input.length - 1];
		bestOne.port = srs;
		bestOne.cost = input[srs][input.length - 1];
		
		int n = input.length;
		
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			BestPath current = BruteForce2(input, i);
			int b = current.cost;
//			min = Math.min(a + b, min);
			if (a + b < bestOne.cost) {
				bestOne.cost = a + b;
				current.previous = bestOne;
				bestOne.next = current;
			}
			
		}
		
		return bestOne;
	}
	
	public static int Dynamic(int[][] input, int srs) {
		int min = input[srs][input.length - 1];
		int n = input.length;
		for (int i = srs + 1; i < n; i++) {
			int a = input[srs][i];
			
			if (dp[i] == 0) {
				dp[i] = Dynamic(input, i);
			}
	
			min = Math.min(a + dp[i], min);
		}
		
		dp[srs] = min;
		
		return min;
	}
	
	public static int Dynamic2(int[][] input, int srs) {
		int[] dp2 = new int[input.length];
		int[] from = new int[input.length];
	
		for (int i = 1; i < dp2.length; i++) {
			dp2[i] = (int) Double.POSITIVE_INFINITY;
		}
		
		int n = input.length;
		
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < i; j++) {
				int cost = input[j][i] + dp2[j];

				if (dp2[i] > cost) {
					dp2[i] = cost;
					from[i] = j + 1;
				}
			}
		}
		
		for (int i : from) {
			System.out.print(i + "\t");
		}
		System.out.println();
//		dp2[srs] = min;
		
		return dp2[dp2.length - 1];
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
	
	public static int[][] randomTestCase(int sizeN) {
		int[][] testCase = new int[sizeN][sizeN];
		Random rd = new Random();
		for (int i = 0; i < sizeN; i++) {
			int random = 1;
			
			for (int j = 0; j < sizeN; j++) {
				if (j == i) {
					testCase[i][j] = 0;
				} else if (j < i) {
					testCase[i][j] = (int) Double.POSITIVE_INFINITY;
				} else {
					int num = rd.nextInt(1000) + random;
					random = num;
					testCase[i][j] = num;
				}
			}
		}
		
		return testCase;
	}
	
	public static class BestPath {
		BestPath previous;
		BestPath next;
		int port;
		int cost;
		
		public BestPath() {
			previous = null;
			next = null;
			port = -1;
//			cost = (int) Double.POSITIVE_INFINITY;
			cost = 0;
		}
	}
	

}
