/**
 * A Connect-4 player that makes a move based on the best next move
 * 
 * @author Daniel Szafir
 *
 */
public class MinimaxPlayer implements Player
{
    private static java.util.Random rand = new java.util.Random();
    int id;
    int opponent_id;
    int cols;
    
    @Override
    public String name() {
        return "Minnie";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id; //id is player's id, opponent's id is 3-id
    	opponent_id = 3-id;
    	this.cols = cols;
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        int maxDepth = 1;
        int move = 0;
        
        while (!arb.isTimeUp() && maxDepth <=board.numEmptyCells()) {
        	//run the first level of minimax search
        	//set col to be the best column corresponding to the best score
        	
        	maxDepth++;
        	arb.setMove(move);
        }
    }
    
    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) { 
//    		if depth = 0 or no more moves or time is up
//    		return the heuristic value of node 
    		
    		if (depth ==0 || board.isFull() || arb.isTimeUp()) {
    			return calcScore(board,id) - calcScore(board, opponent_id);
    		}
    		
//    		if maximizingPlayer then
//    			bestScore = -1000
//    			for each possible move do
   // 				board.move(...) for your player
    		//    	bestScore := max(bestScore, minimax(board, depth - 1, FALSE, arb)) 
    		//		board.unmove(...)
//    			return bestScore
    		if (isMaximizing == true) {
    			int bestScore = -1000;
    			for (cols = 0 ; cols < 7; cols++) {
    				board.move(cols, id);
    				bestScore = Math.max(bestScore, minimax(board, depth - 1, false, arb));
    				board.unmove(cols, id);
    			}
    			return bestScore;
    		}
    		
//    		else /* minimizing player */ 
//    			bestScore = 1000
//    			for each possible move do
//					board.move(...) for your opponenst's pl
//    				bestScore := Math.min(bestScore, minimax(child, depth - 1, FALSE, arb))
    		//		board.unmove(...)
//    			return bestScore
    		else {
    			int bestScore = 1000;
    			for (cols = 0 ; cols < 7; cols++) {
    				board.move(cols, id);
    				bestScore = Math.min(bestScore, minimax(child, depth - 1, false, arb));
    				board.unmove(cols, id);
    			}
    			return bestScore;
    		}
    }
    public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
}