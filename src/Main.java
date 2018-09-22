import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;



public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{	
		long startTime = System.nanoTime(); // for calculate the execution time program holds starting time.
		int control= 0; // control variable for given feature index. 
		int saveFile = 0; //control variable for if sorted date saved or not.
		ArrayList<ArrayList<String>> words= new ArrayList<ArrayList<String>>(); // an array list that contains lines.
		
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) // reading first command line argument
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	ArrayList<String> temp = new ArrayList<String>();// an array list for hold splitted tokens.
                String [] tokens =line.split(","); // splitting by comma
                for (int i = 0; i < tokens.length; i++) 
                {
					temp.add(tokens[i]);// adding tokens to an arraylist.
				}
                words.add(temp); // adding tokens arraylist to an main arraylist
            }
		    br.close();
		}
		
		int featureIndex = Integer.parseInt(args[1]);
		if (featureIndex >= 8 && featureIndex <= 84) // feature index must be in the that interval.
		{
			control = 1;
			featureIndex -= 1; /* i did that for easy computution 
								* for example 8th element in the dataset is 7th element the array.
								*/
		}
		else
		{
			System.out.println("Given feature index is not in the range."); // error massege for wrong feature index.
			return;
			
		}
		if (args[2].equals("T")) // T for save the sorted data. F for don't save it.
		{
			saveFile = 1;
		}
		
		if(control== 1) // in this part all the methods are ready to call.
		{
			//bubleSort(words, words.size(), featureIndex);
			//insertionSort(words, words.size(), featureIndex);
			quickSort(words, 1, words.size() -1 , featureIndex);
			
		}
		if(saveFile == 1) // if user wants to save the data 
		{
			PrintWriter out = new PrintWriter(new FileWriter(args[0])); // i cleared the input file and set the file to a writing 
			for (int i = 0; i < words.size(); i++) 
			{
				for (int j = 0; j < words.get(i).size(); j++) 
				{
					if(j != words.get(i).size()-1 )
					{
						out.print(words.get(i).get(j) + ",");
					}
					else
						out.print(words.get(i).get(j));
					
				}
				out.println();
			}
			out.close();
		}
		
		long endTime = System.nanoTime();// program end time
		long result = endTime - startTime;
		System.out.println("Took "+(result) + " Nano Seconds"); // calculates the compiling time. 		
	}
	
	public static void bubleSort(ArrayList<ArrayList<String>> words, int size, int featureIndex)
	/* parameters are:
	 * First parameter is my main arrayList.
	 * Second one is size of this arrayList.
	 * Third one is feature index by given by user.
	 */
	{
		int i, j;
		ArrayList<String> temp = new ArrayList<String>();
		for(i=1; i<(size-1); i++)
		{
			for(j=1; j<(size-1-i); j++)// iteration starts with 1 because first line of input file is not used.
			{
				if(Double.valueOf(words.get(j).get(featureIndex))> Double.valueOf(words.get(j+1).get(featureIndex)))
					/*
					 * comparing the element ,which is held in current line's feature index, 
					 * by next line's feature index
					 */
				{
					/*
					 * Swap operation.
					 */
					temp= words.get(j);
					words.set(j, words.get(j+1));
					words.set(j+1, temp) ;
				}
			}
		}
	}
	
	public static void insertionSort(ArrayList<ArrayList<String>> words, int size, int featureIndex)
	{/* parameters are:
		 * First parameter is my main arrayList.
		 * Second one is size of this arrayList.
		 * Third one is feature index by given by user.
		 */
		int i, j;
		double key;
		for(i = 2; i < size; i++)// iteration starts with 2 because first line of input file is not used.
		{
			j = i - 1;
			key = Double.valueOf(words.get(i).get(featureIndex)); 
			
			while(j >= 1 && (Double.valueOf(words.get(j).get(featureIndex)) > key))
			{
				/*
				 * swap operation run if inside of while is true.
				 */
				words.get(j+1).set(featureIndex, words.get(j).get(featureIndex));
				j =  j - 1;
			}
			words.get(j+1).set(featureIndex, String.valueOf(key)); // key is assigned to given index.			
		}
		
	}
	public static int partition(ArrayList<ArrayList<String>> words, int low, int high, int featureIndex)
	{
		/* parameters are:
		 * First parameter is my main arrayList.
		 * Second one is beginning index.
		 * Third one is ending index
		 * fourth one is feature index by given by user.
		 */
		ArrayList<String> temp = new ArrayList<String>();// allocating new memory
		ArrayList<String> temp2 = new ArrayList<String>();// allocating new memory
		double pivot = Double.valueOf(words.get(high).get(featureIndex)); // determining pivot value. 
        int i = (low-1); 
        for (int j=low; j<high; j++)
        {
            if (Double.valueOf(words.get(j).get(featureIndex))<= pivot)
            {
            	/*
            	 * if current data is smaller than pivot swap the datas.
            	 */
                i++;
                temp = words.get(i);
                words.set(i, words.get(j));
                words.set(j, temp) ;
            }
        }        
        temp2 = words.get(i+1);
        words.set(i+1, words.get(high));
        words.set(high, temp2);
 
        return i+1;
	}
	
	public static void quickSort(ArrayList<ArrayList<String>> words, int low, int high, int featureIndex)
	{
		/* parameters are:
		 * First parameter is my main arrayList.
		 * Second one is beginning index.
		 * Third one is ending index
		 * fourth one is feature index by given by user.
		 */

		if (low < high)
        {
            int result = partition(words, low, high, featureIndex);
            // calling recursive.
            quickSort(words, low, result-1, featureIndex);
            quickSort(words, result+1, high, featureIndex);
        }		
	}	
	
	
}
