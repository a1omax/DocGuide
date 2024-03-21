package gfl.docguide.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DrugIdAmount {

    @NotEmpty
    Long drugId;

    @Positive
    @NotEmpty
    int amount;
}
