/**
 * A program that takes input of boolean 2D array A[n][n].
 * such as A[i][j] = true if and only if there is a stone at (i, j)
 * Program must find the biggest square can be made in the table.
 * The square should contain no stones.
 * @author Chinh Nguyen, Vinh Vien, Bun Mam
 *	
 */
public class challenge {
	static int counter = 0;
	
	/* input, the boolean map. input[i][j] = true  
	 * if and only if there is a stone at location i and j */
	static boolean[][] input;	
	
	public static void main(String[] args) {
		int n, m;			
		int[] result;		// an array of location x, y and size of biggest square
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
	
	/**
	 * Using brute force to find biggest square
	 * Iterating through every location and check what is biggest square there
	 * Using squareHere(i, j, s) method to check if there is square size s
	 * at location i, j.
	 * @return array contains location i, j and size of biggest square
	 */
	public static int[] BruteForce() {
		int[] biggest = {-1, -1, 1};	// Initialize largest square, size 1.
		
		// Iterating through every locations
		for (int i = 0; i < input.length - biggest[2] ; i++) {
			for (int j = 0; j < input.length - biggest[2] ; j++) {
				
				//Check at location i, j if a square bigger than biggest square
				// can be at location i, j
				for (int z = biggest[2] + 1; z <= input.length; z++) {
					System.out.println(z);
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
	
	/**
	 * Check if a square size s exists at location x, y
	 * Iterating through every spots within the location x, y 
	 * If there is no stone in the range, size s square in x, y is true
	 * @param x location
	 * @param y location
	 * @param s size 
	 * @return true if square size s exists at x, y
	 */
	public static boolean squareHere(int x, int y, int s) {
		boolean square = true;
		
		// Checking for stones
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
	
	/**Using a 2-D to calculate the biggest size of a square
	 * The array store the size of the square at the location:
	 * x = i - z, y = j - z
	 * @return
	 */
	public static int[] Dynamic() {
		int[] biggest = {-1, -1, 1};
		int[][] dp = new int[input.length][input.length];
		
		//Iterating through every locations on the input map
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input.length; j++) {
				

				// If there is a stone, no square
				if (input[i][j]){
					dp[i][j] = 0;
					
				// If the location is at first row and first column 
				// Size is can only be 1.
				} else if (i == 0 || j == 0) {
						dp[i][j] = 1;
						
				// If there is square at locations
				// (i - 1, j), (i, j - 1), (i - 1, j - 1)
				// The location (i, j) should be able to make a square size of
				// the minimum square of one of 3 squares above
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
