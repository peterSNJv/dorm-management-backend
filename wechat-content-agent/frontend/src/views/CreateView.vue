<script setup>
import { ref } from 'vue'
import { useContentStore } from '../stores/content'
import ContentEditor from '../components/ContentEditor.vue'
import CoverPreview from '../components/CoverPreview.vue'
import ImageSelector from '../components/ImageSelector.vue'

const store = useContentStore()

const direction = ref('')
const style = ref('科普')
const length = ref(1000)
const searchKeywords = ref('')
const selectedImages = ref([])

const styles = ['科普', '故事', '新闻', '轻松', '干货', '情感']

async function handleGenerate() {
  if (!direction.value.trim()) return
  await store.generate(direction.value, style.value, length.value)
  if (store.article.keywords.length) {
    await store.genCover(store.article.title, store.article.keywords)
    await store.fetchStockImages(store.article.keywords)
  }
}

async function handleSearchImages() {
  const kw = searchKeywords.value.split(',').map((s) => s.trim()).filter(Boolean)
  if (kw.length) await store.fetchStockImages(kw)
}

function toggleImage(img) {
  const idx = selectedImages.value.findIndex((i) => i.url === img.url)
  if (idx >= 0) selectedImages.value.splice(idx, 1)
  else selectedImages.value.push(img)
}

async function handlePublish() {
  await store.publish({
    title: store.article.title,
    content: store.article.content,
    summary: store.article.summary,
    cover_url: store.coverUrl,
    images: selectedImages.value.map((i) => i.url),
  })
}
</script>

<template>
  <div class="space-y-6">
    <div class="bg-white rounded-lg shadow-sm p-6">
      <h2 class="text-lg font-semibold mb-4">生成内容</h2>
      <div class="flex gap-4 items-end">
        <div class="flex-1">
          <label class="block text-sm font-medium text-gray-700 mb-1">文章方向</label>
          <input
            v-model="direction"
            class="w-full border rounded-lg px-3 py-2 focus:ring-2 focus:ring-blue-500 outline-none"
            placeholder="例如：大学生宿舍安全注意事项"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">风格</label>
          <select v-model="style" class="border rounded-lg px-3 py-2">
            <option v-for="s in styles" :key="s" :value="s">{{ s }}</option>
          </select>
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">字数</label>
          <input type="range" v-model.number="length" min="200" max="5000" step="100" class="w-32" />
          <span class="text-sm text-gray-500 ml-2">{{ length }}</span>
        </div>
        <button
          @click="handleGenerate"
          :disabled="store.loading"
          class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 disabled:opacity-50"
        >
          {{ store.loading ? '生成中...' : '生成文案' }}
        </button>
      </div>
    </div>

    <div class="grid grid-cols-3 gap-6">
      <div class="col-span-2">
        <h3 class="text-sm font-medium text-gray-700 mb-2">文案预览</h3>
        <ContentEditor v-model="store.article.content" />
      </div>
      <div>
        <h3 class="text-sm font-medium text-gray-700 mb-2">封面图</h3>
        <CoverPreview :url="store.coverUrl" :loading="store.loading" />
      </div>
    </div>

    <div class="bg-white rounded-lg shadow-sm p-6">
      <h3 class="text-sm font-medium text-gray-700 mb-2">配图</h3>
      <div class="flex gap-2 mb-3">
        <input
          v-model="searchKeywords"
          class="flex-1 border rounded-lg px-3 py-2 text-sm"
          placeholder="搜索关键词，逗号分隔"
        />
        <button @click="handleSearchImages" class="bg-gray-100 px-4 py-2 rounded-lg text-sm hover:bg-gray-200">
          搜索配图
        </button>
      </div>
      <ImageSelector :images="store.stockImages" :loading="store.loading" @select="toggleImage" />
    </div>

    <div class="flex justify-end gap-3">
      <div v-if="store.publishStatus" class="text-green-600 text-sm self-center">{{ store.publishStatus }}</div>
      <button
        @click="handlePublish"
        :disabled="store.loading || !store.article.title"
        class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 disabled:opacity-50"
      >
        发布到公众号
      </button>
    </div>
  </div>
</template>
