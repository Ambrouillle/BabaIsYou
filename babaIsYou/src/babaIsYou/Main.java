package babaIsYou;

import fr.umlv.zen5.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        String path = "Ressources";
        int i = 0;
        if (args.length > 1){
            while (i < args.length){
                switch (args[i]){
                    case "--levels":
                    case "--level":
                        if (i+1 < args.length) {
                            path = args[+1];
                            i+= 1;
                        }
                        else {
                            throw new Exception("Invalid --levels use");
                        }
                        break;
                    default:
                        throw new Exception("Can't Handle the argument" + args[i]);
                }
                i+=1;

            }
        }
        String finalPath = path;
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            try {
				game.run(finalPath,1);
			} catch (IOException e) {
				e.printStackTrace();
			}
            context.exit(0);
        });
    }

}