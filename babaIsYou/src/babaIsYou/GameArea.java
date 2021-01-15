package babaIsYou;

import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.EventBabaGame;
import babaIsYou.entity.entityEnum.PropertyEnum;
import fr.umlv.zen5.*;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameArea {
    private final ApplicationContext context;
    private final float Width;
    private final float Height;
    private Level lvl;
    private int boardOriginx;
    private int boardOriginy;
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
    EventBabaGame checkMovement(Event event, EntityFactory factory) throws IOException{
        DirectionEnum dir;

        if (List.of("UP","DOWN","LEFT","RIGHT").contains(event.getKey().name())) {
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
        int lvly = lvl.getPlateau().length;
        int lvlx = lvl.getPlateau()[0].length;
        area = new Visual.Area(this.Height/ lvlx, this.Width/ lvly);
        this.boardOriginx = (int)(this.Width / 2) - (area.imagesSize * lvly /2);
        this.boardOriginy = (int)(this.Height / 2) - (area.imagesSize * lvlx /2);
    }

    /**
     * Execute cheat commands(Cheating is bad be ashamed).
      * @param commands List of commands you are shamefully running.
     */
    private void execute(ArrayList<String> commands){
        for(String c : commands){
            String[] command = c.split("\\.");
            try {
                lvl.addPropInMap(PropertyEnum.valueOf(command[2]), ElementEnum.valueOf(command[0]).getElemID());
            }catch (Exception e){
                System.out.println("Wrong arguments to  --execute");
                System.out.println(e.getMessage());

            }
        }
    }

    /**
     * Run the games loop
     * @param id Actual Level id.
     * @param commands Commands extracted from arguments.
     * @return Returns 1 if level is lost, 2 if it is won.
     */
    public int run(String path, int id, ArrayList<String> commands) throws IOException{
        int returnVal;
        do {
            lvl = new Level(path,id); // creation lvl
            EventBabaGame ev;
            setSize();
            returnVal = 1;
            execute(commands);
            ev = EventBabaGame.Good;
            area.ClearScreen(context,(int) this.Width, (int) this.Height);
            area.DrawPLayableFIeld(context, boardOriginx, boardOriginy, lvl);
            area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginx, boardOriginy);
            for (; ; ) {
                Event event = context.pollOrWaitEvent(10);
                if (event == null) {  // no event
                    continue;
                }
                Action action = event.getAction();
                if (event.getKey() == KeyboardKey.L) {
                    returnVal = 3;
                    break;
                }
                if (action == Action.KEY_PRESSED) {
                    ev = checkMovement(event, lvl.getFactory());
                    if (lvl.isLost(lvl.getFactory()) == EventBabaGame.Defeat) {
                        ev = EventBabaGame.Defeat;
                    }
                    area.RefreshScreen(context, (int) this.Width, (int) this.Height, lvl, boardOriginx, boardOriginy);
                }
                if (ev == EventBabaGame.Defeat) {
                    area.Loose(context, (int) this.Width, (int) this.Height);
                    break;
                } else if (ev == EventBabaGame.Win) {
                    returnVal = 2;
                    area.Win(context, (int) this.Width, (int) this.Height);
                    break;
                }
            }
            id += 1;
        }while (returnVal != 3);
        return returnVal;
    }
}