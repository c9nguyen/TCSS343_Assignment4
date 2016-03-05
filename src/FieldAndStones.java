
public class FieldAndStones {
	static int counter = 0;
	static boolean[][] input;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 8;
		
		input = new boolean[n][n];
		
		input[1][2] = true;
		input[2][6] = true;
		input[4][5] = true;
		input[7][3] = true;
		
		for (int i = 0; i < n ; i ++) {
			for (int j = 0; j < n ; j++) {
				if (!input[i][j]) System.out.print("O" + "\t");
				else System.out.print("x" + "\t");
			}
			
			System.out.println();
		}
		
//		System.out.println(squareHere(2, 0, 1));
		
		int[] result = BruteForce();
		
		for (int i : result) {
			System.out.println(i);
		}
		
		System.out.println(counter);
		
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
		
		
		
		return biggest;
	}
	

}
