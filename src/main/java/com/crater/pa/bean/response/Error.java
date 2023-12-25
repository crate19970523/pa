package com.crater.pa.bean.response;

public record Error(boolean isSuccess, String errorMessage, String errorDetail) {

}