package com.crater.pa.bean.response;

import java.util.List;

public record GetCatalogResponse(Error error, List<CatalogInfo> catalogInfos) {

}
