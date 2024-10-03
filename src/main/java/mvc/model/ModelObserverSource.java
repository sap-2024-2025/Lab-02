package mvc.model;

/**
 * 
 * Interface exposed by the model as observed source
 * 
 */
public interface ModelObserverSource extends ModelInterface {

	void addObserver(ModelObserver obs);

}
