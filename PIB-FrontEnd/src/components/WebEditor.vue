<template>
  <div id="editor" contenteditable="true" @compositionstart="onCompositionStart" @compositionend="onCompositionEnd" @input="onInput"></div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default {
  name: 'Editor',
  data() {
    return {
      stompClient: null,
      isComposing: false,
    };
  },
  mounted() {
    this.connect();
  },
  methods: {
    connect() {
      const socket = new SockJS('http://localhost:8080/ws');
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, (frame) => {
        this.stompClient.subscribe('/topic/code', (codeMessage) => {
          const message = JSON.parse(codeMessage.body);
          if (!this.isComposing) {
            document.getElementById('editor').innerText = message.content;
          }
        });
      });
    },
    sendCode(content) {
      this.stompClient.send('/app/code.edit', {}, JSON.stringify({ content, sender: 'user', type: 'edit' }));
    },
    onCompositionStart() {
      this.isComposing = true;
    },
    onCompositionEnd() {
      this.isComposing = false;
      this.sendCode(document.getElementById('editor').innerText);
    },
    onInput() {
      if (!this.isComposing) {
        this.sendCode(document.getElementById('editor').innerText);
      }
    },
  },
};
</script>

<style>
#editor {
  width: 80%;
  height: 500px;
  border: 1px solid #ccc;
  white-space: pre-wrap;
}
</style>
