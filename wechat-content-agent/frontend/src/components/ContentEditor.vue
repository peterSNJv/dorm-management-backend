<script setup>
import { computed } from 'vue'
import { marked } from 'marked'

const props = defineProps({ modelValue: String })
const emit = defineEmits(['update:modelValue'])

const html = computed(() => marked(props.modelValue || ''))
</script>

<template>
  <div class="border rounded-lg overflow-hidden">
    <div class="flex border-b bg-gray-50 text-xs">
      <button class="px-3 py-1.5 font-medium text-blue-600 border-b-2 border-blue-600">预览</button>
    </div>
    <div class="p-4 min-h-[300px] prose prose-sm max-w-none" v-html="html"></div>
  </div>
</template>
