enum TetrisState {
	Running(0), NewBlock(1), Finished(2);
	private final int value;
	private TetrisState(int value) { this.value = value; }
	public int value() { return value; }
}
interface ActionHandler{
	public void run(Tetris t, char key) throws Exception;
}
class OnLeft implements ActionHandler {
	public void run(Tetris t, char key) { t.left = t.left - 1; }
}
class OnRight implements ActionHandler {
	public void run(Tetris t, char key) { t.left = t.left + 1; }
}
class OnDown implements ActionHandler {
	public void run(Tetris t, char key) { t.top = t.top + 1; }
}
class OnUp implements ActionHandler {
	public void run(Tetris t, char key) { t.top = t.top - 1; }
}
class OnCW implements ActionHandler {
	public void run(Tetris t, char key) {
		t.blkDegree = (t.blkDegree + 1) % t.nBlockDegrees;
		t.currBlk = t.blkArray[t.blkType][t.blkDegree];
	}
}
class OnCCW implements ActionHandler {
	public void run(Tetris t, char key) {
		t.blkDegree = (t.blkDegree + 3) % t.nBlockDegrees;
		t.currBlk = t.blkArray[t.blkType][t.blkDegree];
	}
}
class OnNewBlock implements ActionHandler {
	public void run(Tetris t, char key) throws Exception {
		if(t.isJustStarted == false)
			t.oScreen = deleteFullLines(t.oScreen, t.currBlk, t.top, t.iScreenDy);
		t.isJustStarted = false;
		t.iScreen = new Matrix(t.oScreen);
		t.top = 0; t.left = t.iScreenDw + t.iScreenDx/2 - 2;
		t.blkType = key - '0';
		t.blkDegree = 0;
		t.currBlk = t.blkArray[t.blkType][t.blkDegree];
	
	}
	private Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception{
		Matrix line, tmp;
		int cy, y, nDeleted = 0, nScanned = blk.get_dy();
		if(top + blk.get_dy() - 1 >= dy)
			nScanned -= (top + blk.get_dy() - dy);
		for(y = nScanned - 1; y>0; y--) {
			cy = top + y + nDeleted;
			line = screen.clip(cy, 0, cy + 1, screen.get_dx());
			if(line.sum() == screen.get_dx()) {
				tmp = screen.clip(0, 0, cy, screen.get_dx());
				screen.paste(tmp, 1, 0);
				nDeleted++;
			}
		}
		return screen;
	}			
}
class OnFinished implements ActionHandler {
	public void run(Tetris t, char key) {
		System.out.println("OnFinished.run() called");
	}
}

public class Tetris {
	protected static int iScreenDw = 4; // large enough to cover the largest block
	protected static Matrix[][] blkArray;
	private static int nBlockTypes;
	protected static int nBlockDegrees;	
	protected static Matrix[][] setOfBlockObjects;
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
		int max = 0;
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
		iScreenDw = findLargestBlockSize(setOfBlockArrays);
		nBlockDegrees = blkArray[0].length;
	}	
	protected int iScreenDx;
	protected int iScreenDy;
	protected int top ;
	protected int left;
	protected Matrix iScreen;
	protected Matrix oScreen;
	protected Matrix currBlk;	
	protected int blkType;
	protected int blkDegree;
	protected boolean isJustStarted = true;
	private TetrisState state = TetrisState.NewBlock;
	public Tetris(int cy, int cx) throws Exception{
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
/*	public static Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception {
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
	}*/
	private OnLeft onLeft = new OnLeft();
	private OnRight onRight = new OnRight();
	private OnUp onUp = new OnUp();
	private OnDown onDown = new OnDown();
	private OnCW onCW = new OnCW();
	private OnCCW onCCW = new OnCCW();
	private OnNewBlock onNewBlock = new OnNewBlock();
	private OnFinished onFinished = new OnFinished();
	
	class TetrisAction {
		private ActionHandler hDo, hUndo;
		public TetrisAction(ActionHandler d, ActionHandler u) {
			hDo = d; hUndo = u;
		}
		public boolean run(Tetris t, char key, boolean update) throws Exception {
			boolean anyConflict = false;
			hDo.run(t, key);
			Matrix tempBlk;
			tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
			if((anyConflict = tempBlk.anyGreaterThan(1)) == true) {
				hUndo.run(t, key);
				tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);
			}
			if(update == true) {
				oScreen = new Matrix(iScreen);
				oScreen.paste(tempBlk, top, left);
			}
			return anyConflict;
		}
	}
	private TetrisAction moveLeft, moveRight, moveDown, rotateCW, insertBlk;
	public void setOnLeftListener(OnLeft listener){ tetrisActionsInitialized = false; onLeft = listener; }
	public void setOnRightListener( OnRight listener){ tetrisActionsInitialized = false; onRight = listener; }
	public void setOnDonwListener(OnDown listener){ tetrisActionsInitialized = false; onDown = listener; }
	public void setOnUpListener(OnUp listener){ tetrisActionsInitialized = false; onUp = listener; }
	public void setOnCWListener(OnCW listener){ tetrisActionsInitialized = false; onCW = listener; }
	public void setOnCCWListener(OnCCW listener){ tetrisActionsInitialized = false; onCCW = listener; }
	public void setOnNewBlockListener(OnNewBlock listener){ tetrisActionsInitialized = false; onNewBlock = listener; }
	public void setOnFinishedListener(OnFinished listener){ tetrisActionsInitialized = false; onFinished = listener; }
	private void setTetrisActions() {
		moveLeft = new TetrisAction(onLeft, onRight);
		moveRight = new TetrisAction(onRight, onLeft);
		moveDown = new TetrisAction(onDown, onUp);
		rotateCW = new TetrisAction(onCW, onCCW);
		insertBlk = new TetrisAction(onNewBlock, onFinished);
		tetrisActionsInitialized = true;
	}
	private boolean tetrisActionsInitialized = false;
	public TetrisState accept(char key) throws Exception {	// 새 블록이 필요한지 아닌지를 리턴해야 한다. 상태변수로 접근해야 하므로 static은 빼야 한다.
		if(tetrisActionsInitialized == false){
			setTetrisActions();
		}
		if(state == TetrisState.NewBlock){
			if(insertBlk.run(this, key, true) == true)
				state = TetrisState.Finished;
			else
				state = TetrisState.Running;
			return state;
		}
		switch(key) {
		case 'a': moveLeft.run(this, key, true); break; //	move left
		case 'd': moveRight.run(this, key, true); break; // move right
		case 'w': rotateCW.run(this, key, true); break; // rotate the block clockwise
		case 's': 
			if(moveDown.run(this, key, true) == true)
				state = TetrisState.NewBlock;
			break; // move down
		case ' ': 
			while(moveDown.run(this, key, false) == false);
			moveDown.run(this, key, true);
			state = TetrisState.NewBlock;
			break;  // drop the block
		default: System.out.println("unknown key!");
		}
		return state;
	}
}
class TetrisException extends Exception {	// public을 앞에 쓰지 않는 이유? 독자적인 라이브러리 패키지로 만들 때는 Exception 클래스를 상속받는 것이기 때문에 default로 해 줘야 한다.
	public TetrisException() { super("Matrix Exception"); }
	public TetrisException(String msg) { super(msg); }
}
