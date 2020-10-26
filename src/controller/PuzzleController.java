package controller;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.TimeThread;

public class PuzzleController implements ActionListener {
    private JPanel p;
    private JLabel label1, label2;
    private List<JButton> b = new ArrayList<>();
    private int size;
    private int indexSpace;
    private int moveCount;
    private TimeThread tt;

    public PuzzleController(JPanel p, JLabel label1, TimeThread tt) {
        this.p = p;
        this.label1 = label1;
        this.tt = tt;
    }

    public void makeGame(JComboBox cb, JLabel label2) {
        label1.setText("Move count: 0");
        if (tt.isAlive()) {
            tt.stop();
        }
        tt = new TimeThread(label2);
        tt.start();
        setPanel(cb);
        mixNumber();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < b.size(); i++) {
            if (e.getSource().equals(b.get(i))) {
                pressNumber(i);//tao event
            }
        }
    }

    public void setPanel(JComboBox cb) {
        p.removeAll();
        b.removeAll(b);
        String str = cb.getSelectedItem().toString();
        size = Integer.valueOf(str.substring(0, str.indexOf('x')).trim());
        p.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size * size - 1; i++) {
            JButton btn = new JButton(i + 1 + "");
            btn.setFont(new Font("Tahoma", 100, 100 / size));
            b.add(btn);
            p.add(btn);
            btn.addActionListener(this);
        }
        JButton btn = new JButton("");
        btn.setFont(new Font("Tahoma", 100, 100 / size));
        b.add(btn);
        p.add(btn);
        btn.addActionListener(this);//take event
    }

    public void mixNumber() {
        Random r = new Random();
        indexSpace = b.size() - 1;//mac dinh ban dau vi tri cuoi
        for (int i = 0; i < 100 * size; i++) {//so lan mix ko important
            int choose = 1 + r.nextInt(4);//1 up, 2 down, 3 left, 4 right
            if (indexSpace < size && choose == 1) {
                continue;
            }
            if (indexSpace >= size * size - size && choose == 2) {
                continue;
            }
            if (indexSpace % size == 0 && choose == 3) {
                continue;
            }
            if (indexSpace % size == size - 1 && choose == 4) {
                continue;
            }
            switch (choose) {
                case 1:swapBtn(indexSpace, indexSpace-size);
                    indexSpace -= size;
                    break;
                case 2:
                    swapBtn(indexSpace, indexSpace+   size);
                    indexSpace += size;
                    break;
                case 3:swapBtn(indexSpace, indexSpace -1);
                    indexSpace -= 1;
                    break;
                case 4:
                    swapBtn(indexSpace, indexSpace +1);
                    indexSpace += 1;
                    break;
            }
        }
    }

    public void swapBtn(int iFirst, int iSecond) {
        String temp = b.get(iFirst).getText();
        b.get(iFirst).setText(b.get(iSecond).getText());
        b.get(iSecond).setText(temp);
    }

    public void pressNumber(int i){//event an nut
        if (i < size * size - size && b.get(i + size).getText().equals("")){
            swapBtn(i, i + size);
            label1.setText("Move count: " + ++moveCount);
        }else if (i > size - 1 && b.get(i - size).getText().equals("")) {
            swapBtn(i, i - size);
            label1.setText("Move count: " + ++moveCount);
        } else if (i % size < size - 1 && b.get(i + 1).getText().equals("")) {
            swapBtn(i, i + 1);
            label1.setText("Move count: " + ++moveCount);
        } else if (i % size > 0 && b.get(i - 1).getText().equals("")) {
            swapBtn(i, i - 1);
            label1.setText("Move count: " + ++moveCount);
        }
        if (i == size * size - 1 && b.get(i).getText().equals("")) {
            if (checkWin()) {
                JOptionPane.showMessageDialog(null, "You win!");
                tt.stop();
            }
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < b.size() - 1; i++) {
            if (!b.get(i).getText().equals(i + 1 + "")) {
                return false;
            }
        }
        return true;
    }
}
