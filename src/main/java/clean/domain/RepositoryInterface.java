package clean.domain;

/**
 * 
 * Outbound Port
 * 
 */
public interface RepositoryInterface {

	void save(Count count) throws RepositoryException;
}
