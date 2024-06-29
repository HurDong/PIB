<template>
  <div class="chat-room">
    <h1>Video Chat Room</h1>
    <div class="users">
      <h2>Users</h2>
      <ul>
        <li v-for="user in users" :key="user">{{ user }}</li>
      </ul>
    </div>
    <div class="videos">
      <video id="localVideo" autoplay playsinline></video>
      <video id="remoteVideo" autoplay playsinline></video>
    </div>
    <button @click="startCall">Start Call</button>
    <button @click="leaveChat">Leave</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ws: null,
      username: "",
      users: [],
      localStream: null,
      remoteStream: null,
      peerConnection: null,
      configuration: {
        iceServers: [{ urls: "stun:stun.l.google.com:19302" }],
      },
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
    async connectWebSocket() {
      this.ws = new WebSocket("ws://localhost:8080/ws");
      this.ws.onmessage = this.handleMessage;
      this.ws.onopen = () => {
        this.ws.send(JSON.stringify({ type: "join", username: this.username }));
      };
      this.ws.onclose = () => {
        console.log("WebSocket connection closed");
      };
    },
    handleMessage(event) {
      const data = JSON.parse(event.data);
      console.log("Received message:", data);
      if (data.type === "users") {
        this.users = data.users;
      } else if (data.type === "offer") {
        this.handleOffer(data);
      } else if (data.type === "answer") {
        this.handleAnswer(data);
      } else if (data.type === "candidate") {
        this.handleCandidate(data);
      }
    },
    async startCall() {
      this.peerConnection = new RTCPeerConnection(this.configuration);
      this.peerConnection.onicecandidate = (event) => {
        if (event.candidate) {
          this.ws.send(
            JSON.stringify({ type: "candidate", candidate: event.candidate })
          );
        }
      };
      this.peerConnection.ontrack = (event) => {
        this.remoteStream = event.streams[0];
        document.getElementById("remoteVideo").srcObject = this.remoteStream;
      };

      try {
        this.localStream = await navigator.mediaDevices.getUserMedia({
          video: true,
          audio: true,
        });
        document.getElementById("localVideo").srcObject = this.localStream;
        this.localStream.getTracks().forEach((track) => {
          this.peerConnection.addTrack(track, this.localStream);
        });
        const offer = await this.peerConnection.createOffer();
        await this.peerConnection.setLocalDescription(offer);
        this.ws.send(
          JSON.stringify({
            type: "offer",
            sdp: this.peerConnection.localDescription,
          })
        );
      } catch (error) {
        console.error("Error accessing media devices.", error);
      }
    },
    async handleOffer(data) {
      if (!this.peerConnection) {
        this.peerConnection = new RTCPeerConnection(this.configuration);
        this.peerConnection.onicecandidate = (event) => {
          if (event.candidate) {
            this.ws.send(
              JSON.stringify({ type: "candidate", candidate: event.candidate })
            );
          }
        };
        this.peerConnection.ontrack = (event) => {
          this.remoteStream = event.streams[0];
          document.getElementById("remoteVideo").srcObject = this.remoteStream;
        };
      }
      await this.peerConnection.setRemoteDescription(
        new RTCSessionDescription(data.sdp)
      );
      this.localStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      });
      document.getElementById("localVideo").srcObject = this.localStream;
      this.localStream.getTracks().forEach((track) => {
        this.peerConnection.addTrack(track, this.localStream);
      });
      const answer = await this.peerConnection.createAnswer();
      await this.peerConnection.setLocalDescription(answer);
      this.ws.send(
        JSON.stringify({
          type: "answer",
          sdp: this.peerConnection.localDescription,
        })
      );
    },
    async handleAnswer(data) {
      await this.peerConnection.setRemoteDescription(
        new RTCSessionDescription(data.sdp)
      );
    },
    async handleCandidate(data) {
      if (data.candidate) {
        await this.peerConnection.addIceCandidate(
          new RTCIceCandidate(data.candidate)
        );
      }
    },
    leaveChat() {
      if (this.ws) {
        this.ws.send(
          JSON.stringify({ type: "leave", username: this.username })
        );
        this.ws.close();
        if (this.peerConnection) {
          this.peerConnection.close();
          this.peerConnection = null;
        }
        this.$router.push({ name: "Login" });
      }
    },
  },
};
</script>

<style scoped>
.chat-room {
  width: 600px;
  margin: 0 auto;
}
.videos {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
video {
  width: 45%;
  border: 1px solid #ccc;
}
.users {
  margin-top: 20px;
}
button {
  margin-right: 10px;
}
</style>
