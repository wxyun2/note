package cc.wxyun.datastructuer.sparematrix;

import java.util.Iterator;

/**
 * ϡ�����
 * 
 * @author wanxing
 *
 */
public class Matrix implements Iterable<RowData> {

	private int[][] source;

	private int cols;

	private int rows;

	private int cout;

	public Matrix(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		source = new int[rows][cols];
	}

	/**
	 * ����������Ԫ��
	 * 
	 * @param col
	 * @param row
	 * @param value
	 * @return
	 */
	public Matrix put(int row, int col, int value) {
		this.source[row][col] = value;
		cout++;
		return this;
	}

	public Matrix remove(int col, int row) {
		this.source[row][col] = 0;
		cout--;
		return this;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * ���ؾ�������Ч���ݵĸ���
	 * 
	 * @return
	 */
	public int getCount() {
		return cout;
	}

	public void print() {
		MatrixUtil.print(source);
	}

	/**
	 * ѭ��ԭʼ����ĵ�����
	 * 
	 * @author wanxing
	 *
	 */
	class MatrixIterator implements Iterator<RowData> {
		/**
		 * ��ǰѭ�������±�
		 */
		private int pointerRow;

		/**
		 * ��ǰѭ�������±�
		 */
		private int pointerCol;

		@Override
		public boolean hasNext() {
			return pointerRow < rows || pointerCol < cols;
		}

		@Override
		public RowData next() {
			return new RowData(pointerRow, pointerCol, source[pointerRow++][pointerCol++]);
		}
	}

	@Override
	public Iterator<RowData> iterator() {
		return new MatrixIterator();
	}
}
