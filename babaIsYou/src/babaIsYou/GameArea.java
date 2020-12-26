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
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameArea {
    ApplicationContext context;
    float Width;
    float Height;
    Level lvl;
    int lvlx;
    int lvly;
    int boardOriginx;
    int boardOriginy;
    Visual.Area area;

    GameArea(ApplicationContext context){
        this.context = context;
        ScreenInfo info = context.getScreenInfo();
        this.Width = info.getWidth();
        this.Height = info.getHeight();

    }
    EventBabaGame checkMovement(Event event, EntityFactory factory){
        EventBabaGame ev;
        ev = EventBabaGame.Good;
        DirectionEnum dir;

        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {

            ArrayList<Element> list = new ArrayList<>();
        	System.out.println(lvl.getPropertyHashMap().get(ElementEnum.Baba.getElemID()));
        	System.out.println(lvl.getPropertyHashMap().get(ElementEnum.Lava.getElemID()));
        	System.out.println(lvl.factory.elementHashMap.get(ElementEnum.Baba.getElemID()).size() );
            dir = DirectionEnum.valueOf(event.getKey().name());
            for(int elemId: lvl.getElemnwithProp(PropertyEnum.You)) {
            	list.addAll(lvl.factory.elementHashMap.get(elemId));
            }

            for(Entity en : list) {
            	
                ev = lvl.moove(en, dir);
                if (ev == EventBabaGame.Defeat){
                	lvl.mooveProp(factory, PropertyEnum.You, dir);
                	return EventBabaGame.Defeat;
                }
                if(ev== EventBabaGame.Win) {
                	return EventBabaGame.Win;
                }
            }

            if (lvl.toDestroy != null) {
                    lvl.removeFromToDestroy(factory);
            }
            return lvl.isLost(factory) == EventBabaGame.Good ? ev : EventBabaGame.Defeat;
        }
        return EventBabaGame.Good;
    }

    public void setSize(){
        this.lvly = lvl.plateau.length ;
        this.lvlx = lvl.plateau[0].length;
        area = new Visual.Area(this.Height/lvlx, this.Width/lvly);
        this.boardOriginx = (int)(this.Width / 2) - (area.imagesSize * lvly/2);
        this.boardOriginy = (int)(this.Height / 2) - (area.imagesSize * lvlx/2);
    }

    public int run(int id){
        int returnVal;
        lvl = new Level(id); // creation lvl
        lvl.addPropInMap(PropertyEnum.You, ElementEnum.Baba.getElemID());
        lvl.addPropInMap(PropertyEnum.Push, ElementEnum.Rock.getElemID());
        lvl.addPropInMap(PropertyEnum.Sink,ElementEnum.Water.getElemID());
        lvl.addPropInMap(PropertyEnum.Win,ElementEnum.Flag.getElemID());
        lvl.addPropInMap(PropertyEnum.Hot,ElementEnum.Lava.getElemID());
        lvl.addPropInMap(PropertyEnum.Melt,ElementEnum.Baba.getElemID());
        lvl.addPropInMap(PropertyEnum.Stop,ElementEnum.Wall.getElemID());
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
                    ev = checkMovement(event, lvl.factory);
                    if (lvl.isLost(lvl.factory) == EventBabaGame.Defeat) {
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
