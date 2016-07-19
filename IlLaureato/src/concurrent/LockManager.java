package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {

   private static Lock lockZero;
   private static Condition conditionZero;

   private static Lock lockUnoQuattro;
   private static Condition conditionUnoQuattro;

   private static Lock lockTre;
   private static Condition conditionTre;

   private static Lock lockQuattro;
   private static Condition conditionQuattro;

   private static Lock lockCinque;
   private static Condition conditionCinque;

   private static Lock lockSei;
   private static Condition conditionSei;

   private static Lock lockSette;
   private static Condition conditionSette;

   private static Lock lockOtto;
   private static Condition conditionOtto;

   private static Lock lockTredici;
   private static Condition conditionTredici;

   private static Lock lockClose;
   private static Condition conditionClose;

   private static Lock lockEndAll;
   private static Condition conditionEndAll;

   private static Lock lockEndMatch;
   private static Condition conditionEndMatch;

   private static boolean inAttesaZero = true;
   private static boolean inAttesaUnoQuattro = true;
   private static boolean inAttesaTre = true;
   private static boolean inAttesaQuattro = true;
   private static boolean inAttesaCinque = true;
   private static boolean inAttesaSei = true;
   private static boolean inAttesaSette = true;
   private static boolean inAttesaOtto = true;
   private static boolean inAttesaTredici = true;
   private static boolean inAttesaClose = true;
   private static boolean inAttesaEndAll = true;
   private static boolean inAttesaEndMatch = true;

   public LockManager() {

      lockZero = new ReentrantLock();
      conditionZero = lockZero.newCondition();

      lockUnoQuattro = new ReentrantLock();
      conditionUnoQuattro = lockUnoQuattro.newCondition();

      lockTre = new ReentrantLock();
      conditionTre = lockTre.newCondition();

      lockQuattro = new ReentrantLock();
      conditionQuattro = lockQuattro.newCondition();

      lockCinque = new ReentrantLock();
      conditionCinque = lockCinque.newCondition();

      lockSei = new ReentrantLock();
      conditionSei = lockSei.newCondition();

      lockSette = new ReentrantLock();
      conditionSette = lockSette.newCondition();

      lockOtto = new ReentrantLock();
      conditionOtto = lockOtto.newCondition();

      lockClose = new ReentrantLock();
      conditionClose = lockClose.newCondition();

      lockTredici = new ReentrantLock();
      conditionTredici = lockTredici.newCondition();

      lockEndAll = new ReentrantLock();
      conditionEndAll = lockEndAll.newCondition();

      lockEndMatch = new ReentrantLock();
      conditionEndMatch = lockEndMatch.newCondition();

   }

   public void attendiZero() throws InterruptedException {

      lockZero.lock();
      while (inAttesaZero) {
         conditionZero.await();
      }
      inAttesaZero = true;
      lockZero.unlock();
   }

   public void riprendiZero() {

      lockZero.lock();
      inAttesaZero = false;
      conditionZero.signalAll();
      lockZero.unlock();

   }

   public void attendiUnoQuattro() throws InterruptedException {

      lockUnoQuattro.lock();

      while (inAttesaUnoQuattro) {
         conditionUnoQuattro.await();
      }
      inAttesaUnoQuattro = true;
      lockUnoQuattro.unlock();
   }

   public void riprendiUnoQuattro() {

      lockUnoQuattro.lock();
      inAttesaUnoQuattro = false;
      conditionUnoQuattro.signalAll();
      lockUnoQuattro.unlock();

   }

   public void attendiTre() throws InterruptedException {

      lockTre.lock();

      while (inAttesaTre) {
         conditionTre.await();
      }
      inAttesaTre = true;
      lockTre.unlock();
   }

   public void riprendiTre() {

      lockTre.lock();
      inAttesaTre = false;
      conditionTre.signalAll();
      lockTre.unlock();
   }

   public void attendiQuattro() throws InterruptedException {

      lockQuattro.lock();

      while (inAttesaQuattro) {
         conditionQuattro.await();
      }
      inAttesaQuattro = true;
      lockQuattro.unlock();
   }

   public void riprendiQuattro() {

      lockQuattro.lock();
      inAttesaQuattro = false;
      conditionQuattro.signalAll();
      lockQuattro.unlock();
   }

   public void attendiCinque() throws InterruptedException {

      lockCinque.lock();

      while (inAttesaCinque) {
         conditionCinque.await();
      }
      inAttesaCinque = true;
      lockCinque.unlock();
   }

   public void riprendiCinque() {

      lockCinque.lock();
      inAttesaCinque = false;
      conditionCinque.signalAll();
      lockCinque.unlock();

   }

   public void attendiSei() throws InterruptedException {

      lockSei.lock();
      while (inAttesaSei) {
         conditionSei.await();
      }
      inAttesaSei = true;
      lockSei.unlock();
   }

   public void riprendiSei() {

      lockSei.lock();
      inAttesaSei = false;
      conditionSei.signalAll();
      lockSei.unlock();

   }

   public void attendiSette() throws InterruptedException {

      lockSette.lock();

      while (inAttesaSette) {
         conditionSette.await();
      }
      inAttesaSette = true;
      lockSette.unlock();
   }

   public void riprendiSette() {

      lockSette.lock();
      inAttesaSette = false;
      conditionSette.signalAll();
      lockSette.unlock();

   }

   public void attendiOtto() throws InterruptedException {

      lockOtto.lock();

      while (inAttesaOtto) {
         conditionOtto.await();
      }
      inAttesaOtto = true;
      lockOtto.unlock();
   }

   public void riprendiOtto() {

      lockOtto.lock();
      inAttesaOtto = false;
      conditionOtto.signalAll();
      lockOtto.unlock();

   }

   public void attendiTredici() throws InterruptedException {

      lockTredici.lock();

      while (inAttesaZero) {
         conditionTredici.await();
      }

      inAttesaTredici = true;
      lockTredici.unlock();
   }

   public void riprendiTredici() {

      lockTredici.lock();
      inAttesaTredici = false;
      conditionTredici.signalAll();
      lockTredici.unlock();

   }

   public void attendiClose() throws InterruptedException {

      lockClose.lock();

      while (inAttesaClose) {
         conditionClose.await();
      }

      inAttesaClose = true;
      lockClose.unlock();

   }

   public void riprendiClose() {

      lockClose.lock();
      inAttesaClose = false;
      conditionClose.signalAll();
      lockClose.unlock();

   }

   public void attendiEndAll() throws InterruptedException {

      lockEndAll.lock();

      while (inAttesaEndAll) {
         conditionEndAll.await();
      }
      lockEndAll.unlock();
   }

   public void riprendiEndAll() {

      lockEndAll.lock();
      inAttesaEndAll = false;
      ;
      conditionEndAll.signalAll();
      lockEndAll.unlock();

   }

   public void attendiEndMatch() throws InterruptedException {

      lockEndMatch.lock();

      while (inAttesaEndMatch) {
         conditionEndMatch.await();
      }
      inAttesaEndMatch = true;
      lockEndMatch.unlock();
   }

   public void riprendiEndMatch() {

      lockEndMatch.lock();
      inAttesaEndMatch = false;
      conditionEndMatch.signalAll();
      lockEndMatch.unlock();

   }

}
