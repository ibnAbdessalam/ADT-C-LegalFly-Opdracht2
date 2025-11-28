<template>
  <div>
    <!-- Ask Question Section -->
    <div class="section">
      <h2>💬 Ask Question</h2>
      <div class="form-group">
        <label>Question:</label>
        <textarea v-model="question" placeholder="Posez votre question..."></textarea>
      </div>
      <button @click="askQuestion" :disabled="loading">
        {{ loading ? 'Envoi...' : 'Envoyer' }}
      </button>

      <div v-if="questionResponse" class="response-box" :class="questionResponse.error ? 'error' : 'success'">
        <strong>Réponse:</strong>
        <pre>{{ JSON.stringify(questionResponse, null, 2) }}</pre>
      </div>
    </div>

    <!-- Contract Analysis Section -->
    <div class="section">
      <h2>📄 Analyse de Contrat</h2>
      <div class="form-group">
        <label>Texte du contrat:</label>
        <textarea v-model="contractText" placeholder="Collez le texte du contrat ici..."></textarea>
      </div>
      <div class="form-group">
        <label>Question sur le contrat:</label>
        <input v-model="contractQuestion" placeholder="Que voulez-vous savoir sur ce contrat?" />
      </div>
      <button @click="analyzeContract" :disabled="loading">
        {{ loading ? 'Analyse...' : 'Analyser' }}
      </button>

      <div v-if="contractResponse" class="response-box" :class="contractResponse.error ? 'error' : 'success'">
        <div v-if="!contractResponse.error">
          <div style="margin-bottom: 15px;">
            <strong>📝 Résumé:</strong>
            <p>{{ contractResponse.summary }}</p>
          </div>
          <div v-if="contractResponse.recommendation">
            <strong>💡 Recommandation:</strong>
            <p>{{ contractResponse.recommendation }}</p>
          </div>
        </div>
        <div v-else>
          <strong>Erreur:</strong>
          <pre>{{ contractResponse.error }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { askQuestionApi, analyzeContractApi } from '../services/aiService'

export default {
  name: 'AiService',
  data() {
    return {
      loading: false,
      question: '',
      questionResponse: null,
      contractText: '',
      contractQuestion: '',
      contractResponse: null
    }
  },
  methods: {
    async askQuestion() {
      if (!this.question.trim()) {
        alert('Veuillez entrer une question')
        return
      }

      this.loading = true
      this.questionResponse = null

      try {
        this.questionResponse = await askQuestionApi(this.question)
      } catch (error) {
        this.questionResponse = {
          error: error.response?.data || error.message
        }
      } finally {
        this.loading = false
      }
    },

    async analyzeContract() {
      if (!this.contractText.trim() || !this.contractQuestion.trim()) {
        alert('Veuillez remplir le texte du contrat et la question')
        return
      }

      this.loading = true
      this.contractResponse = null

      try {
        this.contractResponse = await analyzeContractApi(
            this.contractText,
            this.contractQuestion
        )
      } catch (error) {
        this.contractResponse = {
          error: error.response?.data || error.message
        }
      } finally {
        this.loading = false
      }
    }
  }
}
</script>