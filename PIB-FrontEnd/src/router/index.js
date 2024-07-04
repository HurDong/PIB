import { createRouter, createWebHistory } from "vue-router";
import Login from "../components/Login.vue";
import ChatRoom from "../components/ChatRoom.vue";
import VideoChat from "../components/VideoChat.vue";
import WebEditor from "../components/WebEditor.vue"; // 새로 추가된 Web Editor 컴포넌트

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
    path: "/editor",  // 새로운 Web Editor 경로 추가
    name: "WebEditor",
    component: WebEditor,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
