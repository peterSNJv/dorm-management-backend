import client from './client'

export function generateContent(direction, style, length) {
  return client.post('/content/generate', { direction, style, length })
}

export function generateCover(title, keywords, style) {
  return client.post('/content/cover', { title, keywords, style })
}

export function searchStockImages(keywords, count = 5) {
  return client.get('/content/stock-images', { params: { keywords: keywords.join(','), count } })
}

export function publishArticle(data) {
  return client.post('/content/publish', data)
}

export function getHistory() {
  return client.get('/content/history')
}

export function getHistoryDetail(id) {
  return client.get(`/content/history/${id}`)
}
