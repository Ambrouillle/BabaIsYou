package babaIsYou;

import java.awt.*;


import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import babaIsYou.entity.Entity;
import fr.umlv.zen5.ApplicationContext;

import javax.imageio.ImageIO;


public class Visual {

    static class Area {
        int imagesSize;
        Map<Integer, Image> images;
        /**
         * Area's constructor.
         * @param h Area's Height.
         * @param w Area's Width.
         */
        Area(float h, float w){
            imagesSize = (int)(Math.min(h, w));
            images = new HashMap<Integer, Image>();
        }

        /**
         * Load id's associated image.
         * @param id Id of the desired image.
         * @return The loaded image.
         */
        Image loadImage(int id) throws IOException {
            Image img;
            if (!images.containsKey(id)) {
                String filename = "Ressources" + File.separator + id + ".gif";
                img = ImageIO.read(new File(filename));
                images.put(id,img);
            }
            else {
                img = images.get(id);
            }
            return img;

        }
        /**
         * Visual win screen interface.
         * @param context Zen5 Application reference.
         * @param width Screen Width.
         * @param height Screen Height.
         */
        void Win(ApplicationContext context, int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(Color.BLACK);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
                graphics.setColor(Color.WHITE);
                graphics.drawString("You Won", width/2, height/2);
            });
        }
        /**
         * Visual lost screen interface.
         * @param context Zen5 Application reference.
         * @param width Screen Width.
         * @param height Screen Height.
         */
        void Loose(ApplicationContext context, int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(new Color(0f,0f,0f,0.5f));
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
                graphics.setColor(Color.WHITE);
                graphics.drawString("You Lost", width/4, height/2);
            });
        }

        /**
         * Draw the desired image at the given position.
         * @param context Zen5 Application reference.
         * @param id Image Id.
         * @param x X-axis coordinate of the wanted position.
         * @param y Y-axis coordinate of the wanted position.
         */
        void DrawImg(ApplicationContext context, int id, float x, float y) throws IOException {
            Image img;
            img = loadImage(id);
            context.renderFrame(graphics -> graphics.drawImage(img, (int)x,(int)y,imagesSize,imagesSize,null));
        }

        /**
         * Clear the screen.
         * @param context Zen5 Application reference.
         * @param width Screen Width.
         * @param height Screen Height.
         */
        void ClearScreen(ApplicationContext context,int width, int height){
            context.renderFrame(graphics -> {
                graphics.setColor(Color.BLACK);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
            });
        }
    
        void ClearCell(ApplicationContext context, int x, int y){
            context.renderFrame(graphics -> {
                graphics.setColor(new Color(.05f,.05f,.05f));
                graphics.fill(new  Rectangle2D.Float(x, y, imagesSize, imagesSize));
            });
        }
        /**
         * Draw a slightly different area to delimit the playable area.
         * @param context Zen5 Application reference.
         * @param bOriginX X-axis coordinate of the board origin.
         * @param bOriginy Y-axis coordinate of the board origin.
         * @param lvl Actual level.
         */
        void DrawPLayableFIeld(ApplicationContext context, int bOriginX, int bOriginy, Level lvl){
            context.renderFrame(graphics -> {
                graphics.setColor(new Color(.05f,.05f,.05f));
                graphics.fill(new  Rectangle2D.Float(bOriginX, bOriginy, lvl.getPlateau()[0].length*imagesSize, lvl.getPlateau().length*imagesSize));
            });
        }

        /**
         * Refresh the screen clearing then drawing all again.
         * @param context Zen5 Application reference.
         * @param width Screen Width.
         * @param height Screen Height.
         * @param lvl Actual level.
         * @param boardOriginX X-axis coordinate of the board origin.
         * @param boardOriginY Y-axis coordinate of the board origin.
         */
        void RefreshScreen(ApplicationContext context, int width, int height, Level lvl, int boardOriginX, int boardOriginY){
            for (Cell[] plt : lvl.getPlateau()){
                for (Cell c : plt) {
                    if(c.isChanged()){
                        ClearCell(context, boardOriginX + (c.getx() * imagesSize), boardOriginY + (c.gety() * imagesSize));
                        for (Entity e : c.getContent()) {
                            try {
                                this.DrawImg(context, e.getImageId(), boardOriginX + (e.getx() * imagesSize), boardOriginY + (e.gety() * imagesSize));
                            } catch (IOException ioException) {
                                System.out.println(ioException);
                            }
                        }
                        c.setChanged(false);
                    }
                }
            }
        }
    }
}