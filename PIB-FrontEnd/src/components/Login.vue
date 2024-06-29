<template>
  <div class="login">
    <h1>Enter Chat Room</h1>
    <input v-model="username" placeholder="Enter your nickname" />
    <button @click="enterChatRoom">Join Text Chat</button>
    <button @click="enterVideoChatRoom">Join Video Chat</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "",
    };
  },
  methods: {
    enterChatRoom() {
      if (this.username.trim()) {
        this.$router.push({
          name: "ChatRoom",
          params: { username: this.username },
        });
      }
    },
    enterVideoChatRoom() {
      if (this.username.trim()) {
        this.$router.push({
          name: "VideoChat",
          params: { username: this.username },
        });
      }
    },
  },
};
</script>

<style scoped>
.login {
  width: 300px;
  margin: 100px auto;
  text-align: center;
}
input {
  width: calc(100% - 20px);
  padding: 10px;
  margin-bottom: 10px;
}
button {
  padding: 10px 20px;
  margin: 5px;
}
</style>
