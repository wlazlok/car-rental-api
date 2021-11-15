package karol.wlazlo.commons.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import karol.wlazlo.model.AppUserResponse.AppUserResponse;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import karol.wlazlo.model.Security.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class UploadImageService {

    public ProductItemResponse upload(HttpServletRequest servletRequest, Long productId) throws ServletException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();

        HttpPost httpPost = new HttpPost(URI.create("http://localhost:8911/ds/update/image/upload/" + productId));

        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        Part part = servletRequest.getPart("file");

        multipartEntity.addPart(part.getName(), new InputStreamBody(part.getInputStream(), java.net.URLEncoder.encode(part.getSubmittedFileName(), StandardCharsets.UTF_8)));

        httpPost.setEntity(multipartEntity.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);

        return new ObjectMapper().readValue(response.getEntity().getContent(), ProductItemResponse.class);
    }

    public AppUserResponse uploadAvatar(HttpServletRequest servletRequest, Long userId) throws ServletException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();

        HttpPost httpPost = new HttpPost(URI.create("http://localhost:8911/ds/update/user/avatar/" + userId));

        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        Part part = servletRequest.getPart("file");

        multipartEntity.addPart(part.getName(), new InputStreamBody(part.getInputStream(), java.net.URLEncoder.encode(part.getSubmittedFileName(), StandardCharsets.UTF_8)));

        httpPost.setEntity(multipartEntity.build());
        CloseableHttpResponse response = httpClient.execute(httpPost);

        return new ObjectMapper().readValue(response.getEntity().getContent(), AppUserResponse.class);
    }
}
