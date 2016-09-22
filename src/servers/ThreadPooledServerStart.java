package servers;

public class ThreadPooledServerStart {

	public static void main(String[] args) {		
		ThreadPooledServer server = new ThreadPooledServer(9000);
		new Thread(server).start();
		try {
		    Thread.sleep(40 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		server.stop();
	}

}

