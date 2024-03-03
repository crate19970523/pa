package com.crater.pa.bean.response;

import java.util.List;

public record GetAccountingRegistrantResponse (Error error, List<AccountRegistrantInfo> accountRegistrantInfos) {

}
