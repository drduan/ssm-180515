package utils.json;

public class JsonTData<T> extends JsonObject {

    private T data;

    public JsonTData() {

    }

    public JsonTData(T data) {
        this.data = data;
        this.success = true;
    }

    public JsonTData(T data, String message) {
        this.data = data;
        this.success = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
