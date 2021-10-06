package karol.wlazlo.model.CardItem;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardItemResponse extends AbstractResponse {

    private List<CardItem> cardItems = new ArrayList<>();
}
