package com.hao.lib.rx.gson;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.hao.lib.bean.HttpResult;
import com.hao.lib.rx.HttpCode;
import com.hao.lib.rx.exception.ApiException;
import com.socks.library.KLog;

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
            String code = jsonObject.optString("error_code", HttpCode.CODE_10031.getCode());
            if (!"0".equals(code)) {
                throw new ApiException(HttpCode.CODE_10031.getCode());
            }
            String data = jsonObject.optString("result");
            if (TextUtils.isEmpty(data)) {
                throw new ApiException(HttpCode.CODE_10031.getCode());
            }

            HttpResult httpResult = (HttpResult) adapter.fromJson(data);
            return httpResult.getData();

        } catch (IOException | JSONException | JsonSyntaxException e) {
            throw new ApiException(HttpCode.CODE_10031.getCode());

        } finally {
            if (value != null) {
                value.close();
            }
        }
    }
}
