
//getKey()가 없으므로 필요없다.
/*
import java.io.BufferedReader;
import java.io.IOException;		// getKey() 메소드에서 요구하는 패키지
import java.io.InputStreamReader;
import java.util.Random;
*/
public class Tetris {
	private static int nBlockTypes = 7;
	private static int nBlockDegrees = 4;
	private static Matrix[][] setOfBlockObjects;
	private static int [][][][] setOfBlockArrays = {
	
		{
			{
				{ 1,1,1,1 },
				{ 0,0,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}, //수평블럭 0도 회전
			{
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 1,0,0,0 }
			}, //수평블럭 90도 회전
			{
				{ 1,1,1,1 },
				{ 0,0,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}, //수평블럭 180도 회전
			{
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 1,0,0,0 }
			} //수평블럭 270도 회전
		},
		{
			{
				{0,1,0,0},
				{1,1,1,0},
				{0,0,0,0},
				{0,0,0,0}
			},
			{
				{0,1,0,0},
				{1,1,0,0},
				{0,1,0,0},
				{0,0,0,0}
			},
			{
				{1,1,1,0},
				{0,1,0,0},
				{0,0,0,0},
				{0,0,0,0}
			},
			{
				{1,0,0,0},
				{1,1,0,0},
				{1,0,0,0},
				{0,0,0,0}
			}
		},
		{
			{
				{ 1,1,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}, 
			{
				{ 1,1,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}, 
			{
				{ 1,1,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}, 
			{
				{ 1,1,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			} 
		},
		{
			{
				{ 0,1,0,0 },
				{ 0,1,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,0,0,0 },
				{ 1,1,1,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,1,0,0 },
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,1,1,0 },
				{ 0,0,1,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}
		},
		{
			{
				{ 1,0,0,0 },
				{ 1,0,0,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,1,1,0 },
				{ 1,0,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,1,0,0 },
				{ 0,1,0,0 },
				{ 0,1,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 0,0,1,0 },
				{ 1,1,1,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			}
		},
		{
			{
				{ 1,1,0,0 },
				{ 0,1,1,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 0,1,0,0 },
				{ 1,1,0,0 },
				{ 1,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,1,0,0 },
				{ 0,1,1,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 0,1,0,0 },
				{ 1,1,0,0 },
				{ 1,0,0,0 },
				{ 0,0,0,0 }
			}
		},
		{
			{
				{ 0,1,1,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,0,0,0 },
				{ 1,1,0,0 },
				{ 0,1,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 0,1,1,0 },
				{ 1,1,0,0 },
				{ 0,0,0,0 },
				{ 0,0,0,0 }
			},
			{
				{ 1,0,0,0 },
				{ 1,1,0,0 },
				{ 0,1,0,0 },
				{ 0,0,0,0 }
			}
		}
	};
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
	private static int iScreenDy = 15;
	private static int iScreenDx = 10;
	private static int iScreenDw = 4; // large enough to cover the largest block
	private static int[][] createArrayScreen(int dy, int dx, int dw) {
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
/*	
public static void printScreen(Matrix screen) {
		int dy = screen.get_dy();
		int dx = screen.get_dx();
		int dw = iScreenDw;
		int array[][] = screen.get_array();
		for(int y = 0; y < dy - dw + 1; y++) {
			for(int x = dw - 1; x < dx - dw + 1; x++) {
				if(array[y][x] == 0) System.out.print("◻ ");
				else if(array[y][x] == 1) System.out.print("◼︎︎ ");
				else System.out.print("X ");
			}
			System.out.println();
		}
	}
	public static void printMatrix(Matrix blk) {
		int dy = blk.get_dy();
		int dx = blk.get_dx();
		int array[][] = blk.get_array();
		for (int y = 0; y < dy; y++) {
			for(int x = 0; x < dx; x++) {
				if(array[y][x] == 0) System.out.print("︎◻︎ ");
				else if(array[y][x] == 1) System.out.print("◼︎︎ ");
				else System.out.print("x ");
			}
			System.out.println();
		}
	}
	*/
	////////키를 입력받는 부분은 TestMain에만 존재한다 사용자의 입력에 따라 값이 변하는 부분이므로 Tetris클래스에 있으면 안된다.
/*	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static String line = null;
	private static int nKeys = 0;
	private static char getKey() throws IOException {
		char ch;
		if(nKeys != 0) { // 이전 getKey() 호출의 결과 line 배열에 남아 있는 원소가 있는지 확인한다.
			ch = line.charAt(line.length() - nKeys);
			nKeys--;	// 남아있는 원소들 중에서 첫번째 원소를 ch에 저장하고 리턴한다.
			return ch;
		}
		do {
			line = br.readLine(); // 자바콘솔에서 한 라인을 읽는다.
			nKeys = line.length();
		} while (nKeys == 0);	// 적어도 하나의 key가 입력될 때까지 반복한다.
		ch = line.charAt(0);	// line배열의 첫번째 원소를 ch에 저장하고 리턴한다.
		nKeys--;
		return ch;
	}							// 키 입력을 받아오는 메소드 
*/	
	public static Matrix deleteFullLines(Matrix screen, Matrix blk, int top, int dy) throws Exception {
		Matrix line, tmp;
		int cy, y, nDeleted = 0, nScanned = blk.get_dy();
		if(top + blk.get_dy() - 1 >= dy)
			nScanned -= (top + blk.get_dy() - dy);
		for(y = nScanned - 1; y >= 0; y--) {
			cy = top + y + nDeleted;
			line = screen.clip(cy,  0,  cy+1,  screen.get_dx());
			if(line.sum() == screen.get_dx()) {
				tmp = screen.clip(0,  0,  cy,  screen.get_dx());
				screen.paste(tmp,  1,  0);
				nDeleted++;					
			}
		}
		return screen;
	}
	public static boolean accept(char key, int idxType) throws Exception {	
		boolean newBlockNeeded = false;
		int top = 0; //left = 4;
		int left = iScreenDw + iScreenDx/2 - 2;
		int[][] arrayScreen = createArrayScreen(iScreenDy, iScreenDx, iScreenDw);
		Matrix iScreen = new Matrix(arrayScreen);
		nBlockTypes = setOfBlockArrays.length;
		nBlockDegrees = setOfBlockArrays[0].length;
		setOfBlockObjects = createSetOfBlocks(setOfBlockArrays);
		Random random = new Random();
		int idxBlockType = random.nextInt(nBlockTypes);
		int idxBlockDegree = 0;
		Matrix currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
//		Matrix currBlk = new Matrix(arrayBlk);
		Matrix tempBlk = iScreen.clip(top, left, top + currBlk.get_dy(), left + currBlk.get_dx());
		tempBlk = tempBlk.add(currBlk);
		Matrix oScreen = new Matrix(iScreen);
		oScreen.paste(tempBlk, top, left);
		printScreen(oScreen); System.out.println();
		
//		boolean newBlockNeeded = false;
		while((key = getKey()) != 'q') {
			switch(key) {
			case 'a': left--; break; //	move left
			case 'd': left++; break; // move right
			case 's': top++; break; // move down
			case 'w': idxBlockDegree = (idxBlockDegree + 1) % 4; break; // rotate the block clockwise
			case ' ': while(!newBlockNeeded) { 
				top++; 
				tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk); 
				if(tempBlk.anyGreaterThan(1)) {newBlockNeeded = true;}
				} 
				break;  // drop the block
			default: System.out.println("unknown key!");
			}
			currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
			tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
			tempBlk = tempBlk.add(currBlk);
			if(tempBlk.anyGreaterThan(1)) {
				switch(key) {
				case 'a': left++; break; //	undo: move left
				case 'd': left--; break; // undo: move right
				case 's': top--; newBlockNeeded = true; break;  // undo: move down
				case 'w': idxBlockDegree--; break; // undo: rotate the block counter = clockwise
				case ' ': top--; break; // undo: move up
				}
				currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
				tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);
			}
			oScreen = new Matrix(iScreen);
			oScreen.paste(tempBlk, top, left);
			printScreen(oScreen); System.out.println();
			if(newBlockNeeded) {								//바닥 충돌시 새 블록 출
				iScreen = new Matrix(oScreen);
				top = 0; left = iScreenDw + iScreenDx/2 - 2;
				newBlockNeeded = false;
//				currBlk = new Matrix(arrayBlk);
				random = new Random();
				idxBlockType = random.nextInt(nBlockTypes);
				idxBlockDegree = 0;
				currBlk = setOfBlockObjects[idxBlockType][idxBlockDegree];
				tempBlk = iScreen.clip(top, left, top+currBlk.get_dy(), left+currBlk.get_dx());
				tempBlk = tempBlk.add(currBlk);
				if(tempBlk.anyGreaterThan(1)) {
					System.out.println("Game Over!");	//새 블록 출현시 충돌 있으면 게임 종
					System.exit(0);
				}
				oScreen = new Matrix(iScreen);
				oScreen.paste(tempBlk, top, left);
				printScreen(oScreen); System.out.println();
			}
		}
	}
}
