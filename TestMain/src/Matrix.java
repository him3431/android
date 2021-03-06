
public class Matrix {
//////////////////////////////////// lab2v3 ////////////////////////////	
	private static int nAlloc = 0;	// 공유 변수를 두어 할당한 공간을 카운트한다.
	private static int nFree = 0;	// 공유 변수를 두어 할당한 공간을 카운트한다.
	protected void finalize() throws Throwable {	//부모클래스에서 정의되어 있다. 자식 클래스에서 재정의 한다. 부모의 형식을 따라서 throwable을 사용해야 한다.
		super.finalize();	// 자바는 소멸자가 없다. 그래서 finalize함수를 소멸자 처럼 생각하면 된다. 부모 클래스의 finalize함수를 상속(왜 상속받는가?)받아서 사용한다.
		nFree++; // count the number of freed objects
	}
	public int get_nAlloc() { return nAlloc; }
	public int get_nFree() { return nFree; }
////////////////////////////////////////////////////////////////////////
	private int dy = 0;
    public int get_dy() {return dy;}
    private int dx = 0;
    public int get_dx() {return dx;}
    private int[][] array = null;
    public int[][] get_array() {return array;}
    private void alloc(int cy, int cx) throws MatrixException{
        if((cy < 0) || (cx < 0)) {
        	throw new MatrixException("wrong matrix size");
//            System.out.println("wrong matrix size");
//            System.exit(0);
        }
        dy = cy;
        dx = cx;
        array = new int[dy][dx];
        nAlloc++; // count the number of allocated objects alloc함수를 불러올 때마다 카운트 되므로 할당된 메모리가 얼마나 많은지 알 수 있다.
    }
    public Matrix() throws MatrixException{ alloc(0, 0);}
    public Matrix(int cy, int cx) throws MatrixException{
        alloc(cy, cx);
        for(int y = 0; y < dy; y++)
            for(int x = 0; x < dx; x++)
                array[y][x] = 0;
    }
    public Matrix(Matrix obj) throws MatrixException{
        alloc(obj.dy, obj.dx);
        for(int y = 0; y < dy; y++)
            for(int x = 0; x < dx; x++)
                array[y][x] = obj.array[y][x];
    }
    public Matrix(int[][] arr) throws MatrixException{
        alloc(arr.length, arr[0].length);
        for(int y = 0; y < dy; y++)
            for(int x = 0; x < dx; x++)
                array[y][x] = arr[y][x];
    }
    public Matrix clip(int top, int left, int bottom, int right) throws MatrixException{
        int cy = bottom - top;
        int cx = right - left;
        Matrix temp = new Matrix(cy, cx);
        for(int y = 0; y < cy; y++)
            for(int x = 0; x < cx; x++)
                if((top+y >= 0) && (left+x >= 0) && (top+y < dy) && (left+x < dx))
                    temp.array[y][x] = array[top+y][left+x];
                else{
                	throw new MatrixException("invalid matrix range");
//                    System.out.println("invalid matrix range");
//                    System.exit(0);
                }
        return temp;
    }
    public void paste(Matrix obj, int top, int left) throws MatrixException{
        for(int y = 0; y < obj.dy; y++)
            for(int x = 0; x < obj.dx; x++)
                if((top+y >= 0) && (left+x >= 0) && (top+y < dy) && (left+x < dx))
                    array[y + top][x + left] = obj.array[y][x];
                else {
                	throw new MatrixException("invalid matrix range");
//                    System.out.println("invalid matrix range");
//                    System.exit(0);
                }
    }
    public Matrix add(Matrix obj) throws MatrixException{
        if((dx != obj.dx) || (dy != obj.dy)) {			//dx, dy의 크기가 각각 다르면 exception 발생  
        	throw new MismatchedMatrixException("matrix sizes mismatch");
//            System.out.println("matrix sizes mismatch");
//            System.exit(0);
        }
        Matrix temp = new Matrix(dy, dx);
        for(int y = 0; y < obj.dy; y++)
            for(int x = 0; x < obj.dx; x++)
                temp.array[y][x] = array[y][x] + obj.array[y][x];
        return temp;
    }
    public int sum() {
        int total = 0;
        for(int y = 0; y < dy; y++)
            for(int x = 0; x < dx; x++)
                total += array[y][x];
        return total;
    }
    public void mulc(int coef) {
        for(int y = 0; y < dy; y++)
            for(int x = 0; x < dx; x++)
                array[y][x] = coef * array[y][x];
    }
    public boolean anyGreaterThan(int val) {		
        for(int y = 0; y < array.length; y++)
            for(int x = 0; x < array[0].length; x++){
                if(array[y][x] > val) return true;
            }
            return false;
    }
    public void print() {
        System.out.println("Matrix(" + dy + "," + dx + ")");
        for(int y = 0; y < dy; y++){
            for(int x = 0; x < dx; x++)
                System.out.print(array[y][x] + " ");
            System.out.println();
        }
    }
    // end of Matrix
}
class MatrixException extends Exception {
	public MatrixException() { super("Matrix Exception"); }
	public MatrixException(String msg) { super(msg); }
}
class MismatchedMatrixException extends MatrixException {
	public MismatchedMatrixException() { super("Mismatched Matrix Exception"); }
	public MismatchedMatrixException(String msg) { super(msg); }
}