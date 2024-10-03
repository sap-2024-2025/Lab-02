package mvc.model;

/**
 * 
 * Interface exposed by model's observers (e.g. the View)
 * 
 * 
 */
public interface ModelObserver {

	void attachToModel(ModelObserverSource source);

	void notifyModelUpdated();
}
