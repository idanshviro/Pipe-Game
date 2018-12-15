package cacheManager;

import java.io.FileNotFoundException;
import solver.Solution;

public interface CacheManager {
	public void save(String problem,Solution sol) throws FileNotFoundException;
	public String load(String problem);
	public boolean exists(String problem);
}
