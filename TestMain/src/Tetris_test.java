enum TetrisState {
	Running(0), NewBlock(1), Finished(2);
	private final int value;
	private TetrisState(int value) { this.value = value; }
	public int value() { return value; }
}
public class Tetris_test {
	private static int iScreenDw = 4; // large enough to cover the largest block
	private static Matrix[][] blkArray;
	private static int nBlockTypes;
	private static int nBlockDegrees;	
	private static Matrix[][] setOfBlockObjects;
	private static Matrix[][] createSetOfBlocks(int[][][][] setOfArrays) throws Exception {
		//	블록의 종류에 따른 블록 선택 
		//	회전한 숫자에 따른 2차원 블록 리턴
		Matrix[][] buffer = new Matrix[setOfArrays.length][setOfArrays[0].length];
		for(int i = 0; i < setOfArrays.length; i++){
			for(int j = 0; j < setOfArrays[0].length; j++){
				buffer[i][j] = new Matrix(setOfArrays[i][j]);				
			}
		}	
		return buffer;
	};
	private static int max(int a, int b) { return (a > b ? a : b); }
	private static int findLargestBlockSize(int [][][][] setOfArrays) {
		int max = 99999999;
		for(int i = 0; i < setOfArrays.length; i++)
		{
			for(int j = 0; j < setOfArrays[0].length; j++)
			{
				if(max < setOfArrays[i][j].length)
				{
					max = setOfArrays[i][j].length;
				}
			}
		}
		return max;
	}
	public static void init(int[][][][] setOfBlockArrays) throws MatrixException {	//init 함수는 hidden object를 초기화 한다. -> static으로 선
		//4차원 배열이 28개라는 것을 알아내야 한다.
		int nTypes = setOfBlockArrays.length;		//블록의 종류  
		int nDegrees = setOfBlockArrays[0].length;	//블록의 회전수  
		blkArray = new Matrix[nTypes][nDegrees];	//blkArray 객체를 최초 할당해 주어야 Martix객체의 구체적인 배열을 할당할 수 있다. **** 자주 나오는 버그이므로 잘 기억해두자****
		for(int t = 0; t < nTypes; t++)
			for(int d = 0; d < nDegrees; d++)
				blkArray[t][d] = new Matrix(setOfBlockArrays[t][d]);	// blkarray의 크기가 정해져 있지 않다. 그래서 미리 blkArray의 크기를 알려줘야 한다.
		iScreenDw = 4;//findLargestBlockSize(setOfBlockArrays);
	}	
	private int iScreenDx;
	private int iScreenDy;
	private int top ;
	private int left;
	private Matrix iScreen;
	private Matrix oScreen;
	private int blkType;
	private int blkDegree;
	private Matrix currBlk;	
	private boolean isJustStarted = true;
	private TetrisState state = TetrisState.NewBlock;
	public Tetris_test(int cy, int cx) throws Exception{
		if(cy <= iScreenDw || cx <= iScreenDw)
			throw new TetrisException("Tetris: too small space");
		iScreenDx = cx;
		iScreenDy = cy;
		int[][] arrayScreen = createArrayScreen(cy, cx, iScreenDw); 
		iScreen = new Matrix(arrayScreen);
		oScreen = new Matrix(arrayScreen);	// 초기화된 채로 있어야 accept에서 사용 가능하다. 
	}
	private int[][] createArrayScreen(int dy, int dx, int dw) {
		int y, x;
		int[][] array = new int[dy + dw][dx + 2*dw];
		for(y = 0; y < array.length; y++)
			for(x = 0; x < dw; x++)
				array[y][x] = 1;
		for (y = 0; y < array.length; y++)
			for(x = dw + dx; x < array[0].length; x++)
				array[y][x] = 1;
		for(y = dy; y < array.length; y++)
			for(x = 0; x < array[0].length; x++)
				array[y][x] = 1;
		return array;
	}
	public void printScreen() {
		int dy = oScreen.get_dy();
		int dx = oScreen.get_dx();
		int dw = iScreenDw;
		int array[][] = oScreen.get_array();
		for(int y = 0; y < dy - dw + 1; y++) {
			for(int x = dw - 1; x < dx - dw + 1; x++) {
				if(array[y][x] == 0) System.out.print("◻ ");
				else if(array[y][x] == 1) System.out.print("◼︎︎ ");
				else System.out.print("X ");
			}
			System.out.println();
		}
	}
	public static Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception {
		Matrix line, tmp;
		int y;
		for(y = dy - 1; y >= 0; y--)
		{
			line = screen.clip(y, 0, y + 1, screen.get_dx());
			if(line.sum() == screen.get_dx())
			{
				tmp = screen.clip(0, 0, y, screen.get_dx());
				screen.paste(tmp,  1,  0);
				y++;
			}
		}
		return screen;
	}

