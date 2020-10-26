
package util;

import javax.swing.JLabel;

public class TimeThread extends Thread{
    private JLabel l;

    public TimeThread(JLabel l) {
        this.l = l;
    }

    @Override
    public void run() {
       int elapsed = 0;
       l.setText("Elapsed: 0 sec");
        while (true) {            
            l.setText("Elapsed: "+ ++elapsed +" sec");
            try {
                sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
}
