import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class TestMain_v4 {
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
	}							// 키 입력을 받아오는 메소드 
	public static void main(String[] args) {
		char key;
		int idxType;
		boolean newBlockNeeded;
		Tetris.init(setOfBlockArrays);
		Tetris.board = new Tetris(15, 10);
		Random random = new Random.netInt(7);
		board.accept('0', idxType);
		board.printScreen(); System.out.println();
		
		while((key = getKey()) != 'q') {		//키를 받고 게임엔진을 부르는 방식으로 tetris의 while함수를 빼줘야 한다.
			newBlockNeeded = board.accept(key, idxType);	//accept는 하나의 키만 가지고 게임엔진이 판단하면 된다.
			board.printScreen(); System.out.println();
			if(newBlockNeeded) {
				idxType = random.nextInt(7);
				newBlockNeeded = board.accept('0', idxType);
				board.priintScreen(); System.out.println();
				if(newBlockNeeded) break;
			}
		}
		System.out.println("Program terminated!");
	}

}
