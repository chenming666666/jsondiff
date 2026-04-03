<template>
  <div class="app-container">
    <h1 class="title">JSON Diff Visualization</h1>
    <Toolbar
      @compare="handleCompare"
      @clear="handleClear"
      :loading="loading"
    />
    <div class="content-container">
      <JsonInput
        v-model:left-json="leftJson"
        v-model:right-json="rightJson"
      />
      <JsonDiffViewer
        v-if="diffResult"
        :diff-result="diffResult"
        :left-formatted="leftFormatted"
        :right-formatted="rightFormatted"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import Toolbar from './components/Toolbar.vue'
import JsonInput from './components/JsonInput.vue'
import JsonDiffViewer from './components/JsonDiffViewer.vue'
import { formatJson } from './services/api.js'

const leftJson = ref('')
const rightJson = ref('')
const leftFormatted = ref('')
const rightFormatted = ref('')
const diffResult = ref(null)
const loading = ref(false)

const handleCompare = async () => {
  if (!leftJson.value.trim() || !rightJson.value.trim()) {
    alert('Please enter JSON in both input fields')
    return
  }

  loading.value = true
  try {
    const response = await formatJson(leftJson.value, rightJson.value)
    if (response.success) {
      leftFormatted.value = response.formatted1
      rightFormatted.value = response.formatted2
      diffResult.value = { left: response.formatted1, right: response.formatted2 }
    } else {
      alert('Error: ' + response.error)
    }
  } catch (error) {
    alert('Request failed: ' + error.message)
  } finally {
    loading.value = false
  }
}

const handleClear = () => {
  leftJson.value = ''
  rightJson.value = ''
  leftFormatted.value = ''
  rightFormatted.value = ''
  diffResult.value = null
}
</script>

<style scoped>
.app-container {
  max-width: 1600px;
  margin: 0 auto;
  padding: 20px;
}

.title {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
  font-size: 24px;
}

.content-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>
