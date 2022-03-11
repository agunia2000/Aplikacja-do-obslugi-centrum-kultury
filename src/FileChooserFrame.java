import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileFilter;

public class FileChooserFrame {

    private JFileChooser fileChooser = new JFileChooser("Wybierz plakat wydarzenia:");
    private JPanel mainPanel;


    public FileChooserFrame(JTextField pathToJpg) {

        File file;
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes()));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setVisible(true);
        int result = fileChooser.showSaveDialog(fileChooser);

        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            pathToJpg.setText(file.getAbsolutePath());
        } else if (result == JFileChooser.CANCEL_OPTION) {
        }
    }
}
