package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.service.IHttpManager;
import com.tradekraftcollective.microservice.utilities.UrlUtil;
import com.tradekraftcollective.microservice.utilities.UtilProtos;
import com.tradekraftcollective.microservice.utilities.UtilProtos.Url;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandonfeist on 11/16/17.
 */
@Slf4j
public class HttpManager implements IHttpManager {
    private HttpClientConnectionManager connectionManager = null;

    public HttpManager(Builder builder) {
        if (builder.connectionManager != null) {
            connectionManager = builder.connectionManager;
        } else {
            connectionManager = new PoolingHttpClientConnectionManager();
        }
    }

    @Override
    public String get(Url url) throws WebApiException, IOException {
        log.info("Preparing GET request to external API [{}]", url.getPath());

        assert (url != null);

        final String uri = UrlUtil.assemble(url);
        final HttpGet method = new HttpGet(uri);

        for (Url.Parameter header : url.getHeaderParametersList()) {
            method.setHeader(header.getName(), header.getValue());
        }

        return execute(method);
    }

    @Override
    public String post(UtilProtos.Url url) throws IOException, WebApiException {
        log.info("Preparing POST request to external API [{}]", url.getPath());

        assert (url != null);

        final String uri = UrlUtil.assemble(url);
        final HttpPost method = new HttpPost(uri);

        for (Url.Parameter header : url.getHeaderParametersList()) {
            method.setHeader(header.getName(), header.getValue());
        }

        if (url.hasJsonBody()) {
            StringEntity stringEntity = new StringEntity(
                    url.getJsonBody(),
                    ContentType.APPLICATION_JSON);
            method.setEntity(stringEntity);
        } else {
            method.setEntity(new UrlEncodedFormEntity(getBodyParametersAsNamedValuePairArray(url)));
        }

        return execute(method);
    }

    @Override
    public String put(UtilProtos.Url url) throws IOException, WebApiException {
        log.info("Preparing PUT request to external API [{}]", url.getPath());

        assert (url != null);

        final String uri = UrlUtil.assemble(url);
        final HttpPut method = new HttpPut(uri);

        for (Url.Parameter header : url.getHeaderParametersList()) {
            method.setHeader(header.getName(), header.getValue());
        }

        if (url.hasJsonBody()) {
            StringEntity stringEntity = new StringEntity(
                    url.getJsonBody(),
                    ContentType.APPLICATION_JSON);
            method.setEntity(stringEntity);
        } else {
            method.setEntity(new UrlEncodedFormEntity(getBodyParametersAsNamedValuePairArray(url)));
        }

        return execute(method);
    }

    @Override
    public String delete(UtilProtos.Url url) throws IOException, WebApiException {
        log.info("Preparing DELETE request to external API [{}]", url.getPath());

        assert (url != null);

        final String uri = UrlUtil.assemble(url);
        final HttpDelete method = new HttpDelete(uri);

        for (Url.Parameter header : url.getHeaderParametersList()) {
            method.setHeader(header.getName(), header.getValue());
        }

        return execute(method);
    }

    private List<NameValuePair> getBodyParametersAsNamedValuePairArray(Url url) {
        List<NameValuePair> out = new ArrayList<>();

        for (Url.Parameter parameter : url.getBodyParametersList()) {
            if (parameter.hasName() && parameter.hasValue()) {
                out.add(new BasicNameValuePair(parameter.getName(), parameter.getValue().toString()));
            }
        }

        return out;
    }

    private String execute(HttpRequestBase method) throws WebApiException, IOException {
        log.info("Executing request to external API");

        final ConnectionConfig connectionConfig = ConnectionConfig
                .custom()
                .setCharset(Charset.forName("UTF-8"))
                .build();
        final RequestConfig requestConfig = RequestConfig
                .custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultConnectionConfig(connectionConfig)
                .setDefaultRequestConfig(requestConfig)
                .build();

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(method);

            handleErrorStatusCode(httpResponse);

            String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

            handleErrorResponseBody(responseBody);

            return responseBody;
        } catch (IOException e) {
            throw new IOException();
        } finally {
            method.releaseConnection();
        }
    }

    private void handleErrorStatusCode(CloseableHttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode >= 400 && statusCode < 500) {
            throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_BAD_REQUEST, "");
        }
        if (statusCode >= 500) {
            throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_SERVER_ERROR, "");
        }

    }

    private void handleErrorResponseBody(String responseBody) throws WebApiException {
        if (responseBody == null) {
            throw new ServiceException(ErrorCode.EXTERNAL_SERVICE_EMPTY_RESPONSE, "");
        }

        if (!responseBody.equals("") && responseBody.startsWith("{")) {
            final JSONObject jsonObject = JSONObject.fromObject(responseBody);

            if (jsonObject.has("error")) {
                throw new WebApiException(jsonObject.getString("error"));
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PoolingHttpClientConnectionManager connectionManager = null;
        public Builder() {}
        public HttpManager build() {
            return new HttpManager(this);
        }
    }
}
