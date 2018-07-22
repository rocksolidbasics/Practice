package interviews;

import java.util.Scanner;

class MaxProfitCimpress {
    public static void main(String args[] ) throws Exception {
    	
    	MaxProfitCimpress mpc = new MaxProfitCimpress();
    	
        Scanner s = new Scanner(System.in);
        String cntStr = s.nextLine();                 // Reading input from STDIN
        Integer counts = Integer.parseInt(cntStr);
        
        String profitStr = s.nextLine();
        String[] profitList = profitStr.split(" ");
        
        int[] profits = new int[profitList.length];
        for(int i=0; i<profitList.length; i++) {
        	profits[i] = Integer.parseInt(profitList[i]);
        }
    	
    	//int[] profits = {1, 9, 3, 4, 2, 8};
    	//int[] profits = {1, 2, 3, 6, 4, 12};
        
    	int maxProfit = 0;
    	int max = profits[profits.length-1];
		int maxDoublingBase = max/2;
		
    	for(int i=0; i<profits.length /*&& profits[i] <= maxDoublingBase*/; i++) {
    		int curProfit = mpc.determineMaxProfit(profits, i);
    		if(curProfit > maxProfit)
    			maxProfit = curProfit;
    	}
    	
        System.out.println("\n" + maxProfit);
    }

    /**
     * 1 2 3 4 9 8
     * 
     * @param profits
     * @return
     */
	private int determineMaxProfit(int[] profits, int startPos) {
		this.sort(profits);
		
		int max = profits[profits.length-1];
		int maxDoublingBase = max/2;
		
		int prevValue = -1;
		int maxProfit = 0;
		
		for(int i=startPos; i<profits.length /*&& profits[i] <= maxDoublingBase*/; i++) {
			//trackingList.push(profits[i]);
			if(prevValue == -1)
				prevValue = profits[i];
			
			int nextProfit = prevValue * 2;
			int foundVal = searchNextDoubledProfit(nextProfit, profits);
			
			if(foundVal != 0) {
				if(maxProfit == 0)
					maxProfit = prevValue;
				
				maxProfit = maxProfit + foundVal;
				prevValue = foundVal;
			}
		}

		return maxProfit;
	}
	
	private int searchNextDoubledProfit(int nextProfit, int[] profits) {
		for(int i=0; i<profits.length; i++) {
			if(profits[i] == nextProfit)
				return nextProfit;
		}
		
		return 0;
	}

	private void sort(int[] profits) {
		for(int i=0; i<profits.length; i++) {
			int smallIdx = i;
			for(int j=profits.length-1; j>i; j--) {
				if(profits[j] < profits[smallIdx])
					smallIdx = j;
			}
			
			int tmp = profits[i];
			profits[i] = profits[smallIdx];
			profits[smallIdx] = tmp;
		}
	}
}
