package clean.infrastructure;

import java.io.*;

import clean.domain.*;
import io.vertx.core.json.JsonObject;

public class MyRepoAdapter implements RepositoryInterface {

	private String COUNT_PATH = "counts";
	
	private String dbaseFolder;
	
	public MyRepoAdapter() {
		this.dbaseFolder =  "dbase";
	}

	public void init() {
		makeDir(dbaseFolder);
		makeDir(dbaseFolder + File.separator + COUNT_PATH);
	}	

	@Override
	public void save(Count count) throws RepositoryException {
		JsonObject obj = new JsonObject();
		obj.put("type", "count");
		obj.put("id", count.getId());
		obj.put("value", count.getValue());
		this.saveObj(COUNT_PATH, count.getId(), obj);
	}

	private void saveObj(String db, String id, JsonObject obj) throws RepositoryException {
		try {									
			FileWriter fw = new FileWriter(dbaseFolder + File.separator + db + File.separator + id + ".json");
			java.io.BufferedWriter wr = new BufferedWriter(fw);	
		
			wr.write(obj.encodePrettily());
			wr.flush();
			fw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RepositoryException();
		}
	}
	
	private void makeDir(String name) {
		try {
			File dir = new File(name);
			if (!dir.exists()) {
				dir.mkdir();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
