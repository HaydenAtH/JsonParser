package JsonParser;

public class JsonElement {
    String name;
    Object value;
    String path;

    public JsonElement(String name,Object value){
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString(){
        return (this.path + ": " + this.value);
    }
}
