<template>
  <div class="chat-room">
    <h1>Chat Room</h1>
    <div class="messages">
      <div v-for="(message, index) in messages" :key="index" class="message">
        {{ message }}
      </div>
    </div>
    <input
      v-model="newMessage"
      @keyup.enter="sendMessage"
      placeholder="Type your message here..."
    />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ws: null,
      messages: [],
      newMessage: "",
    };
  },
  created() {
    this.connectWebSocket();
  },
  methods: {
    connectWebSocket() {
      this.ws = new WebSocket("ws://localhost:8080/ws");
      this.ws.onmessage = (event) => {
        this.messages.push(event.data);
      };
      this.ws.onclose = () => {
        console.log("WebSocket connection closed");
      };
    },
    sendMessage() {
      if (this.newMessage.trim() !== "") {
        this.ws.send(this.newMessage);
        this.newMessage = "";
      }
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
input {
  width: calc(100% - 60px);
  padding: 10px;
}
button {
  width: 50px;
  padding: 10px;
}
</style>
