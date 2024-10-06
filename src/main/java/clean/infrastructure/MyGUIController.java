package clean.infrastructure;

import java.util.logging.Level;
import java.util.logging.Logger;
import clean.domain.MyServiceInterface;
import clean.domain.NotificationInterface;

public class MyGUIController implements NotificationInterface {

	private MyGUIView view;
	private MyServiceInterface domainLayer;
    static Logger logger = Logger.getLogger("[MyGUIController]");	    
    
	public MyGUIController(MyServiceInterface domainLayer) {
		this.domainLayer = domainLayer;
		logger.setLevel(Level.INFO);
		view = new MyGUIView(this);
	}

	public void launch() {
		view.reset();
		view.display();
  	}
	
	public void notifyUpdateRequest() {
		logger.log(Level.INFO, "New request - use case cmd ");
		domainLayer.doMyUseCaseCmd();
	}
	
	public void notifyQueryRequest() {
		logger.log(Level.INFO, "New request - query ");
		int res = domainLayer.doMyUseCaseQuery();
		view.updateValue(res);
	}

	public void notifyCountChanged(int newCount) {
		logger.log(Level.INFO, "notify count changed");
		view.updateValue(newCount);
	}
}
