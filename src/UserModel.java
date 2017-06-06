

import java.util.ArrayList;
import java.util.Random;

public class UserModel {
	/*USERS and GENRES are count+1 because indexing from 1 is considered*/
	private static final int USERS = 944; 
	private static final int MOVIES = 1683; 
	private static final int GENRES = 19; 
	private static final int USER_MODEL_FEATURES = 135;//6*18=108 + 2 + 3 + 21 = 134
	private static final int FOLD_LENGTH = 50; 
	
	//given data
	int data[][];//user ratings
	int movie[][];//movie genre information
	int user[][];//users demographic information
	
	//to be returned
	double usersModel[][];//final usersModel
	ArrayList<Integer> activeUsers;
	int activeUsersTrainingMovies[][];
	
	//temporary storage
	int genreRating[][];//RGR array
	int modifiedGenreFrequency[][];//MRGF array
	double GIMArray[][];//normalised GIM 
	
	//random no generator
	Random randomGenerator;
	
	/*User Model constructor which initializes the above data structures*/
	public void UseModel(int[][] data, int[][] movie, int[][] user)
	{
		this.data = data;
		this.movie = movie;
		this.user = user;
		
		usersModel = new double[USERS][USER_MODEL_FEATURES]; 
		activeUsers = new ArrayList<Integer>();
		activeUsersTrainingMovies = new int[USERS][MOVIES];
		
		genreRating = new int[USERS][GENRES];
		modifiedGenreFrequency = new int[USERS][GENRES];
		GIMArray = new double[USERS][GENRES];
		
		randomGenerator = new Random();
	}
	
	/*Initiates the User Model creation:
	  *make User Model for Active Users, who are selected randomly and their size is the FOLD_LENGTH
	  *make User Model for Normal Users, who are the remaining users and their size is Valid Users - FOLD_LENGTH
	  *Fuzzify the GIM for all users and store in the final USERS MODEL
	  *copy the demographic details of all USERS into the final USERS MODEL
	  *Fuzzify Age of all the USERS in the USERS MODEL*/
	public void createUsersModel(){
		createActiveUsersModel(FOLD_LENGTH);
		createNormalUsersModel();
		createFuzzyUserModel();
		
	}
	
	/*Creates active users model */
	public void createActiveUsersModel(int foldLength){
		int activeUsersCount=0;
		int movieCount=0,trainingMovieCount,testMovieCount;
		int randomMovie, randomUser, randomMovieRating;
		int movieRatedCount;
		int totalValidRatingForGIM=0;
	    
		//select FOLD_LENGTH active users randomly for a fold
		while(activeUsersCount != foldLength)
		{
			//reinitialize local variables for every new user
			movieCount = 0;
			totalValidRatingForGIM = 0;
			
			randomUser = randomGenerator.nextInt(USERS-1) + 1;
			if((movieRatedCount = data[randomUser][0]) >= 60)//valid user, has rated at least 60 movies
			{
				activeUsersCount++;
				activeUsers.add(activeUsersCount, randomUser);
				activeUsersTrainingMovies[randomUser][0] = 1;
				
				//trainingMovies:testMovies :: 4:6 
				trainingMovieCount = (int) (0.4 * movieRatedCount);
				testMovieCount = movieRatedCount - trainingMovieCount;
				
				while(movieCount != trainingMovieCount)
				{
					randomMovie = randomGenerator.nextInt(MOVIES-1) + 1; 
					if((randomMovieRating = data[randomUser][randomMovie]) > 0)//movie has been rated if rating>0
					{
						movieCount++;
						activeUsersTrainingMovies[randomUser][randomMovie] = 1;
						totalValidRatingForGIM += randomMovieRating;//TR for calculating RGR
						
						if(randomMovieRating >= 3) //user model construction using high rated movies (rating>=3)
						{
							for(int i=1; i<19; i++)
							{
								if(movie[randomMovie][i] == 1) //if a movie belongs to genre i then 1 else 0
								{								
									genreRating[randomUser][i] += randomMovieRating; // calculating GR
									if(randomMovieRating==3) // calculating modified genre frequency
										modifiedGenreFrequency[randomUser][i] += randomMovieRating;
									else if(randomMovieRating==4)
										modifiedGenreFrequency[randomUser][i] +=2*randomMovieRating;
									else
										modifiedGenreFrequency[randomUser][i] += 3*randomMovieRating;
									//modifiedGenreFrequency[randomUser][i] += randomMovieRating - 2; //according to MRGF 
								}
							}
						}
						
					}
					else //movie rating was 0
						continue;					
				}
				//normalize the rating and frequency of genres
				for(int i=1; i<19; i++)
				{
					    genreRating[randomUser][i] /= totalValidRatingForGIM;  //calculating RGR
						modifiedGenreFrequency[randomUser][i] /= (movieRatedCount*3); // calculating MGRF
				}
				//calculate GIM using the given formula, taking MAX= 1/5
				for(int i=1; i<19; i++)
				{
					GIMArray[randomUser][i] = 2*(0.2) * genreRating[randomUser][i] * modifiedGenreFrequency[randomUser][i];
					GIMArray[randomUser][i] /= genreRating[randomUser][i] + modifiedGenreFrequency[randomUser][i];	
				}
			}
			else //user hasn't rated at least 60 movies
				continue;
		}
	}
	
