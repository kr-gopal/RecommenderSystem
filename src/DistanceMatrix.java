import java.util.List;


/* this class creates DistanceMatrix of Active Users using Fuzzy sets*/

public class DistanceMatrix {
	
	double [][] userModel;
	List<Integer> activeUsers;
	double GIM[];
	List<Integer> nonActiveUsers;
	int age[];
	
	
	public DistanceMatrix(double um[][],List<Integer> au,double gim[],int age[]) 
	{
		// TODO Auto-generated constructor stub
		
		this.userModel = um;
		this.activeUsers = au;
		this.GIM = gim;
		this.age = age;
		for(int i=0;i<943;i++)
		{
			if(!activeUsers.contains(i))
				nonActiveUsers.add(i);
		}
		
		
	}
	
	

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}



	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}



	public double[][] getDistanceMatrix()
	{	
		//result matrix : contains distance values
		double result[][] = new double[50][447];
		int index=0;
		for(int i=0;i<943;i++)
		{
			if(userModel[i][0]>=60 && activeUsers.contains(i))
			{
				
				for(int j=0;j<nonActiveUsers.size();j++)
					{
						if(userModel[nonActiveUsers.get(j)][0]>=60)
						{
							//compute distance function here
							
							double sum=0;
							
							//computing 21 genres
							for(int k =1;k<=108;k++)
							{
									sum+=(euclideanDistance(userModel[i], userModel[nonActiveUsers.get(j)], k, k+6) * Math.abs(GIM[i]-GIM[nonActiveUsers.get(j)]));
									k+=6;
							
							}
							
							//computing gender
							if(userModel[i][109]==userModel[nonActiveUsers.get(j)][109])
							{
								sum+=euclideanDistance(userModel[i], userModel[nonActiveUsers.get(j)], 109, 110);
							}
							
							//computing Age
							sum+=(euclideanDistance(userModel[i], userModel[nonActiveUsers.get(j)], 111, 113) * Math.abs(age[i]-age[nonActiveUsers.get(j)]));
							
							//computing Occupation
							sum+=euclideanDistance(userModel[i], userModel[nonActiveUsers.get(j)], 114, 134);
				
							result[index][j]=(sum/21);
							index++;
							
							
							
							
						}
					}
				
			}
		}
		
		return result;
	}
	
	public double euclideanDistance(double a[],double b[],int x,int y)
	{	double sum=0;
		for(int i=x;i<=y;i++)
		{
			sum+=((a[i]-b[i])*(a[i]-b[i]));
		}
		return Math.sqrt(sum);
			
	}
	
	

}
