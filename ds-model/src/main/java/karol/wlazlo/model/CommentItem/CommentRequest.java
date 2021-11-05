package karol.wlazlo.model.CommentItem;

import karol.wlazlo.model.Security.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long productId;

    private String message;

    private AppUser user;
}
