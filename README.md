# partner

刷新页面时，传输一个token，调用init接口，jwt解析，获取用户信息
ws://localhost:8080/chat/  主动链接websocket
使用 Sec-WebSocket-Protocol 传递消息  
String authorization = req.getHeader("Sec-WebSocket-Protocol"); 
怎样确定谁发给谁？
前端的token是哪里来的
Message 会存到Mysql 需要有entity 
怎样读取消息的配置


感觉没有必要再要一层ChatSessionServiceImpl.java  直接用service就行了
用户和用户之间，对方离线的处理？
