import { create } from 'zustand'
import axios from 'axios'

const apiUrl = import.meta.env.VITE_API_URL;

const useUserStore = create((set) => ({
  user: null,
  loading: true,

  // 유저 설정
  setUser: (user) => set({ user }),

  // 유저 정보 불러오기
  fetchUser: async () => {
    set({ loading: true }); // 요청 시작 시 로딩 true
    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/user/me`, 
        { withCredentials: true, },
      );

      console.log("data: ", response.data.data);
      set({user: response.data.data, loading: false});

    } catch (err) {
      console.error("오류발생: ", err);
      set({ user: null, loading: false })
    } 
  },

  // 로그아웃
  logout: async () => {
    try {
      await axios.post(
        `${apiUrl}/api/v1/auth/logout`,
        null,
        { withCredentials: true, },
      )
    } finally {
      set({ user: null })
    }
  },

}))

export default useUserStore;