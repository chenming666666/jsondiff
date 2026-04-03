<template>
  <div class="json-input-container">
    <div class="input-panel">
      <label class="panel-label">Original JSON</label>
      <textarea
        class="json-textarea"
        v-model="localLeftJson"
        placeholder="Paste original JSON here..."
        spellcheck="false"
      ></textarea>
    </div>
    <div class="input-panel">
      <label class="panel-label">Modified JSON</label>
      <textarea
        class="json-textarea"
        v-model="localRightJson"
        placeholder="Paste modified JSON here..."
        spellcheck="false"
      ></textarea>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  leftJson: String,
  rightJson: String
})

const emit = defineEmits(['update:leftJson', 'update:rightJson'])

const localLeftJson = computed({
  get: () => props.leftJson,
  set: (val) => emit('update:leftJson', val)
})

const localRightJson = computed({
  get: () => props.rightJson,
  set: (val) => emit('update:rightJson', val)
})
</script>

<style scoped>
.json-input-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.input-panel {
  display: flex;
  flex-direction: column;
}

.panel-label {
  font-weight: 600;
  margin-bottom: 8px;
  color: #555;
}

.json-textarea {
  width: 100%;
  min-height: 250px;
  padding: 12px;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 13px;
  line-height: 1.5;
  border: 1px solid #ddd;
  border-radius: 6px;
  background: #fff;
  resize: vertical;
  outline: none;
  transition: border-color 0.2s;
}

.json-textarea:focus {
  border-color: #4CAF50;
}
</style>
