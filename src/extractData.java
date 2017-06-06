import java.io.FileReader;
import java.io.BufferedReader;
public class extractData {
    //public static void main(String[] args) throws Exception
    public int[][] DataExtraction() throws Exception
    {
            FileReader file = new FileReader("/home/marino/workspace/RecommenderSystemFuzzyApproach/bin/u.data");
            BufferedReader reader = new BufferedReader(file);
            int[][] usersData=new int[944][1683];
            String line;
            while((line=reader.readLine())!=null)
            {
              String[] words = line.split("\\s+");
              usersData[Integer.parseInt(words[0])][Integer.parseInt(words[1])]=Integer.parseInt(words[2]);
              usersData[Integer.parseInt(words[0])][0]++;
            }
            reader.close();
            // user movie rating matrix 
           System.out.println("User rating of Movies:");
         /*   int i,j;
            for(i=0;i<944;i++)
        	{
        		for(j=0;j<1683;j++)
        			System.out.printf("%d\t",usersData[i][j]);
        			
        		System.out.printf("\n");
        	}*/
			return usersData;
    }
 }
