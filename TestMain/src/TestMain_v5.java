import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
public class TestMain_v5 {
	private static int [][][][] setOfBlockArrays = {

			{
				{
					{ 0,0,0,0 },
					{ 1,1,1,1 },
					{ 0,0,0,0 },
					{ 0,0,0,0 }
				}, //수평블럭 0도 회전
				{
					{ 0,0,1,0 },
					{ 0,0,1,0 },
					{ 0,0,1,0 },
					{ 0,0,1,0 }
				}, //수평블럭 90도 회전
				{
					{ 0,0,0,0 },
					{ 1,1,1,1 },
					{ 0,0,0,0 },
					{ 0,0,0,0 }
				}, //수평블럭 180도 회전
				{
					{ 0,0,1,0 },
					{ 0,0,1,0 },
					{ 0,0,1,0 },
					{ 0,0,1,0 }
				} //수평블럭 270도 회전
			},
			{
				{
					{0,1,0},
					{1,1,1},
					{0,0,0}
				},
				{
					{0,1,0},
					{1,1,0},
					{0,1,0}
				},
				{
					{1,1,1},
					{0,1,0},
					{0,0,0}
				},
				{
					{1,0,0},
					{1,1,0},
					{1,0,0}
				}
			},
			{
				{
					{ 1,1 },
					{ 1,1 }
				}, 
				{
					{ 1,1 },
					{ 1,1 }
				}, 
				{
					{ 1,1 },
					{ 1,1 }
				}, 
				{
					{ 1,1 },
					{ 1,1 }
				} 
			},
			{
				{
					{ 0,1,0 },
					{ 0,1,0 },
					{ 1,1,0 }
				},
				{
					{ 1,0,0 },
					{ 1,1,1 },
					{ 0,0,0 }
				},
				{
					{ 1,1,0 },
					{ 1,0,0 },
					{ 1,0,0 }
				},
				{
					{ 1,1,1 },
					{ 0,0,1 },
					{ 0,0,0 }
				}
			},
			{
				{
					{ 1,0,0 },
					{ 1,0,0 },
					{ 1,1,0 }
				},
				{
					{ 1,1,1 },
					{ 1,0,0 },
					{ 0,0,0 }
				},
				{
					{ 1,1,0 },
					{ 0,1,0 },
					{ 0,1,0 }
				},
				{
					{ 0,0,1 },
					{ 1,1,1 },
					{ 0,0,0 }
				}
			},
			{
				{
					{ 1,1,0 },
					{ 0,1,1 },
					{ 0,0,0 }
				},
				{
					{ 0,1,0 },
					{ 1,1,0 },
					{ 1,0,0 }
				},
				{
					{ 1,1,0 },
					{ 0,1,1 },
					{ 0,0,0 }
				},
				{
					{ 0,1,0 },
					{ 1,1,0 },
					{ 1,0,0 }
				}
			},
			{
				{
					{ 0,1,1 },
					{ 1,1,0 },
					{ 0,0,0 }
				},
				{
					{ 1,0,0 },
					{ 1,1,0 },
					{ 0,1,0 }
				},
				{
					{ 0,1,1 },
					{ 1,1,0 },
					{ 0,0,0 }
				},
				{
					{ 1,0,0 },
					{ 1,1,0 },
					{ 0,1,0 }
				}
			}
			};
			private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
			}	
	public static void main(String[] args) throws Exception{
		OnLeft myOnLeft = new OnLeft() {
			public void run(Tetris t, char key) { t.left = t.left - 1; }
		};
		OnRight myOnRight = new OnRight() {
			public void run(Tetris t, char key) { t.left = t.left + 1; }
		};
		OnDown myOnDown = new OnDown() {
			public void run(Tetris t, char key) { t.top = t.top + 1; }
		};
		OnUp myOnUp = new OnUp() {
			public void run(Tetris t, char key) { t.top = t.top - 1; }
		};
		OnCW myOnCW = new OnCW() {
			public void run(Tetris t, char key) {
				t.blkDegree = (t.blkDegree + 1) % t.nBlockDegrees;
				t.currBlk = t.blkArray[t.blkType][t.blkDegree];
			}
		};
		OnCCW myOnCCW = new OnCCW() {
			public void run(Tetris t, char key) { 
				t.blkDegree = (t.blkDegree+3) % t.nBlockDegrees;
				t.currBlk = t.blkArray[t.blkType][t.blkDegree];
			}
		};
		OnNewBlock myOnNewBlock = new OnNewBlock() {
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
		};
		OnFinished myOnFinished = new OnFinished() {
			public void run(Tetris t, char key) {
				System.out.println("OnFinished.run(); called");
			}
		};
		char key;
		TetrisState state;
		Tetris.init(setOfBlockArrays);
		Tetris board = new Tetris(15, 10);
		board.setOnLeftListener(myOnLeft);
		board.setOnRightListener(myOnRight);
		board.setOnDonwListener(myOnDown);
		board.setOnUpListener(myOnUp);
		board.setOnCWListener(myOnCW);
		board.setOnCCWListener(myOnCCW);
		board.setOnNewBlockListener(myOnNewBlock);
		board.setOnFinishedListener(myOnFinished);
		Random random = new Random();
		key = (char) ('0' + random.nextInt(7));
		board.accept(key);
		board.printScreen(); System.out.println();
		
		while((key = getKey()) != 'q') {		//키를 받고 게임엔진을 부르는 방식으로 tetris의 while함수를 빼줘야 한다.
			state = board.accept(key);	//accept는 하나의 키만 가지고 게임엔진이 판단하면 된다.
			board.printScreen(); System.out.println();
			if(state == TetrisState.NewBlock) {
				key = (char)('0' + random.nextInt(7));
				state = board.accept(key); // accept함수가 2번째 블렸을 때는 새로 정한 블럭을 배경에 추가하는 작업이 필요해진다.
				board.printScreen(); System.out.println();
				if(state == TetrisState.Finished) break;	//Game Over!
			}
		}
		System.out.println("Program terminated!");
	}

}