	public TetrisState accept(char key) throws Exception {	// 새 블록이 필요한지 아닌지를 리턴해야 한다. 상태변수로 접근해야 하므로 static은 빼야 한다.
		Matrix tempBlk;
		if(state == TetrisState.NewBlock){
			if(isJustStarted == false)
				oScreen = deleteFullLines(oScreen, currBlk, top, iScreenDy);
			isJustStarted = false;
			iScreen = new Matrix(oScreen);
			top = 0; left = iScreenDw + iScreenDx/2 - 2;
			blkType = key - '0';
			blkDegree = 0;
			currBlk = blkArray[blkType][blkDegree];
			state = TetrisState.Running;
			currBlk = blkArray[blkType][blkDegree];
			tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
			oScreen = new Matrix(iScreen);
			oScreen.paste(tempBlk, top, left);	
		
			return state;
		}
		if(key == '0') {	//'0' 의 키값이 들어오면 새로운 블록을 출력한다.
			iScreen = new Matrix(oScreen);	//충돌 이후 새로운 블럭을 정할 때 필요 땅바닥에 쌓은 블럭을 포함한 배경화면을 초기화해 준다.
			state = TetrisState.Running;
			top = 0;
			left = iScreenDw + iScreenDx / 2 - 2;
			blkType = key - '0';
			blkDegree = 0;
			currBlk = blkArray[blkType][blkDegree];	//indexBlock을 이용한 블럭 선택 부분  
			
			tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
			// 충돌 테스트 
			if(tempBlk.anyGreaterThan(1) == true)
				state = TetrisState.Finished;
			oScreen.paste(tempBlk, top, left);
			return state;
			/*
			 * accept 함수가 해야 할 일은 블록 충돌여부와 새로운 블록 출력이다.
			 */
		}
		switch(key) {
		case 'a': left--; break; //	move left
		case 'd': left++; break; // move right
		case 's': top++; break; // move down
		case 'w': blkDegree = (blkDegree + 1) % 4; break; // rotate the block clockwise
		case ' ': while(state == TetrisState.Running) { 
				top++; 
				tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk); 
				if(tempBlk.anyGreaterThan(1)) {state = TetrisState.NewBlock;}
			} 
			break;  // drop the block
		default: 
			System.out.println("unknown key!");
			return state;
		}
		currBlk = blkArray[blkType][blkDegree];
		tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
		tempBlk = tempBlk.add(currBlk);
		if(tempBlk.anyGreaterThan(1)) {
			switch(key) {
			case 'a': left++; break; //	undo: move left
			case 'd': left--; break; // undo: move right
			case 's': top--; state = TetrisState.NewBlock; break;  // undo: move down
			case 'w': blkDegree = (blkDegree + 3) %4; break; // undo: rotate the block counter = clockwise
			case ' ': top--; break; // undo: move up
			}
			currBlk = blkArray[blkType][blkDegree];
			tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
		}
		oScreen = new Matrix(iScreen);
		oScreen.paste(tempBlk, top, left);	
		deleteFullLines(oScreen, currBlk, top, iScreenDy);
		
		return state;
	}
}
class TetrisException extends Exception {	// public을 앞에 쓰지 않는 이유? 독자적인 라이브러리 패키지로 만들 때는 Exception 클래스를 상속받는 것이기 때문에 default로 해 줘야 한다.
	public TetrisException() { super("Matrix Exception"); }
	public TetrisException(String msg) { super(msg); }
}
