package cc.wxyun.datastructuer.sparematrix;

import java.util.Iterator;

/**
 * ѹ��֮���ϡ�����
 * @author wanxing
 *
 */
public class SpareMatrix implements Iterable<RowData> {

	private int[][] matrix;

	private int currRow;

	/**
	 * 
	 * @param count
	 *            ��Ч���ݵĸ���
	 */
	public SpareMatrix(int count, int cols, int rows) {
		matrix = new int[count + 1][3];
		matrix[0][0] = rows;
		matrix[0][1] = cols;
		matrix[0][2] = count;
		currRow = 1;
	}

	/**
	 * ���Ԫ�ص�ϡ�������
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
	 * ��ȡԭʼ�����Ԫ����
	 * <p>
	 * RowData.col ԭʼ���������<br>
	 * RowData.row ԭʼ��������� <br>
	 * RowData.value ԭʼ������Ч���ݵĸ���<br>
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
