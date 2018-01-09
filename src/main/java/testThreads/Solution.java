package testThreads;

import sun.rmi.runtime.Log;

import javax.swing.text.View;

/**
 * Created by leha on 2017-06-15.
 */
public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.onClick();
    }

    public void onClick() {
        MyThread myThread = new MyThread();
    }

    public class MyThread extends Thread {
        // Конструктор
        MyThread() {
            // Создаём новый поток
           // super("Второй поток");
            System.out.println("Создан второй поток ");
            start(); // Запускаем поток
        }

        public void run() {
            System.out.println("Mой поток запущен...");//

            try {
                for (int i = 5; i > 0; i--) {
                    System.out.println("Второй поток: " + i);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Второй поток прерван");
            }
        }
    }

}
