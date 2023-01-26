package JsonParser;

import java.util.HashMap;

public class JsonTree {
    HashMap<JsonArray, Integer> scopes = new HashMap<JsonArray, Integer>();

    public JsonArray getJsonArray(String keyword){
        for (JsonArray arr : scopes.keySet()){
            if (arr.keyword.equals(keyword)){
                return arr;
            }
        }
        return null;
    }

    public void addElementToScope(JsonElement e, int scope, String keyword, String path){
        boolean s = false;

        for (JsonArray arr : scopes.keySet()){
            if (scopes.get(arr) == scope && keyword.equals(arr.getKeyword()) && arr.path.equals(path)){
                s = true;
                arr.addElement(e);
            }
        }

        if (!s){
            JsonArray newArr = new JsonArray();
            newArr.addElement(e);
            newArr.keyword = keyword;
            newArr.path = path;

            scopes.put(newArr, scope);
        }
    }

    public void addJsonArray(JsonArray arr, int scope){
        scopes.put(arr, scope);
    }

    public JsonArray getPath(String path){
        return null;
    }

    public void printTree(){
        System.out.println("Directory Paths:");
        System.out.println("---------------");
        for (JsonArray arr :scopes.keySet()){
            System.out.println(arr.getPath());
        }
        System.out.println("All Values + Paths:");
        System.out.println("---------------");
        for (JsonArray arr : scopes.keySet()){
            for  (JsonElement e : arr.getElements()){
                String n = "";

                for (int i = 0; i <scopes.get(arr); i++){
                    n += "---";
                }

                System.out.println(n + e.getPath() + ": " + e.getValue() + " | Scope: " + scopes.get(arr) + " | Type: " + e.value.getClass().getSimpleName());
            }
        }
    }


    //TODO Optimize Search later by using presence of path keywords
    public JsonElement getElementByPath(String inpPath){
        for (JsonArray arr : scopes.keySet()){
            for  (JsonElement e : arr.getElements()){
                if (e.getPath().equals(inpPath)){
                    return e;
                }
            }
        }
        return null;
    }

    public JsonArray findItemOfDescription(String[] keywords, Object[] values){
        if (keywords.length != values.length){
            System.out.println("ERROR: Length of keywords does not equal length of values");
            return null;
        }

        JsonArray a = new JsonArray();

        for (JsonArray arr : scopes.keySet()){
            if (arr.getElements().toArray().length == keywords.length){
                Object[] eArray = arr.getElements().toArray();
                boolean s = true;
                    for (int i = 0; i < eArray.length; i++){
                        JsonElement e = (JsonElement) eArray[i];
                        if (!e.getName().equals(keywords[i]) || !e.getValue().equals(values[i])){
                            s = false;
                            break;
                        }
                    }

                    if (s){
                        return arr;
                    }
                }
            }
        return null;
    }

    public JsonArray getPathAsJsonArray(String path){
        return null;
    }
}
