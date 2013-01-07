/***
 * Klasa e naslednik na klasa Thread, koeto ozna4ava, 4e vsi4ko koeto se pravi v
 * metoda Run se otdelq v otdelna ni6ka
 *
 */
public class MatrixMultiplierThread extends Thread{

  	private String threadName;
  	
  	//elemFrom i elemTo imat vida: [teku6tiq red]*[kolonite] + [teku6tata kolona]
	private int elemFrom;//ot koi element vuv virtualniq ednomeren masiv zapo4va umnojenieto
	private int elemTo;//do koi element vuv virtualniq ednomeren masiv e umnojenieto

	
	private int rows;//redovete v matricata A = redovete v matricata C
	private int n;//kolonite v A = redovete v B 
	private int columns;//kolonite v B = kolonite v C
	
	private double executionTime;
	
	/***
	 * Vru6ta vremeto za izpulnenieto na teku6tata ni6ka
	 * @return
	 */
	public double getExecutionTime() {
		return executionTime;
	}


	public MatrixMultiplierThread()
	{
	}
	/**
	 * Konstruktor
	 * 
	 * @param matrixA - Mtricata A, koqto se umnojava
	 * @param matrixB - Matricata B, koqto se umnojava po A
	 * @param elemFrom - ot koi element zapo4va da umnojava
	 * @param elemTo - do koi element , koito da umnojava
	 */
	public MatrixMultiplierThread(String threadName, int elemFrom, int elemTo ){
		
		this.threadName = threadName;
		
		this.elemFrom = elemFrom;
		this.elemTo = elemTo;

		//raboti se s globalnite matrici v Globals 
		this.rows = Globals.matrixA.length;
		this.n = Globals.matrixA[0].length;
		this.columns = Globals.matrixB[0].length;
		
		//System.out.println("m="+m);
		//System.out.println("n="+n);
		//System.out.println("k="+k);
		if(!Globals.isQuiet)
		{
			System.out.println(this.threadName+" e suzdadena - elemnti ot "+elemFrom+" do "+elemTo);
		}
	}
	
	/***
	 * Operaciite v tozi metod se startirat v nova ni6ka
	 */
	public void run(){
		
		long start = 0;
		if(!Globals.isQuiet)
		{
			start = System.nanoTime();//nachalnoto vreme
		}
		//System.out.println("elem from to - "+elemFrom+" "+elemTo);
		for(int i = elemFrom; i <= elemTo; i++){
			int row = i/columns;//vru6ta teku6tiq red t.k i = [teku6tiq red]*[kolonite] + [teku6tata kolona] 
			int column = i%columns;//vru6ta teku6tata kolona
			for(int w = 0; w < n ; w++){
				//sumirane na umnojenite red po kolona i natrupvane vuv suotvetniq element
				Globals.matrixC[row][column] += Globals.matrixA[row][w] * Globals.matrixB[w][column]; 
			}
			if(!Globals.isQuiet)
			{
				System.out.println("C["+row+","+column+"]="+Globals.matrixC[row][column]);
			}
		}	
		if(!Globals.isQuiet)
		{
			executionTime = (System.nanoTime() - start) * 1.0e-9;//execution time in Seconds
			System.out.println(this.threadName+" - "+executionTime+" sec");
		}
		
		
	}	
	

}
