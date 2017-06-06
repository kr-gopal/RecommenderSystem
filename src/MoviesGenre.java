import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
/**
	this code generates a matrix for movies and their genres
	
*/
public class MoviesGenre {
//public static void main(String args[]) throws IOException
//{
	public int[][] movieMatrix() throws IOException
	{

	FileReader file = new FileReader("/home/marino/workspace/RecommenderSystemFuzzyApproach/bin/u.item");
	BufferedReader br = new BufferedReader(file);
	int j=0;
	int [][] moviesMatrix = new int [1682][19];
	String line;
	while((line=br.readLine())!=null)
	{	
		String genres = line.substring(line.length()-38,line.length());
		int k=0;
		for(int i=1;i<genres.length();i=i+2)
		{
				//System.out.printf(genres.charAt(i) + " ");
				if(genres.charAt(i)=='1')
					moviesMatrix[j][k] = 1;
				else
					moviesMatrix[j][k] = 0;
			k++;
			
		}
		
		j++;
	}
	br.close();
	/*FileWriter wfile = new FileWriter("/home/marino/workspace/RecommenderSystemFuzzyApproach/bin/MovieMatrix");
	BufferedWriter bw = new BufferedWriter(wfile);
	System.out.println("Matrix is : ");
	for(int i=0;i<1682;i++)
	{
		for(j=0;j<19;j++)
			System.out.printf("%d	",moviesMatrix[i][j]);
		System.out.printf("\n");
	}
			*/
	return moviesMatrix;
}

}