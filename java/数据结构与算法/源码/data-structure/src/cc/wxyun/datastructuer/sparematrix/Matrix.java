package cc.wxyun.datastructuer.sparematrix;

import java.util.Iterator;

/**
 * 稀疏矩阵
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
	 * 向矩阵中添加元素
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
	 * 获取矩阵的列数
	 * 
	 * @return
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * 获取矩阵的行数
	 * 
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 返回矩阵中有效数据的个数
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
	 * 循环原始矩阵的迭代器
	 * 
	 * @author wanxing
	 *
	 */
	class MatrixIterator implements Iterator<RowData> {
		/**
		 * 当前循环的行下标
		 */
		private int pointerRow;

		/**
		 * 当前循环的列下标
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