	/*Creates normal(non active) users model 
	 *NOTE: should be called after createActiveUsersModel() has been called, since it reads activeUsers list*/
	public void createNormalUsersModel(){
		int movieId, userId, movieRating;
		int movieRatedCount;
		int totalValidRatingForGIM=0;
	    
		//compute GIM for all users other than active users
		for(userId=1; userId<USERS; userId++){
			//reinitialize local variables for every new user
			totalValidRatingForGIM = 0;
			
			if((movieRatedCount = data[userId][0]) >= 60)//valid user, has rated at least 60 movies			
			{
				for(movieId=1; movieId<MOVIES; movieId++)
					{
						if((movieRating = data[userId][movieId]) > 0)//movie has been rated if rating>0
						{
							totalValidRatingForGIM += movieRating;//TR for calculating RGR
						
						if(movieRating >= 3) //user model construction using high rated movies (rating>=3)
						{
							for(int i=1; i<19; i++)
							{
								if(movie[movieId][i] == 1)//if a movie belongs to genre i then 1 else 0
								{								
									genreRating[userId][i] += movieRating;
									if(movieRating==3)
										modifiedGenreFrequency[userId][i] += movieRating;
									else if(movieRating==4)
										modifiedGenreFrequency[userId][i] +=2*movieRating;
									else
										modifiedGenreFrequency[userId][i] += 3*movieRating;
									//modifiedGenreFrequency[userId][i] += movieRating - 2; //according to MRGF 
								}
							}
						}
						
					}
					else //movie rating was 0
						continue;					
				}
				//normalize the rating and frequency of genres
				for(int i=1; i<19; i++)
				{
					    genreRating[userId][i] /= totalValidRatingForGIM;
						modifiedGenreFrequency[userId][i] /= (movieRatedCount*3); 
				}
				//calculate GIM using the given formula, taking MAX= 1/5
				for(int i=1; i<19; i++)
				{
					GIMArray[userId][i] = (2*0.2) * genreRating[userId][i] * modifiedGenreFrequency[userId][i];
					GIMArray[userId][i] /= genreRating[userId][i] + modifiedGenreFrequency[userId][i];	
				}
			}
			else //user hasn't rated at least 60 movies
				continue;
		}
	}
	
	public void createFuzzyUserModel(){
		//fuzzifyGIM for each Genre
		int userId = 1;
		for(userId=1; userId< USERS; userId++)
		{
			if(data[userId][0] >= 60) //check if a valid user, i.e. rated >=60 movies
			{
				for(int i=1; i<19; i++)
				{
					double gimValue = GIMArray[userId][i];
					fuzzifyGIM(userId, i, gimValue);
				}
			}
			else 
				continue;
		}
	}
	
	public void fuzzifyGIM(int userId, int genrePosition, double gimValue )
	{
		int veryBadColumn  = 1 + (genrePosition-1) * 6;
		
		//for Very Bad Column
		if(gimValue <= 1)
			usersModel[userId][veryBadColumn] = 1-gimValue;
		else if(gimValue > 1)
			usersModel[userId][veryBadColumn] = 0;
		
		//for Bad, Average, Good, Very Good Columns
		for(int i=2; i<=5; i++)
		{
			if(gimValue <= (i-2) || gimValue > i)
				usersModel[userId][veryBadColumn + i-1] = 0;
			else if((gimValue > (i-2)) && (gimValue <= (i-1)))
				usersModel[userId][veryBadColumn + i-1] = gimValue - i+2;
			else if((gimValue > (i-1)) && (gimValue <= i))
				usersModel[userId][veryBadColumn + i-1] = i - gimValue;
		}
		
		//for Excellent column
		if(gimValue <= 4)
			usersModel[userId][veryBadColumn + 5] = 0;
		else if((gimValue > 4) && (gimValue <= 5))
			usersModel[userId][veryBadColumn + 5] = gimValue - 4;		
	}
	
	/*Returns the Created USERS MODEL
	 *NOTE: must be called after createUsersModel(), else will return a null 2-d array */
	public double[][] getUsersModel(){
		return usersModel;
	}
	
	/*Returns the ACTIVE USERS userId list for the given FOLD
	 *NOTE: must be called after createUsersModel(), else will return a null list */
	public ArrayList<Integer> getActiveUsers(){
		return activeUsers;
	}
	
	/*Returns an USERS*MOVIES array where 0th column represents active users (value=1),
	 *rest columns represents whether a movie is in training set (value = 1) or not (value = 0)*/
	public int[][] getActiveUsersTrainingMovies(){
		return activeUsersTrainingMovies;
	}
}
