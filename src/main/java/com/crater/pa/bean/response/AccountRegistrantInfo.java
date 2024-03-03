package com.crater.pa.bean.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AccountRegistrantInfo(@Schema(description = "記帳人名") String registrantName,
                                    @Schema(description = "記帳人 ID") Long registrantId) {
}
