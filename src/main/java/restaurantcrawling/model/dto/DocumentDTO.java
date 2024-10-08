package restaurantcrawling.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DocumentDTO {
    private Long id;
    private String address_name;
    private String category_group_code;
    private String category_group_name;
    private String category_name;
    //    private int distance;
    private String phone;
    private String place_name;
    //    private String place_url;
//    private String road_address_name;
    private double x;
    private double y;

    @Override
    public String toString() {

//        String category = category_name.split(" > ")[1];

        return "DocsDTO{" +
                "id=" + id +
                ", address_name='" + address_name + '\'' +
                ", category_group_code='" + category_group_code + '\'' +
                ", category_group_name='" + category_group_name + '\'' +
                ", category_name='" + category_name + '\'' +
//                ", distance=" + distance +
                ", phone='" + phone + '\'' +
                ", place_name='" + place_name + '\'' +
//                ", place_url='" + place_url + '\'' +
//                ", road_address_name='" + road_address_name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
