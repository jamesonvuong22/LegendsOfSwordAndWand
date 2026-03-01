package lowsw;

import javax.swing.*;

// With the use of AI
public class ProfileModuleMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("LoSW - Profile Module (Deliverable 1)");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(520, 220);
            f.add(new JLabel("Profile UI shell: implement register/login panels here."), "Center");
            f.setVisible(true);
        });
    }
}
