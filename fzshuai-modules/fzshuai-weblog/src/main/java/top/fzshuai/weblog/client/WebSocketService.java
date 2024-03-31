package top.fzshuai.weblog.client;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import top.fzshuai.weblog.domain.ChatRecord;
import top.fzshuai.weblog.domain.dto.ChatRecordDto;
import top.fzshuai.weblog.domain.dto.RecallMessageDto;
import top.fzshuai.weblog.domain.dto.WebsocketMessageDto;
import top.fzshuai.weblog.domain.vo.VoiceVo;
import top.fzshuai.weblog.mapper.ChatRecordMapper;
import top.fzshuai.weblog.utils.HTMLUtils;
import top.fzshuai.common.utils.BeanCopyUtils;
import top.fzshuai.common.utils.ServletUtils;
import top.fzshuai.common.utils.ip.AddressUtils;
import top.fzshuai.system.service.ISysOssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import static top.fzshuai.weblog.enums.ChatTypeEnum.*;

/**
 * @author fzshuai
 * @date 2022/05/04 11:10
 * @since 1.0
 */
@ServerEndpoint(value = "/websocket/{userName}", configurator = WebSocketService.ChatConfigurator.class)
@Component
public class WebSocketService {

    @Resource
    private ISysOssService sysOssService;

    @Autowired
    public void setChatRecordMapper(ChatRecordMapper chatRecordMapper) {
        WebSocketService.chatRecordMapper = chatRecordMapper;
    }

    private static ChatRecordMapper chatRecordMapper;

    /**
     * 用户session集合
     */
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收userName
     */
    private String userName = "";

    /**
     * 获取客户端真实ip
     */
    public static class ChatConfigurator extends ServerEndpointConfig.Configurator {

