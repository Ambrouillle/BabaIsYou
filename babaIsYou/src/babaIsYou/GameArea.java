package babaIsYou;

import babaIsYou.*;
import babaIsYou.entity.Entity;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import babaIsYou.Visual;

public class GameArea {
    ApplicationContext context;
    float Width;
    float Height;
    Level lvl;

    GameArea(ApplicationContext context, Level level){
        this.context = context;
        ScreenInfo info = context.getScreenInfo();
        this.Width = info.getWidth();
        this.Height = info.getHeight();
        this.lvl = level;
    }
    public void run(){
    Visual.Area area = new Visual.Area();
    int lvly = lvl.plateau.length ;
    int lvlx = lvl.plateau[0].length;
    int boardOriginx = (int)(this.Width / 2) - (100 * lvly/2);
    int boardOriginy = (int)(this.Height / 2) - (100 * lvlx/2);
    for(;;) {

          Event event = context.pollOrWaitEvent(10);
          if (event == null) {  // no event
              continue;
          }
          Action action = event.getAction();
          if (event.getKey() == KeyboardKey.L) {
              context.exit(0);
          }
          if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
              for (Cell plt[] : lvl.plateau){
                  for (Cell c : plt) {
                      for (Entity e : c.content) {
                          area.drawImg(context, e.getImageID(), boardOriginx +(e.getx()*100), boardOriginy +(e.gety()*100));
                      }
                  }
              }
          }
        }
    }
}
