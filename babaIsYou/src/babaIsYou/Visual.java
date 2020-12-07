package babaIsYou;

import java.awt.*;
import java.awt.geom.Ellipse2D;


import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import babaIsYou.entity.Entity;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;


public class Visual {

    static class Area {
        Image loadImage(int id) {
            Image img;
            String filename ="Ressources" + File.separator + id + ".gif";
            img = Toolkit.getDefaultToolkit().getImage(filename);
            return img;

        }
        void DrawImg(ApplicationContext context, int id, float x, float y){
            Image img;
            img = loadImage(id);
            context.renderFrame(graphics -> {
                graphics.drawImage(img, (int)x,(int)y,null);
            });
        }
        void ClearScreen(ApplicationContext context,int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(Color.BLACK);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
            });
        }

        void RefreshScreen(ApplicationContext context, int width, int height, Level lvl, int boardOriginx, int boardOriginy){
            this.ClearScreen(context, width,height);
            for (Cell plt[] : lvl.plateau){
                for (Cell c : plt) {
                    for (Entity e : c.content) {
                        this.DrawImg(context, e.getImageID(), boardOriginx +(e.getx()*100), boardOriginy +(e.gety()*100));
                    }
                }
            }
        }
    }
}
