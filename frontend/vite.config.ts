import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import { fileURLToPath, URL } from 'node:url'

export default defineConfig({
  // When GITHUB_PAGES=true, assets are served from /Book-My-Bus/
  // Change 'Book-My-Bus' if your GitHub repo name is different
  base: process.env.GITHUB_PAGES ? '/Book-My-Bus/' : '/',
  plugins: [react(), tailwindcss()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
})
