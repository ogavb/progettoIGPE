package networking;

public class Main {

   public static void main(String[] args) {
      MultiThreadedServer server = new MultiThreadedServer(4444);
      new Thread(server).start();

// try {
// Thread.sleep(20 *100000);//TODO da eliminare
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
// System.out.println("Stopping Server");
// server.stop();

   }

}
