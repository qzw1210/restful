package cn.ourfuture.restful.controller;

import cn.ourfuture.restful.model.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quzhiwen on 2016/4/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * get请求，获取所有用户
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = new ArrayList<User>();
        userList.add(new User("1","Ramesh","西安",26));
        userList.add(new User("2","Deepesh","北京",27));
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    /**
     * post请求，获取用户
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> addUser(@RequestBody String message){
        if(message == null) throw new IllegalArgumentException("message 不能为 null");
        System.out.println(message);

        List<User> userList = new ArrayList<User>();
        userList.add(new User("3","Ramesh", "深圳",24));
        userList.add(new User("2","Deepesh","上海",28));
        User findUser = null;
        if(findUser == null) {
            userList.add(new User("1","Ramesh", "山东",24));
            return new ResponseEntity<>( HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> find(@PathVariable String id) throws IOException {
        List<User> userList = new ArrayList<User>();
        userList.add(new User("3","Ramesh", "深圳",24));
        userList.add(new User("2","Deepesh","上海",28));
        User findUser = null;
        for (User user:userList) {
            if(user.getUserId().equals(id)){
               findUser = user;
            }
        }
        if(findUser == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(findUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody String userL) throws IOException {

        if(userL == null) throw new IllegalArgumentException("group 不能为 null");
        List<User> userList = new ArrayList<User>();
        userList.add(new User("3","Ramesh", "深圳",24));
        userList.add(new User("2","Deepesh","上海",28));
        User former = null;
        for (User user:userList) {
            if(user.getUserId().equals(id)){
               user.setAddress("dddd");
                user.setAge(29);
                user.setUserId("2");
                user.setUsername("zhangsan");
                former = user;
            }
        }
        if(former == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        System.out.println("更新数据成功!");
        return new ResponseEntity<>(former, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(String recursive, @PathVariable String id) throws IOException {
        boolean isRecursive = true;
        if("false".equals(recursive) || "0".equals(recursive)) isRecursive = false;

        List<User> userList = new ArrayList<User>();
        userList.add(new User("3","Ramesh", "深圳",24));
        userList.add(new User("2","Deepesh","上海",28));

        User former = null;
        for (User user:userList) {
            if(user.getUserId().equals(id)){
                former = user;
            }
        }
        if(former == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        /**
         * 相关条件判断
         */
        if(isRecursive)
            System.out.println("删除1");
        else
            System.out.println("删除2");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
