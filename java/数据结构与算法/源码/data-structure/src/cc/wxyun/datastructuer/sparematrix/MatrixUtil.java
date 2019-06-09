package cc.wxyun.datastructuer.sparematrix;

public class MatrixUtil {

	/**
	 * ��ӡ����
	 * 
	 * @param matrix
	 */
	public static void print(int[][] matrix) {
		for (int[] is : matrix) {
			for (int i : is) {
				System.out.printf("%d\t", i);
			}
			System.out.println();
		}
	}

	/**
	 * ��ԭʼ����ת��Ϊϡ�����
	 * 
	 * @param matrix
	 * @return
	 */
	public static SpareMatrix matrix2SpareMatrix(Matrix matrix) {
		SpareMatrix spareMatrix = new SpareMatrix(matrix.getCount(), matrix.getCols(), matrix.getRows());
		for (RowData node : matrix) {
			if (node.getValue() != 0) {
				spareMatrix.put(node.getRow(), node.getCol(), node.getValue());
			}
		}
		return spareMatrix;
	}

	/**
	 * ϡ�����ת��Ϊԭʼ����
	 * 
	 * @param spareMatrix
	 * @return
	 */
	public static Matrix spareMatrix2Matrix(SpareMatrix spareMatrix) {
		RowData metaData = spareMatrix.getMetaData();
		Matrix matrix = new Matrix(metaData.getCol(), metaData.getRow());
		for (RowData rowData : spareMatrix) {
			matrix.put(rowData.getRow(), rowData.getCol(), rowData.getValue());
		}
		return matrix;
	}

}
