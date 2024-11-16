package tienda.utils;



import lombok.Data;

@Data
public class ApiMss {

    String message;
    Object data;

    public ApiMss(String message, Object data){
        this.message = message;
        this.data = data;

    }

}