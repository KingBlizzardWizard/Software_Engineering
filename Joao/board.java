import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class board {

    public void makeBoard(){
        JFrame frame = new JFrame();
        frame.setBounds(10,10,528,551);
        frame.setTitle("Chess");
        frame.setLocation(500,250);
        frame.setUndecorated(false);
        JPanel panel = new JPanel(){
            public void paint(Graphics g){
            boolean white = true;
                for (int y= 0; y<8; y++){
                    for (int x=0; x<8; x++){
                        if (white){
                            g.setColor(Color.white);
                        }else{
                            g.setColor(Color.black);
                        }
                        g.fillRect(x*64, y*64, 64, 64);
                    white = !white;
                    }
                    white = !white;
                }
        }
    };
        frame.add(panel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}