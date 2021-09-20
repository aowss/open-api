package openapi.model.v310;

import javax.validation.constraints.NotNull;

public record Version(@NotNull int major, @NotNull int minor, @NotNull String patch) {
    public Version(@NotNull String openapi) {
        this(Integer.parseInt(openapi.split("\\.")[0]), Integer.parseInt(openapi.split("\\.")[1]), openapi.split("\\.")[2]);
    }
}