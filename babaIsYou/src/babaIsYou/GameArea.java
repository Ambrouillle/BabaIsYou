package babaIsYou;

import babaIsYou.*;
import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.EventBabaGame;
import babaIsYou.entity.entityEnum.PropertyEnum;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameArea {
    private ApplicationContext context;
    private float Width;
    private float Height;
    private Level lvl;
    private int lvlx;
    private int lvly;
    private int boardOriginx;
    private int boardOriginy;
    private Visual.Area area;

    GameArea(ApplicationContext context){
        this.context = context;
        ScreenInfo info = context.getScreenInfo();
        this.Width = info.getWidth();
        this.Height = info.getHeight();

    }
    EventBabaGame checkMovement(Event event, EntityFactory factory) throws IOException{
        EventBabaGame ev;
        ev = EventBabaGame.Good;
        DirectionEnum dir;

        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {
            dir = DirectionEnum.valueOf(event.getKey().name());
            
            EventBabaGame turnResult = lvl.mooveProp(factory, PropertyEnum.You, dir);
            
            lvl.removeFromToDestroy();
            
            
            return turnResult;
           
        }
        return EventBabaGame.Good;
    }

    public void setSize(){
        this.lvly = lvl.getPlateau().length ;
        this.lvlx = lvl.getPlateau()[0].length;
        area = new Visual.Area(this.Height/lvlx, this.Width/lvly);
        this.boardOriginx = (int)(this.Width / 2) - (area.imagesSize * lvly/2);
        this.boardOriginy = (int)(this.Height / 2) - (area.imagesSize * lvlx/2);
    }

    public int run(int id) throws IOException{
        int returnVal;
        lvl = new Level(id); // creation lvl
        EventBabaGame ev;

        setSize();
        returnVal = 1;
        ev = EventBabaGame.Good;
        area.RefreshScreen(context,(int)this.Width,(int)this.Height,lvl,boardOriginx,boardOriginy);
        for(;;) {
            Event event = context.pollOrWaitEvent(10);
            if (event == null) {  // no event
                continue;
            }
            Action action = event.getAction();
            if (event.getKey() == KeyboardKey.L) {
                return returnVal;
            }
            if (ev != EventBabaGame.Defeat && ev != EventBabaGame.Win) {
                if (action == Action.KEY_PRESSED) {
                    ev = checkMovement(event, lvl.getFactory());
                    if (lvl.isLost(lvl.getFactory()) == EventBabaGame.Defeat) {
                        ev = EventBabaGame.Defeat;
                    }
                    area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginx, boardOriginy);
                }
            }
            if (ev == EventBabaGame.Defeat){
                area.Loose(context,(int)this.Width, (int)this.Height);
            }
            else if(ev == EventBabaGame.Win){
                returnVal = 2;
                area.Win(context,(int)this.Width, (int)this.Height);
            }
        }
    }
}