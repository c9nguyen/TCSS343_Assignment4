
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
	
//	public static int DivideAndConquer(int[][] input, int srs, int des) {
//		int min;
//		
//		if (des - srs > 2 ) {
//			int part1 = DivideAndConquer(input, srs, des/2);
//			int part2 = DivideAndConquer(input, des/2, des);
//			
//			
//			
//		} else {
//			min = input[srs][des - 1];
//		}
//		
//		return min;
//	}

}
