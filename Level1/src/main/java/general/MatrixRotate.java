package general;

public class MatrixRotate {

	public static void main(String[] args) {
		MatrixRotate mt = new MatrixRotate();
		mt.doTranspose();
	}

	private void doTranspose() {
		int[][] matrix = {{4,5,6}, {7,8,9}, {1,2,3}};
		
		for(int i=0; i<matrix.length; i++) {
			for(int j=matrix.length-1; j>=0; j--) {
				System.out.print(matrix[j][i]);
			}
			System.out.println("");
		}
	}
}
