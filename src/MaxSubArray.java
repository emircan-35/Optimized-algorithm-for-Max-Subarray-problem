
import java.util.Random;

public class MaxSubArray {

	public static void main(String[] args) {
		testAlgorithms();
	}
	
	public static void testAlgorithms() {
		Random rnd=new Random();
		int optimizedPoint=0;
		for (int i = 0; i < 2200; i++) {
			int lengthArray=10;
			while (true) {
				int[] array=new int[lengthArray];
				for (int j = 0; j < array.length; j++) {
					int num=rnd.nextInt(1000);
					if (rnd.nextInt(2)==1) num=num*-1;
					array[j]=num;
				}
				
		        long startBrute = System.nanoTime();
		        Tuple brute=findBruteForce(array,0,array.length-1);
		        long endBrute = System.nanoTime();
		        long resultBrute=Math.abs(endBrute-startBrute);
		        
		        
		        long startDivide = System.nanoTime();
		        Tuple divide=findMaximumSubArray(array, 0, lengthArray-1);
		        long endDivide = System.nanoTime();
		        long resultDivide=Math.abs(endDivide-startDivide);
				
		        if (resultDivide<resultBrute) {
		        	System.out.println(lengthArray);
					optimizedPoint+=lengthArray;
					break;
				}
				lengthArray++;
			}
		}
		optimizedPoint=optimizedPoint/2200;
		
		System.out.println("optimized point is "+optimizedPoint);
		//NOW TIME TO OPTIMIZE AND GET THE RESULTS
		
		int[] lastResult=new int[1000];
		for (int i = 0; i < lastResult.length; i++) {
			int[] array=new int[i+10];
			for (int j = 0; j < array.length; j++) {
				int number=rnd.nextInt(1000);
				if (rnd.nextInt(2)==1) number=number*-1;
				array[j]=number;
			}
	        long startBrute = System.nanoTime();
	        findBruteForce(array,0,array.length-1);
	        long endBrute = System.nanoTime();
	        long resultBrute=endBrute-startBrute;
	        
	        
	        long startDivide = System.nanoTime();
	        findMaximumSubArray(array, 0, array.length-1);
	        long endDivide = System.nanoTime();
	        long resultDivide=endDivide-startDivide;
	        
	        long startOptimized= System.nanoTime();
	        optimizedFindMaximumSubArray(optimizedPoint,array, 0, array.length-1);
	        long endOptimized= System.nanoTime();
	        long resultOptimized=endOptimized-startOptimized;
	        
	        if (resultOptimized<resultDivide&&resultOptimized<resultBrute) {
				lastResult[i]=2;
			}else if(resultBrute<resultOptimized&&resultBrute<resultDivide) {
				lastResult[i]=0;
			}else {
				lastResult[i]=1;
			}
		}
		int lastResultBrute=0;
		int lastResultDivide=0;
		int lastResultOptimized=0;
		for (int i = 0; i < lastResult.length; i++) {
			if (lastResult[i]==1) {
				lastResultDivide++;
			}else if(lastResult[i]==2) {
				lastResultOptimized++;
			}else {
				lastResultBrute++;
			}
		}
		System.out.println("Firstly, 500 random calculation is repeated for finding the optimized point, which is found as "+optimizedPoint);
		System.out.println("LAST RESULTS\n\n With optimized and other two algorithm, totally randomized calculations created is repeated for 1000 time\n\nAccording to the results;");
		System.out.println("\nBrute: "+lastResultBrute);
		System.out.println("\nDivide: "+lastResultDivide);
		System.out.println("\nOptimized: "+lastResultOptimized);
		
	}
	public static Tuple findBruteForce(int[] array,int low, int high) {
		int max=0;
		Tuple infos=new Tuple(3);
		for (int i = low; i < high+1; i++) {
			int maxLocal=0;
			int count=0;
			int lastIndex=0;
			for (int j = i; j < high+1; j++) {
				count+=array[j];
				if (count>maxLocal) {
					maxLocal=count;
					lastIndex=j;
				}
			}
			if (maxLocal>max) {
				max=maxLocal;
				infos.deleteAllElements();
				infos.setElement(0, i);
				infos.setElement(1, lastIndex);
				infos.setElement(2, max);
			}
		}
		return infos;
	}
	
	
	public static Tuple findMaxCrossingSubArray(int[] array, int low, int mid, int high) {
		Tuple indexes=new Tuple(3);
		int leftSum=0;
		int allSumLeft=0;
		int maxLeftIndex=mid;
		for (int i = mid; i > -1; i--) {
			allSumLeft+=array[i];
			if (allSumLeft>leftSum) {
				leftSum=allSumLeft;
				maxLeftIndex=i;
			}
		}
		int rightSum=0;
		int allSumRight=0;
		int maxRightIndex=mid+1;
		for (int i = mid+1; i < high+1; i++) {
			allSumRight+=array[i];
			if (allSumRight>rightSum) {
				rightSum=allSumRight;
				maxRightIndex=i;
			}
		}
		indexes.append(maxLeftIndex);
		indexes.append(maxRightIndex);
		indexes.append(leftSum+rightSum);
		return indexes;
	}
	
	public static Tuple findMaximumSubArray(int[] array, int low, int high) {
		Tuple indexes=new Tuple(3);
		if (high==low) {
			indexes.append(low);
			indexes.append(high);
			indexes.append(array[low]);
			return indexes;
		}else {
			int mid=(low+high)/2;
			Tuple leftSub=findMaximumSubArray(array, low, mid);
			Tuple rightSub=findMaximumSubArray(array, mid+1, high);
			Tuple crossSub=findMaxCrossingSubArray(array, low, mid, high);
			int leftSum=(int) leftSub.getElement(2);
			int rightSum=(int) rightSub.getElement(2);
			int crossSum=(int) crossSub.getElement(2);
			if (leftSum>rightSum&&leftSum>crossSum) {
				return leftSub;
			}else if(rightSum>leftSum&&rightSum>crossSum) {
				return rightSub;
			}else {
				return crossSub;
			}
		}
	}
	
	public static Tuple optimizedFindMaximumSubArray(int optimizedPoint,int[] array, int low, int high) {
		Tuple indexes=new Tuple(3);
		if ((high-low+1)<optimizedPoint) {
			indexes=findBruteForce(array,low,high);
			return indexes;
		}else {
			int mid=(low+high)/2;
			Tuple leftSub=optimizedFindMaximumSubArray(optimizedPoint,array, low, mid);
			Tuple rightSub=optimizedFindMaximumSubArray(optimizedPoint,array, mid+1, high);
			Tuple crossSub=findMaxCrossingSubArray(array, low, mid, high);
			int leftSum=(int) leftSub.getElement(2);
			int rightSum=(int) rightSub.getElement(2);
			int crossSum=(int) crossSub.getElement(2);
			if (leftSum>rightSum&&leftSum>crossSum) {
				return leftSub;
			}else if(rightSum>leftSum&&rightSum>crossSum) {
				return rightSub;
			}else {
				return crossSub;
			}
		}
	}

}