package JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JsonParser {
    public static int currentScope = 0;


    public static JsonTree parseJson(File file) throws FileNotFoundException {
        currentScope = 0;
        int debugKeywords = 0;
        Scanner scnr = null;
        String str = "";
        JsonTree tree = new JsonTree();

        String activePath ="";

        String activeKeyword = " ";

        try {
            scnr = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Input file for JSON PARSER not found");
        }

        while (scnr.hasNext()) {
            str += scnr.next();
        }

        System.out.println("Raw Json: " + str);
        System.out.println("---------------------------");

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == '"' && i < str.length()){

                String n = "";

                int x = i + 1;
                debugKeywords += 1;

                while (str.charAt(x) != '"'){
                    if (x < str.length()){
                        n += String.valueOf(str.charAt(x));

                        if (str.length() == x + 1){
                            break;
                        }

                        x++;
                    }
                }

                x += 2;

                ArrayList<Character> stopIdentifiers = new ArrayList<Character>();
                stopIdentifiers.add('}');
                stopIdentifiers.add(',');

                if (x >= str.length()){
                    break;
                }

                if (str.charAt(x) == '{' || str.charAt(x) == '['){
                    currentScope += 1;
                    activeKeyword = n;

                    if (currentScope == 0){
                        activePath += n;
                    }else{
                        activePath += "/" + n;
                    }

                    i = x;
                    continue;
                }

                if (str.charAt(x) == '"'){
                    x += 1;
                    stopIdentifiers.add('"');
                }

                String value = "";

                while (!stopIdentifiers.contains(str.charAt(x))){
                        value += String.valueOf(str.charAt(x));

                        if (str.length() == x + 1){
                            break;
                        }
                        x++;
                }

                Object obj = sortValue(value);

                JsonElement e = new JsonElement(n, obj);
                e.setPath(activePath + "/" + n);
                tree.addElementToScope(e, currentScope, activeKeyword, activePath);

                ArrayList<Character> scopeDecreasers = new ArrayList<Character>();
                scopeDecreasers.add('}');
                scopeDecreasers.add(']');

                if ( scopeDecreasers.contains(str.charAt(x)) || scopeDecreasers.contains(str.charAt(x - 1)) || scopeDecreasers.contains(str.charAt(x + 1)) || scopeDecreasers.contains(str.charAt(x + 2)) || scopeDecreasers.contains(str.charAt(x - 2))){
                    currentScope -= 1;

                    String v = "";
                    int g = activePath.length();

                    for (int f = activePath.length() - 1; f > 0; f--){
                        if (activePath.charAt(f) != '/'){

                        }else{
                            g = f;
                        }
                    }

                    for (int d = 0; d < g; d++){
                        v += String.valueOf(activePath.charAt(d));
                    }

                    activePath = v;
                }

                i = x;
            }
        }

        System.out.println("Json Parsed Successfully | Keywords: " + debugKeywords);
        return tree;
    }



    // Assigns proper type to a string input and returns obj
    public static Object sortValue(String val){
        boolean s = false;
        Object obj = null;

        while (true){
            //System.out.println(val);
            try {
                obj = Integer.valueOf(val);
                s = true;
            } catch (NumberFormatException e){
                //System.out.println(e);
                s = false;

            }

            if (!s){
                try {
                    obj = Double.valueOf(val);
                    s = true;
                } catch (NumberFormatException e){
                    //System.out.println(e);
                    s = false;

                }

                if (!s){
                    if (val.toLowerCase().equals("true")){
                        obj = true;
                        break;
                    }else if (val.toLowerCase().equals("false")){
                        obj = false;
                        break;
                    }else{

                        obj = String.valueOf(val);
                        break;
                    }
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        return obj;
    }


}
