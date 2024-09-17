package assignment3;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		int totalScore = 0;
		Color[][] arrays = board.flatten();

		for (int i = 0; i < arrays.length; i++) {
			if(arrays[arrays.length-1][i]==targetGoal){
				totalScore++;
			}
			else if (arrays[0][i] == targetGoal) {
				totalScore++;
			}

		}
		for (int i = 0; i < arrays.length; i++) {
			if(arrays[i][arrays.length-1] == targetGoal){
				totalScore++;
			}
			if(arrays[i][0] == targetGoal){
				totalScore++;
			}
		}
		return totalScore;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell count twice toward the final score!";
	}

}
