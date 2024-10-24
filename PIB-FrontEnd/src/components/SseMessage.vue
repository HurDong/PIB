<template>
  <div id="app">
    <h1>SSE 메시지 수신</h1>
    <div v-for="(message, index) in messages" :key="index">
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      messages: [] // 메시지를 저장할 배열
    };
  },
  mounted() {
    // EventSource 객체를 통해 SSE 연결
    const eventSource = new EventSource('http://localhost:8080/sse');

    // 서버에서 전송된 메시지를 수신
    eventSource.onmessage = (event) => {
      console.log('Received message:', event.data);
      // 메시지를 배열에 추가
      this.messages.push(event.data);
    };

    // SSE 연결 오류 처리
    eventSource.onerror = (error) => {
      console.error('Error occurred:', error);
    };
  }
};
</script>

<style>
/* 스타일 추가 가능 */
</style>
