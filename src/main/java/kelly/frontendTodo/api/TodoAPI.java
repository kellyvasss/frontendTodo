package kelly.frontendTodo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kelly.frontendTodo.data.CreateTodoDTO;
import kelly.frontendTodo.data.Todo;
import kelly.frontendTodo.data.UpdateTodoDTO;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static kelly.frontendTodo.api.Payload.createPayload;

public class TodoAPI {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String baseURL = "http://todoapi-env.eba-rgxj6v26.eu-north-1.elasticbeanstalk.com";

    public static List<Todo> getTodods(String searchTerm) throws IOException, ParseException {
        HttpGet get = new HttpGet(baseURL+"?searchTerm="+searchTerm);
        CloseableHttpResponse response = response(get);

        if (response.getCode() != 200) {
            System.out.println("FEL VID GET FÖRFRÅGAN TILL " + get.getRequestUri()+ "\nSTATUS KOD: " + response.getCode());
            return Collections.emptyList();
        }
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Todo> todos = mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<Todo>>() {});
        return todos;
    }

    public static Todo createTodo(CreateTodoDTO dto) throws IOException, ParseException {
        HttpPost post = new HttpPost(baseURL);
        post.setEntity(createPayload(dto));
        CloseableHttpResponse response = response(post);

        if (response.getCode() != 201) {
            throw new RuntimeException("FEL VID SKAPANDET AV NY TODO" + response.getCode());
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        Todo todo = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Todo>() {});
        return todo;
    }

    public static Todo updateTodo(UpdateTodoDTO dto) throws IOException, ParseException {
        HttpPut put = new HttpPut(baseURL+"/update");
        put.setEntity(createPayload(dto));
        CloseableHttpResponse response = response(put);

        if (response.getCode() != 202) {
            throw new RuntimeException("FEL VID UPPDATERING AV TODO " + response.getCode());
        }

        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        Todo todo = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Todo>() {});
        return todo;
    }
    private static CloseableHttpResponse response(ClassicHttpRequest request) throws IOException {
        return httpClient.execute(request);
    }

    public static void deleteTodo(Long id) throws IOException {
        HttpDelete delete = new HttpDelete(baseURL + "/" + id);
        CloseableHttpResponse response = response(delete);

        if (response.getCode() != 202) {
            throw new RuntimeException("FEL VID RADERING AV TODO " + response.getCode());
        }

    }

}
