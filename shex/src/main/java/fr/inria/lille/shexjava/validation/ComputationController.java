package fr.inria.lille.shexjava.validation;

public interface ComputationController {
	public void start() ;	
	public void stop() ;	
	public void canContinue() throws Exception;
}
