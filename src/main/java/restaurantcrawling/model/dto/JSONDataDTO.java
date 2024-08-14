package restaurantcrawling.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class JSONDataDTO {
    private Object meta;
    private List<DocumentDTO> documents = new ArrayList<>();
}
