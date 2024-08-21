import SockJS from "sockjs-client";
import Stomp from "stompjs";

let stompClient = null;
let connected = false;

const serverUrl = "http://localhost:8080/ws"; // 서버 주소와 포트를 설정합니다.

export function connect(onMessageReceived) {
    console.log("Attempting to connect to WebSocket at:", serverUrl);
    const socket = new SockJS(serverUrl);
    stompClient = Stomp.over(socket);

    const reconnect = () => {
        console.log("Attempting to reconnect...");
        connect(onMessageReceived);
    };
    stompClient.connect({}, (frame) => {
        connected = true;
        console.log("Connected: " + frame);
        stompClient.subscribe("/topic/code", (message) => {
            onMessageReceived(JSON.parse(message.body));
        });
    }, (error) => {
        console.error("Connection lost", error);
        connected = false;
        setTimeout(reconnect, 5000); // 5초 후에 재연결 시도
    });
}

export function sendCode(content) {
    if (stompClient && stompClient.connected) {
        stompClient.send("/topic/code.edit", {}, JSON.stringify({ content }));
    }
}
