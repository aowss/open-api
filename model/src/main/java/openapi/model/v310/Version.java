package openapi.model.v310;

import javax.validation.constraints.NotNull;

public record Version(@NotNull int major, @NotNull int minor, int patch) {
    public Version(@NotNull String openapi) {
        this(Integer.parseInt(openapi.split(".")[0]), Integer.parseInt(openapi.split(".")[1]), Integer.parseInt(openapi.split(".")[2]));
    }
}