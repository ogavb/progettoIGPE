package networking;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class MultiThreadedServer implements Runnable {

   protected int serverPort = 4444;
   protected ServerSocket serverSocket = null;
   protected boolean isStopped = false;
   protected Thread runningThread = null;

   public MultiThreadedServer(int port) {
      this.serverPort = port;
   }

   public void run() {
      synchronized (this) {
         this.runningThread = Thread.currentThread();
      }
      System.out.println("StartServer");
      openServerSocket();
      while (!isStopped()) {
         Socket clientSocket = null;
         try {
            clientSocket = this.serverSocket.accept();
         }
         catch (IOException e) {
            if (isStopped()) {
               System.out.println("Server Stopped.");
               return;
            }
            throw new RuntimeException("Error accepting client connection", e);
         }
         Connection C = new Connection(clientSocket);
         GestoreMatch.getInstance().addUsers(C);
         new Thread(C).start();
      }
      System.out.println("Server Stopped.");
   }

   private synchronized boolean isStopped() {
      return this.isStopped;
   }

   public synchronized void stop() {
      this.isStopped = true;
      try {
         // RequestManager.getInstance().close(true);
         this.serverSocket.close();
      }
      catch (IOException e) {
         throw new RuntimeException("Error closing server", e);
      }
   }

   private void openServerSocket() {
      try {
         this.serverSocket = new ServerSocket(this.serverPort);
      }
      catch (IOException e) {
         throw new RuntimeException("Cannot open port " + this.serverPort, e);
      }
   }

}