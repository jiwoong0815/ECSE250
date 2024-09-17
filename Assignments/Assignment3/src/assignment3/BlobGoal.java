package assignment3;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		int totalScore = 0;
		Color[][] arrays = board.flatten();
		boolean[][] visited = new boolean[arrays.length][arrays.length];

		for (int i = 0; i < arrays.length; i++) {
			for (int j = 0; j < arrays.length; j++) {
				int blobSize = undiscoveredBlobSize(i,j,arrays,visited);
				if(blobSize > totalScore){
					totalScore = blobSize;
				}
			}
		}
		return totalScore;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		int sum = 0;
		if(visited[i][j] || unitCells[i][j] != targetGoal){
			return 0;
		}
		else if (unitCells[i][j] == targetGoal){
			sum++;
			visited[i][j] = true;

			if(i - 1 >= 0){
				sum += undiscoveredBlobSize(i-1,j,unitCells,visited);
			}
			if(i + 1 < unitCells.length){
				sum += undiscoveredBlobSize(i+1,j,unitCells,visited);
			}
			if(j - 1 >= 0){
				sum += undiscoveredBlobSize(i,j-1,unitCells,visited);
			}
			if(j + 1 < unitCells.length){
				sum += undiscoveredBlobSize(i,j+1,unitCells,visited);
			}
		}
		return sum;
	}
}
