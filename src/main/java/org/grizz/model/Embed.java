package org.grizz.model;

import lombok.Data;
import lombok.experimental.Builder;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class Embed {
    private boolean plus18;
    private String type;
    private String url;

    public EmbedType getType() {
        if(StringUtils.isEmpty(type)) {
            return null;
        }
        return EmbedType.valueOf(type.toUpperCase());
    }
}
