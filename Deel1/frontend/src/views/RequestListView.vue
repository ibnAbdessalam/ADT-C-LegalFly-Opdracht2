<template>
  <div class="request-list-container">
    <header class="page-header">
      <h1>All Legal Requests</h1>
      <router-link to="/add-request" class="add-button"
        >+ Add New Request</router-link
      >
    </header>
    <div class="request-grid">
      <router-link
        v-for="request in requests"
        :key="request.id"
        :to="{ name: 'request-detail', params: { id: request.id } }"
        class="request-card-link"
      >
        <div class="request-card">
          <h2 class="request-title">{{ request.title }}</h2>
          <div class="request-status-wrapper">
            <span
              class="status-indicator"
              :class="request.status.replace(' ', '-')"
            ></span>
            <p class="request-status">{{ request.status }}</p>
          </div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "RequestListView",
  data() {
    return {
      requests: [],
    };
  },
  created() {
    fetch("http://localhost:8080/api/requests")
      .then((response) => response.json())
      .then((data) => {
        this.requests = data;
      })
      .catch((error) => {
        console.error("There was an error fetching the requests:", error);
      });
  },
};
</script>

<style scoped>
.request-list-container {
  max-width: 960px;
  margin: 0 auto;
  padding: 2rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica,
    Arial, sans-serif;
}

.page-header {
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e9ecef;
}

h1 {
  font-size: 2.5rem;
  color: #212529;
  font-weight: 600;
}

.request-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
}

/* Card Styling */
.request-card-link {
  text-decoration: none;
  color: inherit;
}

.request-card {
  background-color: #ffffff;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 1.5rem;
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  cursor: pointer;
}

.request-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
}

.request-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-top: 0;
  margin-bottom: 1rem;
  color: #343a40;
}

.request-status-wrapper {
  display: flex;
  align-items: center;
}

.request-status {
  font-size: 0.9rem;
  font-weight: 500;
  color: #6c757d;
  margin: 0;
}

.status-indicator {
  height: 10px;
  width: 10px;
  border-radius: 50%;
  margin-right: 0.5rem;
  background-color: #6c757d;
}

.status-indicator.pending {
  background-color: #ffc107; /* Yellow */
}
.status-indicator.in-review {
  background-color: #17a2b8; /* Blue */
}
.status-indicator.completed {
  background-color: #28a745; /* Green */
}
.status-indicator.rejected {
  background-color: #dc3545; /* Red */
}

.add-button {
  padding: 0.6rem 1.2rem;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-weight: 500;
  transition: background-color 0.2s ease;
}
.add-button:hover {
  background-color: #0056b3;
}
</style>
