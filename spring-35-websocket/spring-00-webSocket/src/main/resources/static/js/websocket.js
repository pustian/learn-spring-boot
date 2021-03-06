var socket;
function connect() {
    if (typeof (WebSocket) == "undefined") {
        console.log("遗憾：您的浏览器不支持WebSocket");
    } else {
        console.log("恭喜：您的浏览器支持WebSocket");

        //实现化WebSocket对象
        //指定要连接的服务器地址与端口建立连接
        //注意ws、wss使用不同的端口。我使用自签名的证书测试，
        //无法使用wss，浏览器打开WebSocket时报错
        //ws对应http、wss对应https。
        socket = new WebSocket("ws://localhost:8081/ws/asset");
        //连接打开事件
        socket.onopen = function(evt) {
            console.log(evt);
            console.log("Socket 已打开");
            socket.send("消息发送测试(From Client)");
        };
        //收到消息事件
        socket.onmessage = function(evt) {
            console.log(evt);
            console.log("socket 收到消息");
        };
        //连接关闭事件
        socket.onclose = function(evt) {
            console.log(evt);
            console.log("Socket已关闭");
        };
        //发生了错误事件
        socket.onerror = function(evt) {
            console.log(evt);
            console.log("Socket发生了错误");
            alert("Socket发生了错误");
        }
    }
}
function disconnect() {
    socket.close();
}
function sendMsg(msg, id) {
    console.log("id="+id+" message="+msg)
    if(msg != null && id != null){
        $.ajax({
            method: 'get',
            url: '/api/ws/sendMsg',
            data:{
                message:msg,
                id: id,
            },

            success:function(data) {
                console.log(data);
            }
        })
    }else{
        alert("请填写要发送的内容 和ID");
    }
}

function sendAll(msg) {
    console.log(" message="+msg)
    if(msg != null){
        $.ajax({
            method: 'get',
            url: '/api/ws/notifyMsg',
            data:{
                message:msg,
            },

            success:function(data) {
                console.log(data);
            }
        })
    }else{
        alert("请填写要发送的内容 和ID");
    }
}
