import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class demographDetails{
    int[][] usersGenderData;
    double[][] usersAgeData;
    int[][] usersOccupatioData;
    String line;
    FileReader file ;
    BufferedReader reader;
    int [][]demographicData=new int[944][26];
    public int[][] demographDetail() throws Exception
    {
    	
        usersGenderData=new int[944][2];
        usersAgeData=new double[944][4];
        usersOccupatioData=new int[944][22];
        file = new FileReader("/home/marino/workspace/RecommenderSystemFuzzyApproach/bin/u.user");
        reader = new BufferedReader(file);
        fuzzifyDemographic(reader);
		return demographicData;
    }
    public void fuzzifyDemographic (BufferedReader reader) throws Exception
    {	
        while((line=reader.readLine())!=null)
        {
          String[] words = line.split("\\s+");
         
          //System.out.println("word0"+words[0]+"word1"+words[1]+"word2"+words[2]+"word3"+words[3]);
          fuzzifyGender(words[2],words[0]);
          fuzzifyAge(words[1],words[0]);
          fuzzifyOccupation(words[3],words[0]);
          
         
        }
        /*for(int i=0;i<944;i++)
        {
        	for(int j=0;j<26;j++)
        	{
        		if(j<2)
        		{
        			demographicData[i][j]=(int) usersGenderData[i][j];
        		}
        		else if(j>=2 && j<5)
        		{
        			demographicData[i][j]=(int)usersAgeData[i][j-1];
        		}
        		else
        		{
        			demographicData[i][j]=(int) usersOccupatioData[i][j-4];
        		}
        	}
        }*/
        System.out.println("User demographic profile");
        for(int i=0;i<944;i++)
        {
        	for(int j=0;j<26;j++)
        	{
        		System.out.print(demographicData[i][j]+" ");
        	}
            System.out.println();
        }
    }
	public void fuzzifyGender(String gen,String id)
    {
		
      if(gen=="M")
      {
          //usersGenderData[Integer.parseInt(id)][0]=1;
          demographicData[Integer.parseInt(id)][0]=1;
          //usersGenderData[Integer.parseInt(id)][1]=0;
          demographicData[Integer.parseInt(id)][1]=0;
      }
      else
      {
        usersGenderData[Integer.parseInt(id)][0]=0;
        usersGenderData[Integer.parseInt(id)][1]=1;
        demographicData[Integer.parseInt(id)][0]=0;
        demographicData[Integer.parseInt(id)][1]=1;
      }
      
    }
    public void fuzzifyAge(String aged,String id)
    {
      int age=Integer.parseInt(aged);
      //fuzzy age bit 1
      if(age<=20)
      {
          usersAgeData[Integer.parseInt(id)][1]=1;
          demographicData[Integer.parseInt(id)][2]=1;
          
      }
      else if(age>20 && age<=35)
      {
        usersAgeData[Integer.parseInt(id)][1]=(35-age)/15;
        demographicData[Integer.parseInt(id)][2]=(35-age)/15;
      }
      else
      {
        usersAgeData[Integer.parseInt(id)][1]=0;
        demographicData[Integer.parseInt(id)][2]=0;
      }
      //fuzzy age bit 2
      if(age<=20 || age>60)
      {
          usersAgeData[Integer.parseInt(id)][2]=(age-20)/15;
        demographicData[Integer.parseInt(id)][3]=(age-20)/15;
      }
      else if(age>20 && age<=35)
      {
        usersAgeData[Integer.parseInt(id)][2]=1;
        demographicData[Integer.parseInt(id)][3]=1;
      }
      else
      {
        usersAgeData[Integer.parseInt(id)][2]=(60-age)/15;
        demographicData[Integer.parseInt(id)][3]=(60-age)/15;
      }
      //fuzzy age bit 3
      if(age<=45)
      {
          usersAgeData[Integer.parseInt(id)][3]=0;
          demographicData[Integer.parseInt(id)][4]=0;
      }
      else if(age>45 && age<=60)
      {
        usersAgeData[Integer.parseInt(id)][3]=(age-45)/15;
        demographicData[Integer.parseInt(id)][4]=(age-45)/15;
      }
      else
      {
        usersAgeData[Integer.parseInt(id)][3]=1;
        demographicData[Integer.parseInt(id)][4]=1;
      }
    }
    public void  fuzzifyOccupation(String occ,String id)
    {
     
      for(int i=1;i<22;i++)
      {
    	  usersOccupatioData[Integer.parseInt(id)][i]=0;
      }
      if(occ=="administrator")
      {
        usersOccupatioData[Integer.parseInt(id)][1]=1;
        demographicData[Integer.parseInt(id)][5]=1;
      }
      else if(occ=="artist")
      {
        usersOccupatioData[Integer.parseInt(id)][2]=1;
        demographicData[Integer.parseInt(id)][6]=1;
      }
      else if(occ=="doctor")
      {
        usersOccupatioData[Integer.parseInt(id)][3]=1;
        demographicData[Integer.parseInt(id)][7]=1;
      }
      else if(occ=="educator")
      {
        usersOccupatioData[Integer.parseInt(id)][4]=1;
        demographicData[Integer.parseInt(id)][8]=1;
      }
      else if(occ=="engineer")
      {
        usersOccupatioData[Integer.parseInt(id)][5]=1;
        demographicData[Integer.parseInt(id)][9]=1;
      }
      else if(occ=="entertainment")
      {
        usersOccupatioData[Integer.parseInt(id)][6]=1;
        demographicData[Integer.parseInt(id)][10]=1;
      }
      else if(occ=="executive")
      {
        usersOccupatioData[Integer.parseInt(id)][7]=1;
        demographicData[Integer.parseInt(id)][11]=1;
      }
      else if(occ=="healthcare")
      {
        usersOccupatioData[Integer.parseInt(id)][8]=1;
        demographicData[Integer.parseInt(id)][12]=1;
      }
      else if(occ=="homemaker")
      {
        usersOccupatioData[Integer.parseInt(id)][9]=1;
        demographicData[Integer.parseInt(id)][13]=1;
      }
      else if(occ=="lawyer")
      {
        usersOccupatioData[Integer.parseInt(id)][10]=1;
        demographicData[Integer.parseInt(id)][14]=1;
      }
      else if(occ=="librarian")
      {
        usersOccupatioData[Integer.parseInt(id)][11]=1;
        demographicData[Integer.parseInt(id)][15]=1;
      }
      else if(occ=="marketing")
      {
        usersOccupatioData[Integer.parseInt(id)][12]=1;
        demographicData[Integer.parseInt(id)][16]=1;
      }
      else if(occ=="none")
      {
        usersOccupatioData[Integer.parseInt(id)][13]=1;
        demographicData[Integer.parseInt(id)][17]=1;
      }
      else if(occ=="other")
      {
        usersOccupatioData[Integer.parseInt(id)][14]=1;
        demographicData[Integer.parseInt(id)][18]=1;
      }
      else if(occ=="programmer")
      {
        usersOccupatioData[Integer.parseInt(id)][15]=1;
        demographicData[Integer.parseInt(id)][19]=1;
      }
      else if(occ=="retired")
      {
        usersOccupatioData[Integer.parseInt(id)][16]=1;
        demographicData[Integer.parseInt(id)][20]=1;
      }
      else if(occ=="salesman")
      {
        usersOccupatioData[Integer.parseInt(id)][17]=1;
        demographicData[Integer.parseInt(id)][21]=1;
      }
      else if(occ=="scientist")
      {
        usersOccupatioData[Integer.parseInt(id)][18]=1;
        demographicData[Integer.parseInt(id)][22]=1;
      }
      else if(occ=="student")
      {
        usersOccupatioData[Integer.parseInt(id)][19]=1;
        demographicData[Integer.parseInt(id)][23]=1;
      }
      else if(occ=="technician")
      {
        usersOccupatioData[Integer.parseInt(id)][20]=1;
        demographicData[Integer.parseInt(id)][24]=1;
      }
      else if(occ=="writer")
      {
        usersOccupatioData[Integer.parseInt(id)][21]=1;
        demographicData[Integer.parseInt(id)][25]=1;
      }
    }
	
    
 }
