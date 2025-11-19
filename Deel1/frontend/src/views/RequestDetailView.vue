<template>
  <div class="request-detail-container" v-if="request">
    <header class="page-header">
      <router-link to="/" class="back-button">← Back to List</router-link>
      <div class="header-content">
        <h1>{{ request.title }}</h1>
        <div class="status-pill" :class="request.status.replace(' ', '-')">
          {{ request.status }}
        </div>
      </div>
    </header>

    <div class="details-grid">
      <div class="detail-card jurist-card">
        <h3>Jurist Details</h3>
        <p><strong>Name:</strong> {{ request.clientName }}</p>
        <p><strong>Email:</strong> {{ request.clientEmail }}</p>
      </div>

      <div class="detail-card law-card">
        <h3>Associated Law</h3>
        <p><strong>Name:</strong> {{ request.lawName }}</p>
        <p><strong>Description:</strong> {{ request.lawDescription }}</p>
      </div>
    </div>

    <div class="detail-card content-card">
      <h3>Request Content</h3>
      <p>{{ request.previewText }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "RequestDetailView",
  data() {
    return {
      request: null,
    };
  },
  created() {
    const requestId = this.$route.params.id;

    fetch(`http://localhost:8080/api/requests/${requestId}`)
      .then((response) => response.json())
      .then((data) => {
        this.request = data;
      })
      .catch((error) => {
        console.error(
          "There was an error fetching the request details:",
          error
        );
      });
  },
};
</script>

<style scoped>
.request-detail-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica,
    Arial, sans-serif;
  color: #343a40;
}

/* Header Section */
.page-header {
  margin-bottom: 2rem;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 1rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e9ecef;
}

h1 {
  font-size: 2.5rem;
  color: #212529;
  font-weight: 600;
  margin: 0;
}

/* Details Grid Layout */
.details-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

/* Shared Card Styling */
.detail-card {
  background-color: #ffffff;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 1.5rem;
}

.detail-card h3 {
  font-size: 1.2rem;
  font-weight: 600;
  margin-top: 0;
  margin-bottom: 1rem;
  border-bottom: 1px solid #f1f3f5;
  padding-bottom: 0.75rem;
}

.detail-card p {
  line-height: 1.6;
  margin: 0.5rem 0;
}

/* Back Button */
.back-button {
  display: inline-block;
  margin-bottom: 2rem;
  padding: 0.5rem 1rem;
  background-color: #f8f9fa;
  color: #495057;
  text-decoration: none;
  border-radius: 5px;
  font-weight: 500;
  border: 1px solid #dee2e6;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: #e9ecef;
}

/* Status Pill */
.status-pill {
  padding: 0.3rem 0.8rem;
  border-radius: 15px;
  font-size: 0.85rem;
  font-weight: 600;
  color: #fff;
  text-transform: capitalize;
}

.status-pill.pending {
  background-color: #ffc107; /* Yellow */
  color: #212529;
}

.status-pill.in-review {
  background-color: #17a2b8; /* Blue */
}

.status-pill.completed {
  background-color: #28a745; /* Green */
}

.status-pill.rejected {
  background-color: #dc3545; /* Red */
}
</style>
