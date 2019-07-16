package fr.inria.lille.shexjava.shapeMap.abstrsynt;

import java.util.Optional;

import fr.inria.lille.shexjava.validation.Status;

public class ShapeAssociation {
	protected NodeSelector nodeSelector;
	protected ShapeSelector shapeSelector;
	protected Status status;
	protected Object reason;
	protected Object appInfo;

		
	public ShapeAssociation(NodeSelector nodeSelector, ShapeSelector shapeSelector) {
		super();
		this.nodeSelector = nodeSelector;
		this.shapeSelector = shapeSelector;
		status = null;
		reason = null;
		appInfo = null;
	}
	
	
	public NodeSelector getNodeSelector() {
		return nodeSelector;
	}


	public ShapeSelector getShapeSelector() {
		return shapeSelector;
	}
	
	
	public Optional<Status> getStatus(){
		return Optional.of(status) ;
	}
	
	
	public Optional<Object> getReason(){
		return Optional.of(reason) ;
	}
	
	
	public Optional<Object> getAppInfo(){
		return Optional.of(appInfo) ;
	}
	
	
}
