import java.util.List;


public class Globals {
	//deklarirame publi4ni stati4ni matrici,  vurhu koito da rabotqt Thread-ovete
	public static volatile double[][] matrixA;
	public static volatile double[][] matrixB;
	public static volatile double[][] matrixC;
	
	public static boolean isQuiet;
	
	private static long startTime;//momenta v nanosekundi, koito zapo4va procesa
	private static long executionTime;//vremeto, za koeto e izvur6eno presmqtaneto
	
	public static double getExecutionTimeInSeconds(){
		return  (executionTime * (1.0e-9));
	}
	
	public static void setStartTime(){
		startTime = System.nanoTime();//gets current time in nanoseconds
	}
	
	public static void calculateExecutionTimeByNow(){
		long nowTime = System.nanoTime();
		executionTime = (nowTime - startTime);//execution time in nano seconds
	}
}
