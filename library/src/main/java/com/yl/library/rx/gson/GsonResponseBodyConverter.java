package com.yl.library.rx.gson;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.socks.library.KLog;
import com.yl.library.bean.HttpResult;
import com.yl.library.rx.HttpCode;
import com.yl.library.rx.exception.ApiException;
import com.yl.library.rx.exception.TokenInvalidException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) {
        try {
            String json = new String(value.bytes());
            KLog.json("json----------", json);
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code");
            if (code == 0) {
                HttpResult h = (HttpResult) adapter.fromJson(json);
                return h.getData();
            } else if (code == 20005) {
                throw new TokenInvalidException();
            } else {
                throw new ApiException("" + code);
            }
        } catch (IOException | JSONException | JsonSyntaxException e) {
            throw new ApiException(HttpCode.CODE_30002.getCode());
        } finally {
            if (value != null) {
                value.close();
            }
        }
    }
}
