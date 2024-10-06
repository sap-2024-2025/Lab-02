package clean.domain;

/**
 * 
 * Domain entity
 * 
 */
public class Count {

	private int count;
	private String id;
	
	public Count(String id) {
		count = 0;
		this.id = id;
	}
	
	public int getValue() {
		return count;
	}

	public void inc() {
		count++;
	}
	
	public String getId() {
		return id;
	}
	
}
