package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private String login;
    private String pass;
    private boolean statusLogin=false;
    private boolean isOnline;
    private Set<User> usersList=new HashSet<>();

    public User() {
    }


    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public boolean getStatusLogin() {
        return statusLogin;
    }

    public Set<User> getUsersList() throws IOException {
        dowloadUserList();
        return usersList;
    }

    public boolean isOnline() {
        return isOnline;
    }


    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void loginAccount() throws IOException {
        URL obj = new URL(Utils.getURL()+"/login");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try(OutputStream os = conn.getOutputStream()){
            String json=toJSON();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            System.out.println(conn.getResponseCode());

            if(conn.getResponseCode()==100){
                System.out.println("Your login successful");
                statusLogin=true;
            }else System.out.println("You entered incorretc login or password");
        }
    }

  public void createAccount() throws IOException {

      URL obj = new URL(Utils.getURL()+"/login");
      HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

      conn.setRequestMethod("POST");
      conn.setDoOutput(true);

      try(OutputStream os = conn.getOutputStream()){
          String json=toJSON();
          byte[]buf=json.getBytes(StandardCharsets.UTF_8);
          os.write(buf);

          System.out.println("Код ответа"+conn.getResponseCode()); //удалить

          if(conn.getResponseCode()==200){
              System.out.println("Your account created");
              statusLogin=true;
          }else System.out.println("Try use another login");
      }
  }

  public String toJSON(){
      Gson gson=new GsonBuilder().create();
      return  gson.toJson(this);
  }
  public static User fromJSON(String s){
        Gson gson=new GsonBuilder().create();
    return gson.fromJson(s,User.class);
  }

  public void dowloadUserList() throws IOException {

      URL url = new URL(Utils.getURL() + "/getUsers");
      HttpURLConnection http = (HttpURLConnection) url.openConnection();


      try (InputStream is = http.getInputStream()) {
          byte[] buf = responseBodyToArray(is);
          String strBuf = new String(buf, StandardCharsets.UTF_8);
          Gson gson = new GsonBuilder().create();
          Set<User> list = gson.fromJson(strBuf, Set.class);
      }
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(pass, user.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, pass);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          byte[] buf = new byte[10240];
          int r;

          do {
              r = is.read(buf);
              if (r > 0) bos.write(buf, 0, r);
          } while (r != -1);

          return bos.toByteArray();
      }
}


