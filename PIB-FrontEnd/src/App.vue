<template>
  <div id="app">
    <div v-if="!connected">
      <input v-model="username" placeholder="Enter your name" />
      <button @click="connect">Join Chat</button>
    </div>
    <div v-else>
      <div class="chat-window">
        <div v-for="message in messages" :key="message.content">
          <b>{{ message.sender }}:</b> {{ message.content }}
        </div>
      </div>
      <input
        v-model="message"
        @keyup.enter="sendMessage"
        placeholder="Type a message"
      />
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  data() {
    return {
      username: "",
      message: "",
      messages: [],
      stompClient: null,
      connected: false,
    };
  },
  methods: {
    connect() {
      const socket = new SockJS("http://localhost:8080/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, (frame) => {
        this.connected = true;
        console.log("Connected: " + frame);
        this.stompClient.subscribe("/topic/public", (message) => {
          const msg = JSON.parse(message.body);
          this.messages.push(msg);
        });

        this.stompClient.send(
          "/app/chat.addUser",
          {},
          JSON.stringify({ sender: this.username, type: "JOIN" })
        );
      });
    },
    sendMessage() {
      if (this.message.trim() !== "") {
        const chatMessage = {
          sender: this.username,
          content: this.message,
          type: "CHAT",
        };
        this.stompClient.send(
          "/app/chat.sendMessage",
          {},
          JSON.stringify(chatMessage)
        );
        this.message = "";
      }
    },
  },
};
</script>

<style>
.chat-window {
  height: 300px;
  overflow-y: scroll;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}
</style>
