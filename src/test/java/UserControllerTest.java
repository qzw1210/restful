import cn.ourfuture.restful.model.User;
import net.sf.json.JSONObject;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by quzhiwen on 2016/4/28.
 */
public class UserControllerTest {
    private static final String User_SERVICE_URL = "http://192.168.8.51:8089/restful/user/";

    /**
     * 测试获取所有用户、单个用户信息
     */
    @Test
    public void testGet() {
        getUsers("");
    }
    @Test
    public void testPost() {
        User user = new User("12","lisi","eee",26);
        JSONObject jsonObject = JSONObject.fromObject(user);
        postUser(jsonObject.toString());
    }
    @Test
    public void testDelete() throws Exception {
        deleteUser("2");
    }
    @Test
    public void testPut() throws Exception {
        User user = new User("2","zhangsan","dddd",29);
        JSONObject jsonObject = JSONObject.fromObject(user);
        putUser("2",jsonObject.toString());
    }



    public void postUser( String json) {
        System.out.println("\n### POST User -> ");
        HttpURLConnection connection;
        try {
            connection = connect(User_SERVICE_URL);
            System.out.println(connection);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(
                    json.toString().getBytes("UTF-8"));
            JAXBContext jaxbContext = JAXBContext
                    .newInstance(UserControllerTest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            System.out.println("\n### POST User RESPONSE");
            System.out.println("Status: " + connection.getResponseCode() + " "
                    + connection.getResponseMessage());
            System.out.println("Location: "
                    + connection.getHeaderField("Location"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void putUser(String id, String json) {
        System.out.println("\n### POST User -> ");
        HttpURLConnection connection;
        try {
            connection = connect(User_SERVICE_URL+id);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("PUT");
            connection.getOutputStream().write(
                    json.toString().getBytes("UTF-8"));
            JAXBContext jaxbContext = JAXBContext
                    .newInstance(UserControllerTest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            System.out.println("Status: " + connection.getResponseCode() + " "
                    + connection.getResponseMessage());
            System.out.println("Location: "
                    + connection.getHeaderField("Location"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void getUsers(String id){
        try {
            String url = null;
            if (id != null && !"".equals(id)) {
                url = User_SERVICE_URL  + id;
            } else {
                url = User_SERVICE_URL;
            }
            System.out.println("\n### GET User WITH ID " + url);
            HttpURLConnection connection = connect(url);
            connection.setDoInput(true);
            InputStream stream = connection.getResponseCode() / 100 == 2 ? connection
                    .getInputStream() : connection.getErrorStream();
            System.out.println("Status: " + connection.getResponseCode() + " "
                    + connection.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteUser(String id) throws Exception {
        try {
            String url = User_SERVICE_URL + id;
            System.out.println("\n### DELETE User WITH ID " + id + " FROM URL "
                    + url);
            HttpURLConnection connection = (HttpURLConnection) connect(url);
            connection.setRequestMethod("DELETE");
            connection.setDoInput(true);
            System.out.println("Status: " + connection.getResponseCode() + " "
                    + connection.getResponseMessage());
            if (connection.getResponseCode() / 100 != 2) {
                // System.out.println(IOUtils.toString(connection.getErrorStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpURLConnection connect(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url)
                .openConnection();
        return connection;
    }

}