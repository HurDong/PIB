import { createRouter, createWebHistory } from "vue-router";
import Login from "../components/Login.vue";
import ChatRoom from "../components/ChatRoom.vue";
import VideoChat from "../components/VideoChat.vue";
import WebEditor from "../components/WebEditor.vue";
import SseMessage from "../components/SseMessage.vue"; // 새로 추가된 SSE 메시지 컴포넌트

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/chat/:username",
    name: "ChatRoom",
    component: ChatRoom,
  },
  {
    path: "/videochat/:username",
    name: "VideoChat",
    component: VideoChat,
  },
  {
    path: "/editor",
    name: "WebEditor",
    component: WebEditor,
  },
  {
    path: "/sse-message",  // 새로운 SSE 메시지 경로 추가
    name: "SseMessage",
    component: SseMessage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
