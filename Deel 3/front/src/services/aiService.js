import axios from 'axios'

const AI_SERVICE_URL = 'http://localhost:9082'

export const askQuestionApi = async (questionText) => {
    const response = await axios.post(`${AI_SERVICE_URL}/ask`, {
        question: questionText
    })
    return response.data
}

export const analyzeContractApi = async (contractText, question) => {
    const response = await axios.post(`${AI_SERVICE_URL}/api/ai/analyze`, {
        contractText,
        question
    })
    return response.data
}