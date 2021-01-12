package babaIsYou;

import java.awt.*;


import java.awt.geom.Rectangle2D;
import java.io.File;

import babaIsYou.entity.Entity;
import fr.umlv.zen5.ApplicationContext;


public class Visual {

    static class Area {
        int imagesSize;
        Area(float h, float w){
            imagesSize = (int)(Math.min(h, w));
        }
        Image loadImage(int id) {
            Image img;
            String filename ="Ressources" + File.separator + id + ".gif";
            img = Toolkit.getDefaultToolkit().getImage(filename);
            return img;

        }

        void Win(ApplicationContext context, int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(Color.BLACK);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
                graphics.setColor(Color.WHITE);
                graphics.drawString("You Won", width/2, height/2);
            });
        }

        void Loose(ApplicationContext context, int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(new Color(0f,0f,0f,0.5f));
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
                graphics.setColor(Color.WHITE);
                graphics.drawString("You Lost", width/4, height/2);
            });
        }

        void DrawImg(ApplicationContext context, int id, float x, float y){
            Image img;
            img = loadImage(id);

            context.renderFrame(graphics -> graphics.drawImage(img, (int)x,(int)y,imagesSize,imagesSize,null));
        }
        void ClearScreen(ApplicationContext context,int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(Color.BLACK);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
            });
        }

        void DrawPLayableFIeld(ApplicationContext context, int bOriginX, int bOriginy, Level lvl){
            context.renderFrame(graphics -> {
                graphics.setColor(new Color(.05f,.05f,.05f));
                graphics.fill(new  Rectangle2D.Float(bOriginX, bOriginy, lvl.getPlateau()[0].length*imagesSize, lvl.getPlateau().length*imagesSize));
            });
        }

        void RefreshScreen(ApplicationContext context, int width, int height, Level lvl, int boardOriginx, int boardOriginy){
            this.ClearScreen(context, width,height);
            this.DrawPLayableFIeld(context,boardOriginx,boardOriginy,lvl);
            for (Cell[] plt : lvl.getPlateau()){
                for (Cell c : plt) {
                    for (Entity e : c.getContent()) {
                        this.DrawImg(context, e.getImageId(), boardOriginx +(e.getx()*imagesSize), boardOriginy +(e.gety()*imagesSize));
                    }
                }
            }
        }
    }
}