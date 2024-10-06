package clean.domain;

import java.util.ArrayList;
import java.util.List;

public class MyServiceImpl implements MyServiceInterface {

	private Count count;
	private RepositoryInterface repo;
	private List<NotificationInterface> notificationServices;

	public MyServiceImpl() {
		count = new Count("my-count");
		notificationServices = new ArrayList<>();
	}

	public void injectRepository(RepositoryInterface repo) {
		this.repo = repo;
	}

	public void addNotificationService(NotificationInterface notificationService) {
		this.notificationServices.add(notificationService);
	}
	
	@Override
	public void doMyUseCaseCmd() {
		count.inc();
		
		/* persistance */
		try {
			repo.save(count);
		} catch (RepositoryException ex) {
			ex.printStackTrace();
		}
		
		/* notification */
		int v = count.getValue();
		for (var s: notificationServices) {
			s.notifyCountChanged(v);
		}
	}

	@Override
	public int doMyUseCaseQuery() {
		return count.getValue();
	}
	
	
	
}
