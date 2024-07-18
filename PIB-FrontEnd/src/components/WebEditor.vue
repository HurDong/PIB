<template>
  <div id="editor" style="width: 100%; height: 100vh"></div>
</template>

<script>
import * as monaco from "monaco-editor";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  name: "WebEditor",
  mounted() {
    this.initializeEditor();
    this.connectWebSocket();
  },
  methods: {
    initializeEditor() {
      this.editor = monaco.editor.create(document.getElementById("editor"), {
        value: 'function hello() {\n\tconsole.log("Hello, world!");\n}',
        language: "javascript",
      });

      this.editor.onDidChangeModelContent(() => {
        const content = this.editor.getValue();
        this.sendContent(content);
      });
    },
    connectWebSocket() {
      const socket = new SockJS("http://localhost:8080/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe("/topic/editor", (message) => {
          const content = JSON.parse(message.body).content;
          this.editor.setValue(content);
        });
      });
    },
    sendContent(content) {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          "/app/updateContent",
          {},
          JSON.stringify({ content })
        );
      }
    },
  },
};
</script>

<style scoped>
#editor {
  border: 1px solid #ccc;
}
</style>
