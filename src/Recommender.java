import java.io.IOException;


public class Recommender {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		int [][] moviesMatrix = new int [1682][19];
		int[][] usersData=new int[944][1683];
		int [][]demographicData=new int[944][26];
		
		
		MoviesGenre MG=new MoviesGenre();
		moviesMatrix=MG.movieMatrix();
		
		extractData ED=new extractData();
		usersData=ED.DataExtraction();
		
		demographDetails DGD=new demographDetails();
		demographicData= DGD.demographDetail();
		
		UserModel UM=new UserModel();
		UM.UseModel(usersData, moviesMatrix, demographicData );
		UM.createUsersModel();
	}

}
