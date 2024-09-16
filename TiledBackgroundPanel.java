import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TiledBackgroundPanel extends JPanel {
    private BufferedImage image;

    public TiledBackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the dimensions of the panel and image
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // Draw the image at the center of the panel
        int x = (panelWidth - imageWidth) / 2;
        int y = (panelHeight - imageHeight) / 2;
        g.drawImage(image, x, y, null);
    }
}
