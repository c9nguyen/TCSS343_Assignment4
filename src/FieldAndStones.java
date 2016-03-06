
public class FieldAndStones {
	static int counter = 0;
	static boolean[][] input;
	
	public static void main(String[] args) {
		int n, m;
		int[] result;
		int x, y;
		
		
		/* Reading input from args*/
		n = Integer.parseInt(args[0]);
		input = new boolean[n][n];
		m = Integer.parseInt(args[1]);
		
		for (int i = 0; i < m * 2 - 1; i+= 2) {

			x = Integer.parseInt(args[i + 2]);
			y = Integer.parseInt(args[i + 3]);
			System.out.println(x + " " + y);

			input[x][y] = true;
		}
		
		/* Hardcode */
//		n = 8;
//		
//		input = new boolean[n][n];
//		
//		input[1][2] = true;
//		input[2][6] = true;
//		input[4][5] = true;
//		input[7][3] = true;
		
		
		/*Print out table */
//		for (int i = 0; i < n ; i ++) {
//			for (int j = 0; j < n ; j++) {
//				if (!input[i][j]) System.out.print("O" + "\t");
//				else System.out.print("x" + "\t");
//			}
//			
//			System.out.println();
//		}
		
		result = BruteForce();
		System.out.println("Brute-Force:\t x:" + result[0] + "  y:" + result[1] + "  Size:" + result[2]);
		
		result = Dynamic();
		System.out.println("Dynamic:\t x:" + result[0] + "  y:" + result[1] + "  Size:" + result[2]);
		
	}
	
	public static int[] BruteForce() {
		int[] biggest = {-1, -1, 1};
		
		for (int i = 0; i < input.length - biggest[2] ; i++) {
			for (int j = 0; j < input.length - biggest[2] ; j++) {
				for (int z = biggest[2] + 1; z < input.length - j; z++) {
					
					if (squareHere(i, j, z)) {
						biggest[0] = i;
						biggest[1] = j;
						biggest[2] = z;
					}
				}
			}
		}
		
		
		return biggest;
	}
	
	public static boolean squareHere(int x, int y, int s) {
		boolean square = true;
		
		for (int i = 0; i < s && square ; i++) {
			for (int j = 0; j < s && square; j++) {
				counter++;
				if (input[i + x][j + y]) {
					square = false;
				}
			}
		}
		
		return square;
	}
	
	public static int[] Dynamic() {
		int[] biggest = {-1, -1, 1};
		int[][] dp = new int[input.length][input.length];
		
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input.length; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 1;
				} else if (input[i][j]){
					dp[i][j] = 0;
				} else {
					int top = dp[i - 1][j];
					int left = dp[i][j - 1];
					int topleft = dp[i - 1][j - 1];
					
					dp[i][j] = Math.min(Math.min(top, left), topleft) + 1;

					if (dp[i][j] > biggest[2]) {
						biggest[2] = dp[i][j];
						biggest[0] = i - biggest[2] + 1;
						biggest[1] = j - biggest[2] + 1;
					}
				}		
			}
		}
		
		
		return biggest;
	}
	

}
