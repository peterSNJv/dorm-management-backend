import { defineStore } from 'pinia'
import { ref } from 'vue'
import {
  generateContent,
  generateCover,
  searchStockImages,
  publishArticle,
  getHistory,
} from '../api/content'

export const useContentStore = defineStore('content', () => {
  const loading = ref(false)
  const article = ref({ title: '', content: '', summary: '', keywords: [] })
  const coverUrl = ref('')
  const stockImages = ref([])
  const history = ref([])
  const publishStatus = ref('')

  async function generate(direction, style, length) {
    loading.value = true
    try {
      const res = await generateContent(direction, style, length)
      article.value = res.data
    } finally {
      loading.value = false
    }
  }

  async function genCover(title, keywords, style) {
    loading.value = true
    try {
      const res = await generateCover(title, keywords, style)
      coverUrl.value = res.data.image_url
    } finally {
      loading.value = false
    }
  }

  async function fetchStockImages(keywords, count) {
    loading.value = true
    try {
      const res = await searchStockImages(keywords, count)
      stockImages.value = res.data.images
    } finally {
      loading.value = false
    }
  }

  async function publish(data) {
    loading.value = true
    publishStatus.value = ''
    try {
      const res = await publishArticle(data)
      publishStatus.value = res.data.message
    } finally {
      loading.value = false
    }
  }

  async function fetchHistory() {
    const res = await getHistory()
    history.value = res.data
  }

  return { loading, article, coverUrl, stockImages, history, publishStatus, generate, genCover, fetchStockImages, publish, fetchHistory }
})
