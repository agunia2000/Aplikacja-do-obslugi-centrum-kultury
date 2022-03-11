import javax.swing.*;
import java.awt.*;

public class PreviewPosterFrame {
    private JPanel mainPanel;
    private JLabel image;

    public PreviewPosterFrame(Event event) {
        JFrame frame = new JFrame(event.getName());
        frame.add(mainPanel);
        image.setIcon(new ImageIcon(new ImageIcon(event.getPathToPoster()).getImage().getScaledInstance(-1, 500, Image.SCALE_DEFAULT)));

        if(image.getIcon().getIconHeight()!=500)
            image.setIcon(new ImageIcon(new ImageIcon("src/posters/no_picture_poster.jpg").getImage().getScaledInstance(-1, 500, Image.SCALE_DEFAULT)));

        frame.setVisible(true);
        frame.pack();
    }

}
