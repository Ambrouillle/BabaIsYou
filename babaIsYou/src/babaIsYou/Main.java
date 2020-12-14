package babaIsYou;

import fr.umlv.zen5.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            game.run(1);
            context.exit(0);
        });
    }

}