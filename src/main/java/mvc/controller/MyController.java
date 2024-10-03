package mvc.controller;

import mvc.model.ModelInterface;

/**
 * 
 * A Controller component.
 * 
 * It is an observer of the Input.
 * 
 */
public class MyController implements UserInputObserver {
	
	private ModelInterface model;

	public MyController(ModelInterface model){
		this.model = model;
	}
	
	public void notifyNewUpdateRequested() {
		log("New update requested by the user");
		model.update();
	}

	private void log(String msg) {
		// System.out.println("[Controller] " + msg);
	}
}
