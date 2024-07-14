<template>
  <div id="editor" style="width: 100%; height: 100vh"></div>
</template>

<script>
import * as monaco from "monaco-editor";

export default {
  name: "CodeEditor",
  mounted() {
    this.editor = monaco.editor.create(document.getElementById("editor"), {
      value: 'function x() {\n\tconsole.log("Hello world!");\n}',
      language: "javascript",
    });

    // WebSocket 설정
    this.socket = new WebSocket("ws://localhost:8080/ws");

    this.socket.onopen = () => {
      console.log("WebSocket connection established");
    };

    this.socket.onmessage = (event) => {
      let data = JSON.parse(event.data);
      this.editor.setValue(data.content);
    };

    this.socket.onclose = () => {
      console.log("WebSocket connection closed");
    };

    this.socket.onerror = (error) => {
      console.error("WebSocket error:", error);
    };

    // 에디터 내용 변경 시 WebSocket 메시지 전송
    this.editor.onDidChangeModelContent(() => {
      let content = this.editor.getValue();
      this.socket.send(JSON.stringify({ content: content }));
    });
  },
};
</script>

<style scoped>
#editor {
  border: 1px solid #ccc;
}
</style>
