<template>
  <div class="social-feed-page">
    <div class="main-content">
      <!-- Post Input Box -->
      <div class="post-input">
        <input
          type="text"
          v-model="newMessage"
          placeholder="Write a message... Use # for hashtags or @ for mentions"
        />
        <button @click="submitMessage">Post</button>
      </div>

      <!-- Social Feed Messages -->
      <div class="messages">
        <div
          v-for="(message) in messageList"
          :key="message.username + message.time"
          class="message"
        >
          <div class="message-header">
            <span class="username">{{ message.username }}</span>
            <span class="time">{{ message.time }}</span>
          </div>
          <div class="message-content">
            {{ message.content }}
          </div>
          <div class="message-actions">
            <span @click="likeMessage(message)">&#128077; {{ message.likes }}</span>
            <span @click="commentMessage">&#128172; {{ message.comments.length }}</span>
            <span @click="shareMessage">&#128257;</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Sidebar Trending Hashtags -->
    <div class="sidebar">
      <div class="trending-hashtags">
        <h3>Trending</h3>
        <ul>
          <li v-for="(hashtag) in trendingHashtags" :key="hashtag">
            <span>{{ hashtag }}</span>
            <button @click="followHashtag(hashtag)">Follow</button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SocialFeedPage",
  data() {
    return {
      newMessage: "",
      messageList: [
        {
          username: "EthanROSEKK",
          time: "1 month ago",
          content: "@helloworld hi!",
          likes: 1,
          comments: [],
        },
      ],
      trendingHashtags: [
        "#toptraders",
        "#amostil123",
        "#marketnews",
        "#EURUSD",
        "#superfx10",
        "#prowebafz",
        "#eurusdprofits",
        "#xingtingliu",
        "#bulltrade100",
        "#lieutenantdan",
      ],
    };
  },
  methods: {
    submitMessage() {
      if (this.newMessage.trim() !== "") {
        this.messageList.unshift({
          username: "EthanROSEKK",
          time: "just now",
          content: this.newMessage,
          likes: 0,
          comments: [],
        });
        this.newMessage = "";
      }
    },
    likeMessage(message) {
      message.likes++;
    },
    commentMessage() {
      alert("Comment functionality coming soon!");
    },
    shareMessage() {
      alert("Share functionality coming soon!");
    },
    followHashtag(hashtag) {
      alert(`You are now following ${hashtag}`);
    },
  },
};
</script>

<style scoped>
.social-feed-page {
  display: flex;
  padding: 20px;
  gap: 20px;
}

.main-content {
  flex: 3;
}

.sidebar {
  flex: 1;
}

.post-input {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.post-input input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.post-input button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}

.post-input button:hover {
  background-color: #0056b3;
}

.messages {
  margin-bottom: 20px;
}

.message {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;
  margin-bottom: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.message-header {
  font-weight: bold;
  margin-bottom: 5px;
  display: flex;
  justify-content: space-between;
}

.message-content {
  margin-bottom: 10px;
}

.message-actions {
  display: flex;
  gap: 15px;
}

.message-actions span {
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.trending-hashtags {
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.trending-hashtags ul {
  list-style: none;
  padding: 0;
}

.trending-hashtags li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  align-items: center;
}

.trending-hashtags button {
  background-color: #d9534f;
  color: white;
  border: none;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 5px;
}

.trending-hashtags button:hover {
  background-color: #c9302c;
}
</style>
