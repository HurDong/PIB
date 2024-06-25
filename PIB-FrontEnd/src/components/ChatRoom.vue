<template>
  <div class="chat-room">
    <h1>Chat Room</h1>
    <div class="users">
      <h2>Users</h2>
      <ul>
        <li v-for="user in users" :key="user">{{ user }}</li>
      </ul>
    </div>
    <div class="messages">
      <div v-for="(message, index) in messages" :key="index" class="message">
        <strong>{{ message.username }}:</strong> {{ message.text }}
        <span class="timestamp">{{ formatTimestamp(message.timestamp) }}</span>
      </div>
    </div>
    <input
      v-model="newMessage"
      @keyup.enter="sendMessage"
      placeholder="Type your message here..."
    />
    <button @click="sendMessage">Send</button>
    <button @click="leaveChat">Leave</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ws: null,
      messages: [],
      newMessage: "",
      username: "",
      users: [],
    };
  },
  created() {
    this.username = this.$route.params.username;
    this.connectWebSocket();
  },
  beforeUnmount() {
    this.leaveChat();
  },
  methods: {
    connectWebSocket() {
      this.ws = new WebSocket("ws://localhost:8080/ws");
      this.ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        if (data.type === "message") {
          this.messages.push(data);
        } else if (data.type === "users") {
          this.users = data.users;
        }
      };
      this.ws.onopen = () => {
        this.ws.send(JSON.stringify({ type: "join", username: this.username }));
      };
      this.ws.onclose = () => {
        console.log("WebSocket connection closed");
      };
    },
    sendMessage() {
      if (this.newMessage.trim() !== "") {
        const message = {
          type: "message",
          username: this.username,
          text: this.newMessage,
          timestamp: new Date().toISOString(),
        };
        this.ws.send(JSON.stringify(message));
        this.newMessage = "";
      }
    },
    leaveChat() {
      if (this.ws) {
        this.ws.send(
          JSON.stringify({ type: "leave", username: this.username })
        );
        this.ws.close();
        this.$router.push({ name: "Login" });
      }
    },
    formatTimestamp(timestamp) {
      const date = new Date(timestamp);
      return date.toLocaleTimeString();
    },
  },
};
</script>

<style scoped>
.chat-room {
  width: 400px;
  margin: 0 auto;
}
.messages {
  height: 300px;
  border: 1px solid #ccc;
  padding: 10px;
  overflow-y: scroll;
}
.message {
  margin-bottom: 10px;
}
.timestamp {
  font-size: 0.8em;
  color: gray;
}
.users {
  margin-top: 20px;
}
input {
  width: calc(100% - 60px);
  padding: 10px;
}
button {
  width: 50px;
  padding: 10px;
}
</style>
