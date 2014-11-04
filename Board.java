import java.util.Iterator;

public class Board {
	private int board[][];//later version: to speed things up we could implement with a single dimension array....
	private int dimension;
	private int hamming;
	private int drManhattan;
	
	
    public Board(int[][] blocks){
    		// construct a board from an N-by-N array of blocks
    		board = blocks;
    		dimension = board[0].length;
    		
    		getDistances();
    }
    
    private int getManhattanDistance(int x1,int y1,int x2, int y2,int N){
    		if(x2 == N-1 && y2 == N-1){
    			//this is the zero element.
    			return 0;
    		}
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    private int getHammingValue(int valueOnBoard, int x, int y){
    		if(valueOnBoard==0){
    			return 0;
    		}
    		return (valueOnBoard==x*dimension + y + 1)?0:1;
    }
    private void getDistances(){
    		hamming = 0;
    		    		
		for(int i=0;i<dimension;i++){
			for(int p=0;p<dimension;p++){
				hamming += getHammingValue(board[i][p],i,p);
				drManhattan += getManhattanDistance(i, p,
												//the 2 below parameters are what the numbers SHOULD be, 
												//which will be compared to what number is there already...
												//if the number in the spot is 0, then it ought to be in the last
												//spot, which is [dimension-1][dimension-1]. Otherwise, we get that
												//info with modular arithmetic.
												(board[i][p]==0)?dimension-1:(board[i][p] - 1) / dimension,
												(board[i][p]==0)?dimension-1:(board[i][p] - 1) % dimension,
												dimension);
			}
		}
		
    }
                                           
    public int dimension(){
 
    		return dimension;
    }
    public int hamming(){
    		// number of blocks out of place
    		return hamming;
    }
    public int manhattan(){
    		// sum of Manhattan distances between blocks and goal
    		return drManhattan;
    }
    public boolean isGoal(){
    		return (hamming==0);
    		// is this board the goal board?
    }
    
    public Board twin(){
    	   int twinsy[][] = new int[dimension][dimension];
    	   //a twin is defined as a board with a single switch of adjacent elements in one row (and neither can be the 0th element!)
   		for(int i=0;i<dimension;i++){
   			//first, copy the board
			for(int p=0;p<dimension;p++){
				twinsy[i][p] = board[i][p];
			}
   		}
   		//if neither of the first 2 bits are '0', then we switch them....
		if(twinsy[0][0] != 0 && twinsy[0][1] != 0){
			int temp = twinsy[0][0];
			twinsy[0][0] = twinsy[0][1];
			twinsy[0][1] = temp;
		}else{
			//in this case, we know the '0' element is in the first row, so we switch elements in the last row
			int temp = twinsy[dimension-1][dimension-2];
			twinsy[dimension-1][dimension-2] = twinsy[dimension-1][dimension-1];
			twinsy[dimension-1][dimension-1] = temp;
		}

    		return new Board(twinsy);
    		// a board that is obtained by exchanging two adjacent blocks in the same row
    }
    public boolean equals(Object y){
    		// does this board equal y?
    		//System.out.println("Comparing 2 boards:\n" + toString() + "\nand \n" + y.toString());
    		//System.out.println("\nthe result is: " + toString().compareTo(y.toString())); 	
    		return (y==null)?false:toString().compareTo(y.toString())==0;
    }
    public Iterable<Board> neighbors(){
    		// all neighboring boards
    	 	return new myIterator();
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    private class myIterator implements Iterable<Board>{
    		public Iterator<Board> iterator(){
    			return new myNeighbor(board);
    		}
    }
    private class myNeighbor implements Iterator<Board>{
    	
    		private Board allNeighbors[];
    		int neighborIndex = 0;
    		public myNeighbor(int original[][]){
    			int zeroX = 0,zeroY = 0;
    			int dimension = original[0].length;
    			//we need to add 
    			int left[][] = new int[dimension][dimension];
    			int right[][] = new int[dimension][dimension];
    			int up[][] = new int[dimension][dimension];
    			int down[][] = new int[dimension][dimension];
    			
    			for(int x=0;x<dimension;x++){
    				for(int y=0;y<dimension;y++){
    					left[x][y] = original[x][y];
    					up[x][y] = original[x][y];
    					down[x][y] = original[x][y];
    					right[x][y] = original[x][y];
    					if(original[x][y] == 0){
    						zeroX = x;
    						zeroY = y;
    					}
    				}
    			}//end top for
    			//check if there is anything above
    			int numberOfNeighbors = 4;//the max we can have
    			//if we are on an edge then we account for that by reducing the 
    			//number of neighbors...
    			if(zeroX == 0){
    				up = null;
    				numberOfNeighbors--;
    			}
    			if(zeroX == dimension-1){
    				down = null;
    				numberOfNeighbors--;
    			}
    			if(zeroY == 0){
    				left = null;
    				numberOfNeighbors--;
    			}
    			if(zeroY == dimension - 1){
    				right = null;
    				numberOfNeighbors--;
    			}
    			
    			Board UP,DOWN,LEFT,RIGHT;
    			allNeighbors = new Board[numberOfNeighbors];
    			int neighborIndex = 0;
    			//we only instantiate the above objects if they are valid (we are not on an edge)
    			if(up!=null){
    				up[zeroX][zeroY] = up[zeroX-1][zeroY]; 
    				up[zeroX-1][zeroY] = 0; 
    				UP = new Board(up);
    				allNeighbors[neighborIndex++] = UP;
    			}
    			
    			if(right != null){
    				right[zeroX][zeroY] = right[zeroX][zeroY+1]; 
    				right[zeroX][zeroY+1] = 0; 
    				RIGHT = new Board(right);
    				allNeighbors[neighborIndex++] = RIGHT;
    			}
    			
    			if(down!=null){
    				down[zeroX][zeroY] = down[zeroX+1][zeroY]; 
    				down[zeroX+1][zeroY] = 0; 
    				DOWN = new Board(down);
    				allNeighbors[neighborIndex++] = DOWN;
    			}
    			
    			if(left != null){
    				left[zeroX][zeroY] = left[zeroX][zeroY-1]; 
    				left[zeroX][zeroY-1] = 0; 
    				LEFT = new Board(left);
    				allNeighbors[neighborIndex++] = LEFT;
    			}
    			//at the end, the allNeighbors array has only valid neighbors in it...
    		}
    	@Override
		public boolean hasNext() {
			return (neighborIndex<allNeighbors.length)?true:false;
		}

		@Override
		public Board next() {
			return allNeighbors[neighborIndex++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("this operation is not supported, holmes");
		}
    }
    
    
    public static void main(String[] args){
   }
}
