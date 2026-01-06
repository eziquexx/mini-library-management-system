import { useState } from "react";
import useUserStore from "../../stores/useUserStore";
import { Link, useNavigate } from "react-router-dom";
import FocusPlaceholderInput from "../../components/users/FocusPlaceholderInput";
import axios from "axios";


const AdminLoginPage = () => {

  const { fetchUser } = useUserStore();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const apiUrl = `http://localhost:8080/api/v1`;

  const handleLogin = async (e) => {
    e.preventDefault(); // 새로고침 방지

    try {
      await axios.post(
        `http://localhost:8080/api/v1/auth/signin`,
        { username, password },
        { withCredentials: true }
      );

      await fetchUser();

      navigate('/admin');
      alert("로그인 성공");

    } catch (error) {
      console.log(error);
      alert('로그인 실패');
    }
  }

  return (
    <div className="flex h-screen justify-center items-center bg-[#f9fafc]">
      <div className="px-16 py-18 rounded-3xl bg-white shadow-[0px_0px_14px_rgba(0,0,0,0.08)]">
        <div className="w-80">
          <h2 className="text-xl font-bold text-center mb-8">관리자 로그인</h2>
          <form onSubmit={handleLogin} className='block flex flex-col justify-center'>
            <div className='flex flex-col'>
              <label htmlFor="username" className='text-left mb-1 font-bold px-2'>ID</label>
              <FocusPlaceholderInput 
                type="text"
                value={username}
                id={username} 
                name={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholderText="아이디를 입력하세요"
              />
            </div>
            <div className='flex flex-col mt-4'>
              <label htmlFor="password" className='text-left mb-1 font-bold px-2'>Password</label>
              <FocusPlaceholderInput 
                type="password"
                value={password}
                id={password} 
                name={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholderText={"비밀번호를 입력하세요"}
              />
            </div>
            <button type="submit" className="py-4 border rounded-xl text-white hover:bg-teal-600 active:bg-blue-700 bg-teal-500 mt-7 mb-6 cursor-pointer">로그인</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default AdminLoginPage;