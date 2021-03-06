
//public class TestMain {
//
//	public static void main(String[] args) {
//		String s1 = "Hello, Java!";
//        System.out.println(s1);
//        String s2 = String.format("%s, %s!", "Hello", "Java");
//        System.out.println(s2);
//
//        int len1 = s1.length();
//        int len2 = s2.length();
//        boolean b1 = s1.equals(s1); // compare s1 and s2 (in content)
//        boolean b2 = s1.equals(s2); // compare s1 and s2 (in content)
//        boolean b3 = (s1 == s2); // compare s1 and s2 (in object)
//
//        System.out.println("s1 =[" + s1 + "], len=" + len1 + ", (s1 equals s1)=" + b1);
//        System.out.println("s2 =[" + s2 + "], len=" + len2 + ", (s1 equals s2)=" + b2);
//        System.out.println("(s1 == s2)=" + b3);
//	}
//
//}

//public class TestMain{
//    public static void main(String[] args){
//        String istr = "1234";
//        String dstr = "12.34";
//
//        int ival = Integer.parseInt(istr);  // string -> integer value
//        double dval = Double.parseDouble(dstr);    //string -> double value
//        System.out.println("before	: " + ival + ", " + dval);
//
//        ival = ival + 1111;
//        dval = dval + 11.11;
//        String istr2 = String.valueOf(ival);    //integer value -> string
//        String dstr2 = String.valueOf(dval);    //double value -> string
//
//        System.out.println("after   : " + istr2 + ", " + dstr2);
//    }
//}

//import java.util.Arrays;
//
//public class TestMain{
//    public static void main(String[] args) {
//        int A1[] = null;
//        int A2[] = { 1, 2, 3, 4, 5 };
//        int[] A3 = new int[5];
//        int A4[] = new int[] { 1, 2, 3, 4, 5 };
//
//        System.out.print("A1:"); printArray(A1);
//        System.out.print("A2:"); printArray(A2);
//        System.out.print("A3:"); printArray(A3);
//        System.out.print("A4:"); printArray(A4);
//        System.out.println("A2.equals(A3)=" + A2.equals(A3));
//        System.out.println("A2.equals(A4)=" + A2.equals(A4));
//        System.out.println("Arrays.equals(A2, A3)=" + Arrays.equals(A2, A3));
//        System.out.println("Arrays.equals(A2, A4)=" + Arrays.equals(A2, A4));
//
//    }
//    public static void printArray(int A[]) {
//        if(A == null) {
//            System.out.println();
//            return;
//        }
//        for(int i = 0; i < A.length; i++)
//            System.out.print(A[i] + " ");
//        System.out.println();
//    }
//}
//public class TestMain{
//    public static void main(String[] args) {
//        Matrix m1 = new Matrix(3,3);
//        m1.print(); System.out.println();
//        int A[][] = { {0, 1, 0}, {1, 1, 1}, {0, 0, 0} };
//        Matrix m2 = new Matrix(A);
//        m2.print(); System.out.println();
//        MyMatrix m3 = new MyMatrix(3, 3);
//        m3.print(); System.out.println();
//        MyMatrix m4 = new MyMatrix(A);
//        m4.print(); System.out.println();
//        m2 = m4;			// 자식 클래스의 print함수가 호출된다. 동적 바인딩 방식 (C++은 부모 클래스의 print함수가 호출. 정적 바인딩 방식 )
//        m2.print(); System.out.println();
//    }
//}
//
//class MyMatrix extends Matrix {					// 자식 클래스도 private변수를 받긴 한다. 다만 볼 수 없을뿐이다.
//    public MyMatrix() { super(); }				// 부모 클래스의 생성자를 직접 호출한다. 생성자를 만들 때 형식은 마음대로 만들어도 된다.
//    public MyMatrix(int cy, int cx) { super(cy, cx); }
//    public MyMatrix(int[][] arr) { super(arr); }
//    public void print() {
//        int dy = get_dy();
//        int dx = get_dx();
//        int array[][] = get_array();
//        for(int y = 0; y < dy; y++) {
//            for(int x = 0; x < dx; x++) {
//                if(array[y][x] == 0) System.out.print("☐ ");
//                else if(array[y][x] == 1) System.out.print("◼︎︎ ");
//                else System.out.print("X ");
//            }
//            System.out.println();
//        }
//    }
//}
//
public class TestMain {
    public static void main(String[] args) {
        Nested m1 = new Nested(1, 2);
        System.out.println("m1.get_dy()=" + m1.get_dy() + ", m1.get_dx()=" + m1.get_dx());
        Nested m2 = new Nested(3, 4);
        System.out.println("m1.get_dy()=" + m1.get_dy() + ", m1.get_dx()=" + m1.get_dx());
        System.out.println("m2.get_dy()=" + m2.get_dy() + ", m2.get_dx()=" + m2.get_dx());
        
        Nested.DynInner d1 = m1.new DynInner();
        Nested.StaInner s1 = new Nested.StaInner();
        
        Nested.DynInner d2 = m2.new DynInner();
        Nested.StaInner s2 = new Nested.StaInner();
        System.out.println("d1.get_dy()=" + d1.get_dy() + ", s1.get_dx()=" + s1.get_dx());
        System.out.println("d2.get_dy()=" + d2.get_dy() + ", s2.get_dx()=" + s2.get_dx());
    }
}

class Nested {
    private int dy;
    private static int dx;		// 객체들 간에 공유되는 변수 
    public int get_dy() { return dy; }
    public static int get_dx() { return dx; }   // can be declared 'dynamic'
    public Nested(int cy, int cx) { dy = cy; dx = cx; }
    public class DynInner { public int get_dy() { return dy; } }	// 정적 변수에 접근이 가능하다.(dx가 공유 변수이기 때문) 
    public static class StaInner { public int get_dx() { return dx; } }	// 정적 변수외에는 접근할 수 없다.
}
