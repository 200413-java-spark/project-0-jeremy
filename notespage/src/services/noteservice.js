import axios from 'axios'
const baseUrl = 'http://192.168.1.100:8081/api/notes'

const getAllNotes = () => {
  const request = axios.get(baseUrl)
  return request.then(response => response.data)
}

const getNote = (id) => {
  const request = axios.get(`${baseUrl}/note/${id}`)
  return request.then(response => response.data)
}

const getLatest = (n = 10) => {
  const request = axios.get(`${baseUrl}/latest/${n}`)
  return request.then(response => response.data)
}

const getCategories = () => {
  const request = axios.get(`${baseUrl}/categories`)
  return request.then(response => response.data)
}

const insertNote = newObject => {
  const request = axios.post(baseUrl, newObject)
  return request.then(response => response.data)
}

const updateNote = (id, newObject) => {
  const request = axios.put(`${baseUrl}/${id}`, newObject)
  return request.then(response => response.data)
}

const deleteNote = (id) => {
  const request = axios.delete(`{baseUrl}/${id}`)
  return request.then(response => response.data)
}

export default { getAllNotes, getNote, getLatest, getCategories, insertNote, updateNote, deleteNote }