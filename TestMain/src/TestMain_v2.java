//
//public class TestMain_v2 {
//
//	public static void main(String[] args) {
//		int [][] arrayScreen = {
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
//		};
//		int [][] arrayBlk = {
//				{ 0, 1, 0 },
//				{ 1, 1, 1 },
//				{ 0, 0, 0 },
//		};
//		
//		Matrix oScreen = new Matrix(arrayScreen);
//		printMatrix(oScreen); System.out.println();
//		
//		Matrix currBlk = new Matrix(arrayBlk);
//		printMatrix(currBlk); System.out.println();
//		
//		Matrix tempBlk = oScreen.clip(1,  4,  4,  7);
//		printMatrix(tempBlk); System.out.println();
//		
//		tempBlk = tempBlk.add(currBlk);
//		printMatrix(tempBlk); System.out.println();
//		
//		oScreen.paste(tempBlk,  1,  4);
//		printMatrix(oScreen); System.out.println();
//		
//		System.out.println("currBlk.sum()=" + currBlk.sum());
//		System.out.println();
//		
//		tempBlk.mulc(2);
//		tempBlk.print(); System.out.println();
//		
//		System.out.println("currBlk.anyGreaterThan(1)=" + currBlk.anyGreaterThan(1));
//		System.out.println("tempBlk.anyGreaterThan(1)=" + tempBlk.anyGreaterThan(1));
//	}
//	public static void printMatrix(Matrix blk) {
//		int dy = blk.get_dy();
//		int dx = blk.get_dx();
//		int array[][] = blk.get_array();
//		for (int y = 0; y < dy; y++) {
//			for(int x = 0; x < dx; x++) {
//				if(array[y][x] == 0) System.out.print("︎◻︎ ");
//				else if(array[y][x] == 1) System.out.print("◼︎︎ ");
//				else System.out.print("x ");
//			}
//			System.out.println();
//		}
//	}
//}

//public class TestMain_v2 {
//	public static void main(String[] args) throws Exception {		// 76번 줄이 exception을 일으키면 메인함수가 호출한 상위 예외처리로 던지게 된다. 
//		//만들어놓은 예외처리 함수에서 사용하고 싶다면 try문 안으로 76번줄을 넣어야 한다. 
//		int[][] blkArray = {
//				{ 0, 1, 0 },
//				{ 1, 1, 1 },
//				{ 0, 0, 0 }	
//		};
//		Matrix currBlk = new Matrix(blkArray);	// 행렬의 크기가 3x3인 2차원 배열 (세로 x 가로)
//		try {
//			Matrix tempBlk = new Matrix(5, 5); 	// 임시블럭을 만들어서 5x5의 크기로 만든다.
//			tempBlk = tempBlk.add(currBlk);		// 두 개의 블럭을 덧셈한다. add 메소드를 이용 (원소대 원소 덧셈) -> 하지만 다 덧셈할 수 없다.
//			// 에러 발생예상  mismatchedMatrixException 이 잡힌다.
//		} catch(MismatchedMatrixException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			System.out.println("at first catch");
//		} catch(MatrixException e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			System.out.println("at second catch");
//		}
//	}
//}

public class TestMain_v2 {
	public static void main(String[] args) throws Exception {
		Matrix m = new Matrix(10, 10);
		for(int i = 0; i < 999; i++)		// 1000개의 객체를 할
			m = new Matrix(10, 10);			// 계속 새로운 객체를 가리키게 된다. 가장 최근에 할당된 객체만 가리킨다. 자바는 delete라는 키워드가 없다. 시스템이 알아서 해제해준다.
		System.gc(); // System의 garbage collection을 강제 유도함  garbage를 수집해서 메모리에 반납해준다. System.gc()함수는 garbage collection을 수행하라는 명령
		// garbage collection은 별도의 thread가 있다. 그 스레드가 충분한 실행시간이 확보되어야 999개의 garbage를 할당한다. main 부분의 thread 처리 때문에 garbage thread가 모두 모으지 못할 수 있다.
		System.out.println("nAlloc=" + m.get_nAlloc());
		System.out.println("nFree=" + m.get_nFree());
	}
}