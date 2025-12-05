<template>
  <div>
    <!-- Create Request Section -->
    <div class="section">
      <h2>➕ Créer une Demande</h2>
      <div class="form-group">
        <label>Titre:</label>
        <input name="titel" v-model="newRequest.title" placeholder="Titre de la demande" />
      </div>
      <div class="form-group">
        <label>Description:</label>
        <textarea name="beschrijving" v-model="newRequest.description" placeholder="Description détaillée..."></textarea>
      </div>
      <div class="form-group">
        <label>Email client:</label>
        <input name="emailKlant" v-model="newRequest.clientEmail" type="email" placeholder="client@example.com" />
      </div>
      <button name="createRequest" @click="createRequest" :disabled="loading">
        {{ loading ? 'Création...' : 'Créer' }}
      </button>
    </div>

    <!-- Requests List Section -->
    <div class="section">
      <h2>📋 Liste des Demandes</h2>
      <button @click="loadRequests" :disabled="loading" style="margin-bottom: 15px;">
        {{ loading ? 'Chargement...' : '🔄 Actualiser' }}
      </button>

      <div v-if="requests.length === 0" class="response-box">
        Aucune demande trouvée.
      </div>

      <div class="requests-list">
        <div v-for="request in requests" :key="request.id" class="request-item">
          <div class="request-header">
            <span class="request-title">{{ request.title }}</span>
            <span class="request-status" :class="'status-' + request.status">
              {{ request.status }}
            </span>
          </div>
          <p><strong>Description:</strong> {{ request.description }}</p>
          <p><strong>Email:</strong> {{ request.clientEmail }}</p>
          <p><strong>Créé le:</strong> {{ formatDate(request.createdAt) }}</p>
          <p v-if="request.updatedAt"><strong>Mis à jour le:</strong> {{ formatDate(request.updatedAt) }}</p>

          <div class="request-actions">
            <select v-model="request.newStatus" class="btn-small">
              <option value="PENDING">PENDING</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="COMPLETED">COMPLETED</option>
              <option value="CANCELLED">CANCELLED</option>
            </select>
            <button @click="updateRequestStatus(request)" class="btn-small btn-warning">
              Changer Statut
            </button>
            <button @click="deleteRequest(request.id)" class="btn-small btn-danger">
              🗑️ Supprimer
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getAllRequestsApi,
  createRequestApi,
  updateRequestApi,
  deleteRequestApi
} from '../services/requestsService'

export default {
  name: 'RequestsService',
  data() {
    return {
      loading: false,
      newRequest: {
        title: '',
        description: '',
        clientEmail: ''
      },
      requests: []
    }
  },
  mounted() {
    this.loadRequests()
  },
  methods: {
    async createRequest() {
      if (!this.newRequest.title || !this.newRequest.clientEmail) {
        alert('Veuillez remplir au moins le titre et l\'email')
        return
      }

      this.loading = true

      try {
        await createRequestApi(this.newRequest)
        this.newRequest = { title: '', description: '', clientEmail: '' }
        alert('Demande créée avec succès!')
        await this.loadRequests()
      } catch (error) {
        alert('Erreur: ' + (error.response?.data || error.message))
      } finally {
        this.loading = false
      }
    },

    async loadRequests() {
      this.loading = true

      try {
        const data = await getAllRequestsApi()
        this.requests = data.map(req => ({
          ...req,
          newStatus: req.status
        }))
      } catch (error) {
        alert('Erreur lors du chargement: ' + error.message)
      } finally {
        this.loading = false
      }
    },

    async updateRequestStatus(request) {
      this.loading = true

      try {
        const updatedRequest = {
          ...request,
          status: request.newStatus
        }
        await updateRequestApi(request.id, updatedRequest)
        alert('Statut mis à jour!')
        await this.loadRequests()
      } catch (error) {
        alert('Erreur: ' + (error.response?.data || error.message))
      } finally {
        this.loading = false
      }
    },

    async deleteRequest(id) {
      if (!confirm('Êtes-vous sûr de vouloir supprimer cette demande?')) {
        return
      }

      this.loading = true

      try {
        await deleteRequestApi(id)
        alert('Demande supprimée!')
        await this.loadRequests()
      } catch (error) {
        alert('Erreur: ' + (error.response?.data || error.message))
      } finally {
        this.loading = false
      }
    },

    formatDate(dateString) {
      if (!dateString) return ''
      return new Date(dateString).toLocaleString('fr-FR')
    }
  }
}
</script>