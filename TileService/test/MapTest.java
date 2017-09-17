import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MapTest {
	static ExecutorService pool;
	public static void main(String[] args) {
		
	}
	
	
	public  void init() {
		pool = Executors.newFixedThreadPool(5);
	}
	
	public void startDownloadThread(){
		pool.execute(new Runnable() {

			@Override
			public void run() {
				
				
			}});
	}
}
