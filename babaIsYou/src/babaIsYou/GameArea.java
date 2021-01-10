package babaIsYou;

import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.EventBabaGame;
import babaIsYou.entity.entityEnum.PropertyEnum;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.util.List;

public class GameArea {
    private final ApplicationContext context;
    private final float Width;
    private final float Height;
    private Level lvl;
    private int boardOriginX;
    private int boardOriginY;
    private Visual.Area area;

    /**
     * GameArea constructor.
     * @param context Zen5 Application reference.
     */
    GameArea(ApplicationContext context){
        this.context = context;
        ScreenInfo info = context.getScreenInfo();
        this.Width = info.getWidth();
        this.Height = info.getHeight();

    }

    /**
     * Check if user input leads to movement if so executes it.
     * @param event Keyboard event from Zen5.
     * @param factory Game level factory.
     * @return EventBabaGame that correspond to the game state.
     */
    EventBabaGame checkMovement(Event event, EntityFactory factory){
        EventBabaGame ev;
        ev = EventBabaGame.Good;
        DirectionEnum dir;

        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {

//            ArrayList<Element> list = new ArrayList<>();
            dir = DirectionEnum.valueOf(event.getKey().name());
            
            EventBabaGame turnResult = lvl.moveProp(factory, PropertyEnum.You, dir);
            
            lvl.removeFromToDestroy();
            
            
            return turnResult;
           
        }
        return EventBabaGame.Good;
    }

    /**
     * Sets level size.
     */
    public void setSize(){
        int lvlY = lvl.getPlateau().length;
        int lvlX = lvl.getPlateau()[0].length;
        area = new Visual.Area(this.Height/ lvlX, this.Width/ lvlY);
        this.boardOriginX = (int)(this.Width / 2) - (area.imagesSize * lvlY /2);
        this.boardOriginY = (int)(this.Height / 2) - (area.imagesSize * lvlX /2);
    }

    /**
     * Run the games loop
     * @param id Actual Level id.
     * @return Returns 1 if level is lost, 2 if it is won.
     */
    public int run(int id){
        int returnVal;
        lvl = new Level(id); // creation lvl
        System.out.println(lvl.getPropertyHashMap());
        
        EventBabaGame ev;

        setSize();
        returnVal = 1;
        ev = EventBabaGame.Good;
        area.RefreshScreen(context,(int)this.Width,(int)this.Height,lvl, boardOriginX, boardOriginY);
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
                    area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginX, boardOriginY);
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
