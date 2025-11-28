import axios from 'axios'

const REQUESTS_SERVICE_URL = 'http://localhost:9081/api/requests'

export const getAllRequestsApi = async () => {
    const response = await axios.get(REQUESTS_SERVICE_URL)
    return response.data
}

export const getRequestByIdApi = async (id) => {
    const response = await axios.get(`${REQUESTS_SERVICE_URL}/${id}`)
    return response.data
}

export const createRequestApi = async (request) => {
    const response = await axios.post(REQUESTS_SERVICE_URL, request)
    return response.data
}

export const updateRequestApi = async (id, request) => {
    const response = await axios.put(`${REQUESTS_SERVICE_URL}/${id}`, request)
    return response.data
}

export const deleteRequestApi = async (id) => {
    await axios.delete(`${REQUESTS_SERVICE_URL}/${id}`)
}