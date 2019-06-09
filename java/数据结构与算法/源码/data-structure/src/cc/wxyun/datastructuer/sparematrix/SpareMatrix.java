package cc.wxyun.datastructuer.sparematrix;

import java.util.Iterator;

/**
 * 压缩之后的稀疏矩阵
 * @author wanxing
 *
 */
public class SpareMatrix implements Iterable<RowData> {

	private int[][] matrix;

	private int currRow;

	/**
	 * 
	 * @param count
	 *            有效数据的个数
	 */
	public SpareMatrix(int count, int cols, int rows) {
		matrix = new int[count + 1][3];
		matrix[0][0] = rows;
		matrix[0][1] = cols;
		matrix[0][2] = count;
		currRow = 1;
	}

	/**
	 * 添加元素到稀疏矩阵中
	 * 
	 * @param row
	 * @param col
	 * @param value
	 * @return
	 */
	public SpareMatrix put(int row, int col, int value) {
		matrix[currRow][0] = row;
		matrix[currRow][1] = col;
		matrix[currRow][2] = value;
		currRow++;
		return this;
	}

	/**
	 * 获取原始矩阵的元数据
	 * <p>
	 * RowData.col 原始矩阵的列数<br>
	 * RowData.row 原始矩阵的行数 <br>
	 * RowData.value 原始矩阵有效数据的个数<br>
	 * </p>
	 * 
	 * @return
	 */
	public RowData getMetaData() {
		return new RowData(matrix[0][0], matrix[0][1], matrix[0][2]);
	}

	public void print() {
		MatrixUtil.print(matrix);
	}

	class SpareMatrixIterator implements Iterator<RowData> {

		private int currRow = 1;

		@Override
		public boolean hasNext() {
			return currRow <= matrix[0][2];
		}

		@Override
		public RowData next() {
			RowData rowData = new RowData(matrix[currRow][0], matrix[currRow][1], matrix[currRow][2]);
			currRow++;
			return rowData;
		}
	}

	@Override
	public Iterator<RowData> iterator() {
		return new SpareMatrixIterator();
	}

}
