package babaIsYou;

import fr.umlv.zen5.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static String parse_arguments(ArrayList<String> commands, String[] args) throws Exception {
        int i = 0;
        String path = "Levels";
        if (args.length > 1){
            while (i < args.length){
                if ("--levels".equals(args[i]) || "--level".equals(args[i])) {
                    if (i + 1 < args.length) {
                        path = args[i + 1];
                        i += 1;
                    } else {
                        throw new Exception("Invalid --levels use");
                    }
                } else if ("--execute".equals(args[i])) {
                    if (i + 3 < args.length) {
                        commands.add(args[i + 1] + "." + args[i + 2] + "." + args[i + 3]);
                        i += 3;
                    } else {
                        throw new Exception("Invalid --execute use");
                    }
                } else {
                    throw new Exception("Can't Handle the argument" + args[i]);
                }
                i+=1;

            }
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        String path;
        ArrayList<String> commands = new ArrayList<>();
        path = parse_arguments(commands, args);
        String finalPath = path;
        Application.run(Color.BLACK, context -> {
            GameArea game = new GameArea(context);
            try {
				game.run(finalPath,0, commands);
			} catch (IOException e) {
				e.printStackTrace();
			}
            context.exit(0);
        });
    }

}