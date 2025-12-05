<template>
  <div id="app">
    <h1>🚀 Microservices Dashboard</h1>

    <div class="tabs">
      <button
          class="tab"
          :class="{ active: activeTab === 'ai' }"
          @click="activeTab = 'ai'"
      >
        AI Service (Port 9082)
      </button>
      <button
          name="RequestTab"
          class="tab"
          :class="{ active: activeTab === 'requests' }"
          @click="activeTab = 'requests'"
      >
        Requests Service (Port 9081)
      </button>
    </div>

    <AiService v-show="activeTab === 'ai'" />
    <RequestsService v-show="activeTab === 'requests'" />
  </div>
</template>

<script>
import AiService from './components/AiService.vue'
import RequestsService from './components/RequestsService.vue'

export default {
  name: 'App',
  components: {
    AiService,
    RequestsService
  },
  data() {
    return {
      activeTab: 'ai'
    }
  }
}
</script>
