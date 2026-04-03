const API_BASE = '/api'
const REQUEST_TIMEOUT = 30000 // 30 seconds timeout

export async function formatJson(json1, json2) {
  const controller = new AbortController()
  const timeoutId = setTimeout(() => controller.abort(), REQUEST_TIMEOUT)

  try {
    const response = await fetch(`${API_BASE}/format`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ json1, json2 }),
      signal: controller.signal
    })

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return await response.json()
  } catch (error) {
    if (error.name === 'AbortError') {
      throw new Error('Request timed out after 30 seconds. Try with a smaller JSON.')
    }
    throw error
  } finally {
    clearTimeout(timeoutId)
  }
}
