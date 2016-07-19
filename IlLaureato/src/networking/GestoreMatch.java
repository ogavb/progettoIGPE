package networking;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GestoreMatch {

   private static GestoreMatch instance;
   private static List<Match> matchs;
   private List<Connection> users;

   public static GestoreMatch getInstance() {
      if (instance == null)
         instance = new GestoreMatch();
      return instance;
   }

   private GestoreMatch() {
      matchs = new CopyOnWriteArrayList<Match>();
      users = new CopyOnWriteArrayList<Connection>();
   }

   public void removeMatch(Match m) {
      matchs.remove(m);
   }

   public List<Match> getMatchs() {
      return matchs;
   }

   public List<Connection> getNameUsers() {
      return users;
   }

   public void addUsers(Connection c) {
      users.add(c);
   }

   public void removeUsers(Connection c) {
      users.remove(c);
   }

   public synchronized int getProgressiveId(int numberPlayer, Connection c) {

      Match temp = null;
      int i = 0;
      boolean find = false;
      do {
         i = i + 1;
         if (findForId(i) == null) {
            temp = new Match(i, numberPlayer, c);
            matchs.add(temp);
            find = true;
         }

      } while (!find);

      return i;
   }

   @Override
   public String toString() { // id,maxpers,ncollegati/id,maxpers,ncollegati
      String tmp = "";
      boolean first = false;
      for (Match m : matchs) {
         if (!first) {
            first = true;
         }
         else {
            tmp += "/";
         }
         tmp += m.toString();

      }

      return tmp;

   }

   public void stampa() {
      System.out.print("[");
      for (Match m : matchs) {
         System.out.print(m.getIdMatch() + ", ");
      }
      System.out.println("]");
   }

   public Match findForId(int id) {
      for (Match m : matchs) {
         if (id == m.getIdMatch()) {
            System.err.println("HO TROVATO IL MATCH");
            return m;
         }
      }
      return null;
   }

   public void notifyAll(String message) {

      for (Connection c : users) {

         c.insertMessage(message);

      }

   }

   public void notifyAllUserInMatch(String message) {

      for (Match m : matchs) {

         m.notifyAll(message);

      }

   }

   // si vuole modellare una classe per gestire i match e di tipo singleton
   // al fine di gestire lan e network
   // list match

   // Match findForId(int id){}

}
