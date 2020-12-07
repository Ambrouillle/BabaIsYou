package babaIsYou;

import babaIsYou.entity.Element;
import babaIsYou.entity.Entity;
import babaIsYou.entity.EntityFactory;
import babaIsYou.entity.Property;
import babaIsYou.entity.Text;
import babaIsYou.entity.entityEnum.DirectionEnum;
import babaIsYou.entity.entityEnum.ElementEnum;
import babaIsYou.entity.entityEnum.PropertyEnum;

import fr.umlv.zen5.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            game.run();
        });
    }

}