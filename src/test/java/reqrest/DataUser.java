package reqrest;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataUser {

    private int id;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;

}
