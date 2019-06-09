package cc.wxyun.datastructuer.sparematrix;

public class TestMatrix {

	public static void main(String[] args) {
		/**
		 * 创建个原始矩阵
		 */
		Matrix matrix = new Matrix(10, 10);
		matrix.put(0, 0, 1);
		matrix.put(1, 1, 2);
		matrix.put(2, 2, 1);
		System.out.println("原始矩阵为:");
		matrix.print();

		/**
		 * 由原始矩阵转换为稀疏矩阵
		 */
		SpareMatrix spareMatrix = MatrixUtil.matrix2SpareMatrix(matrix);
		System.out.println("转换之后的稀疏矩阵为:");
		spareMatrix.print();
		
		/**
		 * 稀疏矩阵再转换为原始矩阵
		 */
		Matrix matrix2 = MatrixUtil.spareMatrix2Matrix(spareMatrix);
		System.out.println("由稀疏矩阵转换回来的原始矩阵为:");
		matrix2.print();
	}
}
