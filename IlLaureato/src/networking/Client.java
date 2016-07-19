package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Client extends Thread {
   private String ip;
   private int port;
   private Socket socketSender;

   private List<String> send;//
   private List<String> recive;//

   private String nomeGiocatore;

   private Lock lock;
   private Condition condition;

   private boolean Stop;
   private boolean StopSend = false;
   private boolean StopRecive = false;

   private Socket getSocketS() {
      return this.socketSender;
   }

   public Client(String ip, int port) throws UnknownHostException, IOException {
      this.ip = ip;
      this.port = port;
      this.socketSender = new Socket(ip, port);
      this.send = new CopyOnWriteArrayList<String>();
      this.recive = new CopyOnWriteArrayList<String>();
      this.lock = new ReentrantLock();
      this.condition = this.lock.newCondition();
      this.Stop = false;

// this.send.add("2##ciao##");
// this.send.add("2##ciao##");
// this.send.add("2##ciao##");
// //
// cosi(ID**piet_quantita,piet_quantita**bib_num,bib**h**m**nota**Destinazione)
// this.send.add("#END#");

// this.send.add("#END#");

      System.out.println("Sender " + socketSender.getPort());
      this.start();

   }

   public String getNomeGiocatore() {
      return nomeGiocatore;
   }

   public void setNomeGiocatore(String nomeGiocatore) {
      this.nomeGiocatore = nomeGiocatore;
   }

   public int sizeRecive() {
      return recive.size();
   }

   public String getMessage() {
      if (recive.isEmpty())
         return null;
      return recive.get(0);
   }

   public String popRequest() {
      if (recive.isEmpty())
         return null;
      else {

         String message = recive.get(0);

         if (message == null) {
            return "null";
         }

         recive.remove(0);
         return message;
      }
   }

   public void addRequest(String r) {
      lock.lock();
      send.add(r);
      condition.signalAll();
      lock.unlock();
   }

   @Override
   public void run() {

      new Thread() {

         @Override
         public void run() {
            PrintWriter out;
            try {
               out = new PrintWriter(getSocketS().getOutputStream());
               try {
                  sleep(100);
               }
               catch (InterruptedException e) {

                  e.printStackTrace();
               }
               while (!StopSend) {

                  lock.lock();

                  while (send.isEmpty())
                     try {
                        condition.await();
                     }
                     catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }

                  lock.unlock();

                  if (!send.isEmpty()) {
                     String message = send.get(0);
                     out.println(message);
                     System.out.println("send " + message);
                     out.flush();
                     if (send.get(0).equals("#END#")) {
                        StopSend = true;

                     }
                     send.remove(0);
                  }

               }
               System.out.println("Stop send");
               try {
                  sleep(100);
               }
               catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               out.close();
               // new LockManager().riprendiEndAll();
            }
            catch (IOException e) {
               e.printStackTrace();
            }
         }

      }.start();

      new Thread() {

         @Override
         public void run() {
            BufferedReader in;
            try {
               in = new BufferedReader(
                     new InputStreamReader(getSocketS().getInputStream()));
               try {
                  sleep(100);
               }
               catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               while (!StopRecive) {
                  String S = in.readLine();

                  System.out.println("Recive " + S);
                  if (S == null || S.equals("#END#")) {

                     StopRecive = true;

                     lock.lock();
                     send.add(S);
                     condition.signalAll();
                     lock.unlock();
                  }
                  RequestManagerClient.lock.lock();
                  recive.add(S);
                  RequestManagerClient.condition.signalAll();
                  RequestManagerClient.lock.unlock();

               }
               System.out.println("Stoprecive");
               in.close();
            }
            catch (IOException e) {
               e.printStackTrace();
            }
         }

      }.start();

   }

   public static void main(String[] args) {
      try {
         Client c = new Client("127.0.0.1", 4444);
         if (c == null)
            System.err.println("client nullo ");
         // c.start();
      }
      catch (UnknownHostException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

}
