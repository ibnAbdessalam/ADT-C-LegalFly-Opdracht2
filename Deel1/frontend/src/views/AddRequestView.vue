<template>
  <div class="add-request-container">
    <header class="page-header">
      <h1>Create a New Legal Request</h1>
      <router-link to="/" class="back-button">← Cancel</router-link>
    </header>

    <form @submit.prevent="submitRequest" class="request-form">
      <div class="form-group">
        <label for="jurist">Select Jurist</label>
        <select id="jurist" v-model="newRequest.juristId" required>
          <option disabled value="">Please select a jurist</option>
          <option v-for="jurist in jurists" :key="jurist.id" :value="jurist.id">
            {{ jurist.firstName }} {{ jurist.lastName }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="law">Select Associated Law</label>
        <select id="law" v-model="newRequest.lawId" required>
          <option disabled value="">Please select a law</option>
          <option v-for="law in laws" :key="law.id" :value="law.id">
            {{ law.name }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="content">Request Content</label>
        <textarea
          id="content"
          v-model="newRequest.content"
          rows="6"
          required
          placeholder="Describe the legal request in detail..."
        ></textarea>
      </div>

      <button type="submit" class="submit-button">Submit Request</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "AddRequestView",
  data() {
    return {
      newRequest: {
        juristId: "",
        lawId: "",
        content: "",
      },
      jurists: [],
      laws: [],
    };
  },
  methods: {
    submitRequest() {
      // Use fetch to send a POST request to the backend
      fetch("http://localhost:8080/api/requests", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.newRequest), // Convert the form data to a JSON string
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then(() => {
          // After successful submission, redirect back to the main list page
          this.$router.push("/");
        })
        .catch((error) => {
          console.error("There was an error submitting the request:", error);
        });
    },
  },
  created() {
    // When the component is created, fetch both jurists and laws
    // Fetch Jurists
    fetch("http://localhost:8080/api/jurists")
      .then((response) => response.json())
      .then((data) => {
        this.jurists = data;
      })
      .catch((error) => console.error("Error fetching jurists:", error));

    // Fetch Laws
    fetch("http://localhost:8080/api/laws")
      .then((response) => response.json())
      .then((data) => {
        this.laws = data;
      })
      .catch((error) => console.error("Error fetching laws:", error));
  },
};
</script>

<style scoped>
.add-request-container {
  max-width: 700px;
  margin: 0 auto;
  padding: 2rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica,
    Arial, sans-serif;
  color: #343a40;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e9ecef;
}

h1 {
  font-size: 2rem;
  margin: 0;
}

.back-button {
  text-decoration: none;
  color: #6c757d;
  font-weight: 500;
}

.request-form {
  background-color: #fff;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #495057;
}

select,
textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 1rem;
  font-family: inherit;
  background-color: #f8f9fa;
}

select:focus,
textarea:focus {
  outline: none;
  border-color: #80bdff;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.submit-button {
  padding: 0.75rem 1.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 500;
  transition: background-color 0.2s ease;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>
