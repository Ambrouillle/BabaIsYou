package babaIsYou;

import java.awt.*;
import java.awt.geom.Ellipse2D;


import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import fr.umlv.zen5.ApplicationContext;


public class Visual {
    private Ellipse2D.Float ellipse = new Ellipse2D.Float(0, 0, 0, 0);

    static class Area {
        Image loadImage(int id) {
            Image img;
            String filename ="Ressources" + File.separator + id + ".gif";
            System.out.println(filename);
            img = Toolkit.getDefaultToolkit().getImage(filename);
            return img;

        }
        void drawImg(ApplicationContext context, int id, float x, float y){
            Image img;
            img = loadImage(id);
            context.renderFrame(graphics -> {
                graphics.drawImage(img, (int)x,(int)y,null);
            });
        }
    }
}
