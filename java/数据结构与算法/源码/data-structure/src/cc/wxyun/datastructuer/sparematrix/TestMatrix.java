package cc.wxyun.datastructuer.sparematrix;

public class TestMatrix {

	public static void main(String[] args) {
		/**
		 * ������ԭʼ����
		 */
		Matrix matrix = new Matrix(10, 10);
		matrix.put(0, 0, 1);
		matrix.put(1, 1, 2);
		matrix.put(2, 2, 1);
		System.out.println("ԭʼ����Ϊ:");
		matrix.print();

		/**
		 * ��ԭʼ����ת��Ϊϡ�����
		 */
		SpareMatrix spareMatrix = MatrixUtil.matrix2SpareMatrix(matrix);
		System.out.println("ת��֮���ϡ�����Ϊ:");
		spareMatrix.print();
		
		/**
		 * ϡ�������ת��Ϊԭʼ����
		 */
		Matrix matrix2 = MatrixUtil.spareMatrix2Matrix(spareMatrix);
		System.out.println("��ϡ�����ת��������ԭʼ����Ϊ:");
		matrix2.print();
	}
}
