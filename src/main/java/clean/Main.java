package clean;

import clean.domain.*;
import clean.infrastructure.*;

public class Main {

	public static void main(String[] args) {
		
		/* domain layer */
		
		MyServiceImpl domain = new MyServiceImpl();
	
		/* infrastructure layer */
		
		/* repository adapter injected into domain */ 
		
	   	MyRepoAdapter myRepo = new MyRepoAdapter();
	   	myRepo.init();
	   	domain.injectRepository(myRepo);
	   
	   	/* a couple of controllers */
	   	
	   	/* web controller */
	   	
	   	MyWebController webController = new MyWebController(domain);	   	
	   	domain.addNotificationService(webController);
	   	webController.launch();

	   	/* GUI controller */
	   	
	   	MyGUIController guiController = new MyGUIController(domain);	   	
	   	domain.addNotificationService(guiController);
	   	guiController.launch();
	}   
}
