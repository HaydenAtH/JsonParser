package JsonParser;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonArray {
    public String path;
    public String keyword;
    ArrayList<JsonElement> elements = new ArrayList<>();

    public ArrayList<JsonElement> getElements() {
        return (ArrayList<JsonElement>) elements;
    }

    public boolean hasElement(JsonElement e){
        return elements.contains(e);
    }

    public void addElement(JsonElement e){
        elements.add(e);
    }

    public String getPath(){
        return this.path;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKeyword(){
        return this.keyword;
    }
}
