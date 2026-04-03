<template>
  <div class="diff-container">
    <div class="diff-header">Comparison Result</div>
    <div class="diff-panels">
      <div class="diff-panel">
        <div class="panel-header">Original</div>
        <pre class="diff-content" v-html="highlightedLeft"></pre>
      </div>
      <div class="diff-panel">
        <div class="panel-header">Modified</div>
        <pre class="diff-content" v-html="highlightedRight"></pre>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { diffLines } from 'diff'

const props = defineProps({
  diffResult: Object,
  leftFormatted: String,
  rightFormatted: String
})

const highlightedLeft = computed(() => {
  return highlightDiff(props.leftFormatted, props.rightFormatted, 'left')
})

const highlightedRight = computed(() => {
  return highlightDiff(props.leftFormatted, props.rightFormatted, 'right')
})

function highlightDiff(left, right, side) {
  const leftLines = left.split('\n')
  const rightLines = right.split('\n')
  const diffs = diffLines(leftLines.join('\n'), rightLines.join('\n'))

  if (side === 'left') {
    let result = ''
    let leftIdx = 0
    diffs.forEach(part => {
      const lines = part.value.split('\n')
      if (lines[lines.length - 1] === '') lines.pop()
      lines.forEach(line => {
        if (part.added) {
          // Added in right, nothing in left
        } else if (part.removed) {
          // Removed from left, mark as deleted
          result += `<span class="diff-removed">${escapeHtml(line)}</span>\n`
          leftIdx++
        } else {
          // Unchanged
          result += `${escapeHtml(line)}\n`
          leftIdx++
        }
      })
    })
    return result
  } else {
    let result = ''
    let rightIdx = 0
    diffs.forEach(part => {
      const lines = part.value.split('\n')
      if (lines[lines.length - 1] === '') lines.pop()
      lines.forEach(line => {
        if (part.removed) {
          // Removed from left, nothing in right
        } else if (part.added) {
          // Added in right, mark as added
          result += `<span class="diff-added">${escapeHtml(line)}</span>\n`
          rightIdx++
        } else {
          // Unchanged
          result += `${escapeHtml(line)}\n`
          rightIdx++
        }
      })
    })
    return result
  }
}

function escapeHtml(text) {
  const div = document.createElement('div')
  div.textContent = text
  return div.innerHTML
}
</script>

<style scoped>
.diff-container {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  background: #fff;
}

.diff-header {
  background: #333;
  color: #fff;
  padding: 12px 16px;
  font-weight: 600;
  font-size: 14px;
}

.diff-panels {
  display: grid;
  grid-template-columns: 1fr 1fr;
}

.diff-panel {
  border-right: 1px solid #eee;
}

.diff-panel:last-child {
  border-right: none;
}

.panel-header {
  background: #f8f9fa;
  padding: 8px 12px;
  font-weight: 600;
  font-size: 13px;
  color: #666;
  border-bottom: 1px solid #eee;
}

.diff-content {
  padding: 12px;
  margin: 0;
  font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
  font-size: 13px;
  line-height: 1.6;
  white-space: pre-wrap;
  word-break: break-all;
  max-height: 600px;
  overflow-y: auto;
}

:deep(.diff-added) {
  background: #e6ffec;
  display: inline-block;
  width: 100%;
}

:deep(.diff-removed) {
  background: #ffebe9;
  display: inline-block;
  width: 100%;
}
</style>
