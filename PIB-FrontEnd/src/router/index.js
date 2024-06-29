import { createRouter, createWebHistory } from "vue-router";
import Login from "../components/Login.vue";
import ChatRoom from "../components/ChatRoom.vue";
import VideoChat from "../components/VideoChat.vue"; // 새로 추가된 화상 채팅 컴포넌트

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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
