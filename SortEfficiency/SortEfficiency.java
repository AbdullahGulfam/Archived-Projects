//This program runs a small scale efficiency test on the selecion sort, insertion sort, and merge sort, under varying conditions. 
public class SortEfficiency
{
	public static void main(String[] args)
	{
		final int ARR_LENGTH = 100000;
		int[] arr = new int[ARR_LENGTH];
		int[] arrSel = new int[ARR_LENGTH];
		int[] arrIns = new int[ARR_LENGTH];
		int[] arrMer = new int[ARR_LENGTH];
		
		//Array Randomized
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] = (int)(Math.random() * 1001);
			arrMer[i] = arrIns[i] = arrSel[i] = arr[i];
		}
		//Selection Sort
		System.out.println("Data randomized.");	
		long start = System.currentTimeMillis();
		selectionSort(arrSel);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("Selection Sort: " + time);
		
		//Insertion Sort
		start = System.currentTimeMillis();
		insertionSort(arrIns);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Insertion Sort: " + time);
		
		//Merge Sort	
		start = System.currentTimeMillis();
		mergeSort(arrMer);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Merge Sort: " + time);
		
		/*-----------------------------------------------------*/
		//Array Sorted
		sort(arrSel);
		sort(arrIns);
		sort(arrMer);
		System.out.println();
	
		System.out.println("Data initially sorted.");
		//Selection Sort
		start = System.currentTimeMillis();
		selectionSort(arrSel);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Selection Sort: " + time);
		
		//Insertion Sort
		start = System.currentTimeMillis();
		insertionSort(arrIns);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Insertion Sort: " + time);
		
		//Merge Sort	
		start = System.currentTimeMillis();
		mergeSort(arrMer);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Merge Sort: " + time);
		
		/*-----------------------------------------------------*/
		//Array Sorted, Reverse 
		sortReverse(arrSel);
		sortReverse(arrIns);
		sortReverse(arrMer);
		System.out.println();
		
		System.out.println("Data initially sorted in reverse.");
		//Selection Sort
			
		start = System.currentTimeMillis();
		selectionSort(arrSel);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Selection Sort: " + time);
		
		//Insertion Sort
		start = System.currentTimeMillis();
		insertionSort(arrIns);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Insertion Sort: " + time);
		
		//Merge Sort	
		start = System.currentTimeMillis();
		mergeSort(arrMer);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Merge Sort: " + time);
		
		/*-----------------------------------------------------*/
		
		//First 'N' Selection Sorted
		int N = 5;
		System.out.println();
		System.out.println("First " + N + " numbers sorted.");
		
		start = System.currentTimeMillis();
		firstNSelection(arrSel, N);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Selection Sort: " + time);
	}
	
	private static void merge(int[] array1, int[] array2, int[] temp)
	{
		int current1 = 0;
		int current2 = 0;
		int current3 = 0;
		while (current1 < array1.length && current2 < array2.length)
   		{
       		if (array1[current1] < array2[current2])
        	{
        		temp[current3] = array1[current1];
        		current1++;
        	}
       		else
        	{
           		temp[current3] = array2[current2];
           		current2++;
        	}
        	current3++;
    	}
    	while (current1 < array1.length)
    	{
        	temp[current3] = array1[current1];
        	current1++;
        	current3++;
    	}
    	while (current2 < array2.length)
    	{
        	temp[current3] = array2[current2];
        	current2++;
        	current3++;
    	}
	}
	
	private static void mergeSort(int[] array)
	{
		if (array.length > 1)
		{
			int[] firstHalf = new int[array.length / 2];
			for (int i = 0; i < firstHalf.length; i++)
			{
				firstHalf[i] = array[i];
			}
			mergeSort(firstHalf);
			
			int[] secondHalf = new int[array.length - firstHalf.length];
			for (int i = array.length / 2; i < array.length; i++)
			{
				secondHalf[i - array.length / 2] = array[i];
			}
			mergeSort(secondHalf);
			merge(firstHalf, secondHalf, array);
		}
	}
	
	private static void selectionSort(int[] array)
	{
		for (int i = 0; i < array.length - 1; i++)
		{
			int minIndex = i;
			for(int j = i + 1; j < array.length; j++) 
			{
           	 	if (array[minIndex] > array[j]) 
            	{
               		minIndex = j;
            	}
			}
			swap(array,i,minIndex);
		}
	}
	
	private static void insertionSort(int[] array)
	{
		for (int i = 1; i < array.length; i++) 
		{	
            int numberToInsert = array[i];
            int compareIndex = i;
            while (compareIndex > 0 && array[compareIndex - 1] > numberToInsert) 
            {
                array[compareIndex] = array[compareIndex - 1];
                compareIndex--; 
            }
            array[compareIndex] = numberToInsert;
        }
	}
	
	public static void firstNSelection(int[] array, int n)
	{
		for(int i = 0; i < n; i++)
		{
			int minIndex = i;
			for(int j = i + 1; j < array.length; j++)
			{
				if(array[minIndex] > array[j])
				{
					minIndex = j;
				}
			}
			swap(array, i, minIndex);
		}
	}
	
	private static void sort(int[] array)
	{
		for (int i = 0; i < array.length - 1; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++)
			{
				if (compareTo(array[j],array[minIndex]) < 0)
				{
					minIndex = j;
				}	
			}
			swap(array,i,minIndex);
		}
	}
	
	private static void sortReverse(int[] array)
	{
		for (int i = 0; i < array.length - 1; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++)
			{
				if (compareTo(array[j],array[minIndex]) > 0)
				{
					minIndex = j;
				}	
			}
			swap(array,i,minIndex);
		}
	}
	
	public static void swap(int[] array, int num1, int num2)
	{
		int temp = array[num1];
        array[num1] = array[num2];
        array[num2] = temp;
		
	}
	
	private static int compareTo(int num1, int num2)
	{
		if(num1 > num2)
		{
			return 1;
		}
		return -1;
	}
}
