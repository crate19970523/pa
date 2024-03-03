package com.crater.pa.bean.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetAccountingRegistrantResponse (@Schema(description = "錯誤資訊") Error error, @Schema(description = "記帳使用者資訊") List<AccountRegistrantInfo> accountRegistrantInfos) {

}
