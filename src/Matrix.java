import java.io.*;
import java.text.Format;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
public class Matrix {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//po podrazbirane slagame Quiet=true, ako ne go vuvedem ot vhodnite parmeetri v AGRS[]
		Globals.isQuiet = true;
		int m = 0;
		int n = 0;
		int k = 0;
		int maxThreadCount = 0;
		//proverka za vhodni parametri ot consolata- i 4te tqbva da sa vuvedeni
		if (args.length >= 4) {
			m =	Integer.parseInt(args[0]);
			n = Integer.parseInt(args[1]);
			k = Integer.parseInt(args[2]);
			maxThreadCount = Integer.parseInt(args[3]);
			if(args.length>=5){
				Globals.isQuiet = Boolean.parseBoolean(args[4]);
			}
			
			System.out.println("m n k maxThreadCount isQuiet(false/true)");
			System.out.println(String.format("%s %s %s %s %s", m, n, k, maxThreadCount, Globals.isQuiet));
			
		}
		else{
			Scanner s = new Scanner(System.in);
			System.out.println("m n k maxThreadCount isQuiet(false/true)");
			m = s.nextInt();
			n = s.nextInt();
			k = s.nextInt();
			maxThreadCount = s.nextInt();
			Globals.isQuiet = s.nextBoolean();
		}
		//vuvejdane na dannite za razmernostta

		
		//suzdavane na matricite i populvaneto im s danni
		Globals.matrixA = new double[m][n];
		Globals.matrixB = new double[n][k];
		Globals.matrixC = new double[m][k];

		
		if(!Globals.isQuiet)
		{
			System.out.println("Inicializirane na matricite A i B");
		}
		//populvane na matricite s danni
		initMatrix(Globals.matrixA);
		initMatrix(Globals.matrixB);

		if(!Globals.isQuiet)
		{
			System.out.println("printirane na matricite A i B");
			System.out.println("Matrix A:");
			printMatrix(Globals.matrixA);
			System.out.println("Matrix B:");
			printMatrix(Globals.matrixB);
		}
		
		System.out.println(String.format(" --------------------------------"));
		System.out.println(String.format("|%12s | %15s  |","thread count","total time(sec)"));
		System.out.println(String.format(" --------------------------------"));
		for(int threadCount=1;threadCount<=maxThreadCount;threadCount++)
		{
			executeMatrixCalculations(threadCount);
		}	
		System.out.println(String.format(" --------------------------------"));
		if(!Globals.isQuiet)
		{
			System.out.println("printirane na matricata C");
			System.out.println("Matrix C:");
			printMatrix(Globals.matrixC);
		}
	}

	public static void executeMatrixCalculations(int threadCount)
			throws InterruptedException {
		
		if(!Globals.isQuiet)
		{
			System.out.println("**** Izpulnenie na test s "+threadCount+" nishki ****");
		}
		
		int n = Globals.matrixA[0].length;//kolonite v A=redovete v B
		//suzdavame spisuk, koito da durji matricite
		LinkedList<MatrixMultiplierThread> threadList = new LinkedList<MatrixMultiplierThread>();
		
		int elementsCountInC = Globals.matrixC.length * Globals.matrixC[0].length;//broq elementi v c
		//elementsCountInC=elemInterval*(threadCounts-1)+(elemInterval+elemIntervalRest)
		int elemInterval = elementsCountInC/threadCount;//interval ot elementi, koito 1 ni6ka 6te iz4islqva
		int elemIntervalRest = elementsCountInC%threadCount;//posledniq interval za iz4islenie
		
		if(!Globals.isQuiet)
		{
			System.out.println("Razglejdame matricata C kato ednomeren masiv s MxK = "+elementsCountInC+" elemnta");
			System.out.println("Interval ot elementi, koito 1 ni6ka 6te iz4islqva : "+elemInterval+" elemnta ot C");
		}

		int elemFrom;//ot koi element v ednomerniq masiv, otgovarq6t na C 
		int elemTo;//do koi element v ednomerniq masiv, otgovarq6t na C
		
		if(!Globals.isQuiet)
		{
			System.out.println();
			System.out.println("Suzdavane na nishkite:");
		}
		for(int i=0;i<threadCount;i++){
			elemFrom = i*elemInterval;
			elemTo = elemFrom + elemInterval-1;
			
			if (i==(n-1))
				elemTo+=elemIntervalRest-1;
			
			MatrixMultiplierThread t = new MatrixMultiplierThread("Thread "+i, elemFrom, elemTo);
			threadList.add(t);
		}
		
		Globals.setStartTime();//setvame na4alnoto vreme
		//Puskame nishkite
		for(MatrixMultiplierThread t : threadList){
			t.start();
		}
		//iz4akvame ni6kite da svur6at
		for(MatrixMultiplierThread t : threadList){
			t.join();//4aka nishkata da svur6i i togava produlvawa rabota
		}
		Globals.calculateExecutionTimeByNow();//setvame krainoto vreme
		System.out.println(String.format("|%12s | %15f  |",threadCount,Globals.getExecutionTimeInSeconds()));
		//System.out.println(StringthreadCount+" - total execution time: "+Globals.getExecutionTimeInSeconds()+" sec");
	}
	
	/***
	 * Inicializira matrica sus slu4aini veli4ini
	 * @param matrix
	 * 
	 */
	public static void initMatrix(double[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = Math.random()*10000;
			}
		}
	}
	
	/***
	 * Printira elementite na matrica
	 * @param matrix
	 */
	public static void printMatrix(double[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				System.out.print(matrix[i][j]+"  ");
			}
			System.out.println();
		}		
	}

}

