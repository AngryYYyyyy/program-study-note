/*
 * @Author: ch cwl_ch@163.com
 * @Date: 2022-11-21 17:23:51
 * @LastEditors: ch
 * @LastEditTime: 2022-12-30 16:32:05
 * @Description: file content
 */
import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'
import fs from 'fs';
import path from 'path';
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    uni(),
  ],
  server: {
    // 浏览器本地开发代理配置
    proxy: {
      "/api": {
        target: "http://localhost:8081",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "/")
      },
      "/sseApi": {
        target: "http://localhost:9000",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/sseApi/, "/")
      },

      "/payApi": {
        target: "http://localhost:9001",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/payApi/, "/")
      },
    },
  },
})
