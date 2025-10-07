import { create } from 'zustand'
import axios from 'axios'

const useUserStore = create((set) => ({
  user: null,
  loading: true,

  // 유저 정보 불러오기
  fetchUser: async () => {
    try {
      const response = await axios.get(
        'http://localhost:8080/api/v1/user/me', 
        { withCredentials: true, },
      )
    } catch (error) {
      set({ user: null, loading: false })
    } 
  },

  // 유저 설정
  setUser: (user) => set({ user }),

  // 로그아웃
  logout: async () => {
    try {
      await axios.post(
        'http://localhost:8080/api/v1/auth/logout',
        null,
        { withCredentials: true, },
      )
    } finally {
      set({ user: null })
    }
  },
}))

export default useUserStore;