package babaIsYou;

import fr.umlv.zen5.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static String parse_arguments(ArrayList commands, String[] args) throws Exception {
        int i = 0;
        String path = "Ressources";
        if (args.length > 1){
            while (i < args.length){
                switch (args[i]){
                    case "--levels":
                    case "--level":
                        if (i+1 < args.length) {
                            path = args[i+1];
                            i+= 1;
                        }
                        else {
                            throw new Exception("Invalid --levels use");
                        }
                        break;
                    case "--execute":
                        if (i+3 < args.length) {
                            commands.add(args[i+1]+"."+args[i+2]+"."+args[i+3]);
                            i+= 3;
                        }
                        else {
                            throw new Exception("Invalid --execute use");
                        }
                        break;
                    default:
                        throw new Exception("Can't Handle the argument" + args[i]);
                }
                i+=1;

            }
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        String path;
        ArrayList<String> commands = new ArrayList();
        path = parse_arguments(commands, args);
        String finalPath = path;
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            try {
				game.run(finalPath,6, commands);
			} catch (IOException e) {
				e.printStackTrace();
			}
            context.exit(0);
        });
    }

}