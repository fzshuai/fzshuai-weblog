package top.fzshuai.weblog.client;

import javax.websocket.Session;

/**
 * @author fzshuai
 * @date 2022/05/04 11:10
 * @since 1.0
 */
public class WebSocketClient {

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 连接的uri
    private String uri;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