        public static String HEADER_NAME = "X-Real-IP";

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            try {
                String clientIP = ServletUtils.getClientIP();
                sec.getUserProperties().put(HEADER_NAME, clientIP);
            } catch (Exception e) {
                sec.getUserProperties().put(HEADER_NAME, "未知ip");
            }

        }
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName, EndpointConfig endpointConfig) throws IOException {
        /**
         * 判断是否为游客，是游客则给游客命名为其ip地址为唯一标识
         */
        if (userName.equals("null")) {
            userName = endpointConfig.getUserProperties().get(ChatConfigurator.HEADER_NAME).toString();
            WebSocketClient client = new WebSocketClient();
            if (!webSocketMap.containsKey(userName)) {
                addOnlineCount(); // 在线数 +1
            }
        }
        /**
         * 初始化登录用户
         */
        if (WebSocketService.onlineCount == 0) {
            addOnlineCount(); // 在线数 +1
        }
        this.session = session;
        webSocketSet.add(this);
        // 更新在线人数
        updateOnlineCount();
        this.userName = userName;
        WebSocketClient client = new WebSocketClient();
        client.setSession(session);
        client.setUri(session.getRequestURI().toString());
        webSocketMap.put(userName, client);
        log.info("用户连接:" + userName + ",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("来自后台的反馈：连接成功");
        } catch (IOException e) {
            log.error("用户:" + userName + ",网络异常!!!!!!");
        }

        // 加载历史聊天记录
        ChatRecordDto chatRecordDTO = listChartRecords(endpointConfig);
        // 发送消息
        WebsocketMessageDto messageDTO = WebsocketMessageDto.builder()
            .type(HISTORY_RECORD.getType())
            .data(chatRecordDTO)
            .build();
        synchronized (session) {
            session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
            System.out.println("发送成功" + JSON.toJSONString(messageDTO));
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
        updateOnlineCount();
        if (webSocketMap.containsKey(userName)) {
            webSocketMap.remove(userName);
            if (webSocketMap.size() >= 0) {
                // 从set中删除
                subOnlineCount();
            }
        }
        log.info("----------------------------------------------------------------------------");
        log.info(userName + "用户退出,当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        WebsocketMessageDto messageDTO = JSON.parseObject(message, WebsocketMessageDto.class);
        switch (Objects.requireNonNull(getChatType(messageDTO.getType()))) {
            case SEND_MESSAGE:
                // 发送消息
                ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
                // 过滤html标签
                chatRecord.setContent(HTMLUtils.deleteTag(chatRecord.getContent()));
                // 设置ip地址
                // 设置时间
                chatRecord.setCreateTime(new Date());
                chatRecordMapper.insert(chatRecord);
                messageDTO.setData(chatRecord);
                // 广播消息
                broadcastMessage(messageDTO);
                break;
            case RECALL_MESSAGE:
                // 撤回消息
                RecallMessageDto recallMessage = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), RecallMessageDto.class);
                // 删除记录
                chatRecordMapper.deleteById(recallMessage.getMessageId());
                // 广播消息
                broadcastMessage(messageDTO);
                break;
            case HEART_BEAT:
                // 心跳消息
                messageDTO.setData("pong");
                session.getBasicRemote().sendText(JSON.toJSONString(JSON.toJSONString(messageDTO)));
            default:
                break;
        }
    }

    /**
     * 广播消息
     *
     * @param messageDTO 消息dto
     * @throws IOException io异常
     */
    private void broadcastMessage(WebsocketMessageDto messageDTO) throws IOException {
        for (WebSocketService webSocketService : webSocketSet) {
            synchronized (webSocketService.session) {
                webSocketService.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
                System.out.println("广播的消息为：" + messageDTO.getData().toString());
            }
        }
    }

    /**
     * 更新在线人数
     *
     * @throws IOException io异常
     */
    public void updateOnlineCount() throws IOException {
        // 获取当前在线人数
        WebsocketMessageDto messageDTO = WebsocketMessageDto.builder()
            .type(ONLINE_COUNT.getType())
            .data(getOnlineCount())
            .build();
        // 广播消息
        System.out.println("当前在线人数是" + getOnlineCount());
        broadcastMessage(messageDTO);
    }

    /**
     * 发送语音
     *
     * @param voiceVo 语音路径
     */
    public void sendVoice(VoiceVo voiceVo) {
        // 上传语音文件
        String content = sysOssService.upload(voiceVo.getFile()).getUrl();
        voiceVo.setContent(content);
        // 保存记录
        ChatRecord chatRecord = BeanCopyUtils.copy(voiceVo, ChatRecord.class);
        chatRecord.setCreateTime(new Date());
        chatRecordMapper.insert(chatRecord);
        // 发送消息
        WebsocketMessageDto messageDTO = WebsocketMessageDto.builder()
            .type(VOICE_MESSAGE.getType())
            .data(chatRecord)
            .build();
        // 广播消息
        try {
            broadcastMessage(messageDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载历史聊天记录
     *
     * @return 加载历史聊天记录
     */
    private ChatRecordDto listChartRecords(EndpointConfig endpointConfig) {
        // 获取聊天历史记录
        List<ChatRecord> chatRecordList = chatRecordMapper.selectList(new LambdaQueryWrapper<ChatRecord>()
            .ge(ChatRecord::getCreateTime, DateUtil.offsetHour(new Date(), -12)));
        // 获取当前用户ip
        String ipAddress = endpointConfig.getUserProperties().get(ChatConfigurator.HEADER_NAME).toString();
        return ChatRecordDto.builder()
            .chatRecordList(chatRecordList)
            .ipAddress(ipAddress)
            .ipSource(AddressUtils.getRealAddressByIP(ipAddress))
            .build();
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userName + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 连接服务器成功后主动推送
     */
    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 向指定客户端发送消息
     *
     * @param userName
     * @param message
     */
    public static void sendMessage(String userName, String message) {
        try {
            WebSocketClient webSocketClient = webSocketMap.get(userName);
            if (webSocketClient != null) {
                webSocketClient.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketService.onlineCount = onlineCount;
    }


    public static ConcurrentHashMap<String, WebSocketClient> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketClient> webSocketMap) {
        WebSocketService.webSocketMap = webSocketMap;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
