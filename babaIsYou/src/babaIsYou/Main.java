package babaIsYou;

import fr.umlv.zen5.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            try {
				game.run(54);
			} catch (IOException e) {
				e.printStackTrace();
			}
            context.exit(0);
        });
    }

}