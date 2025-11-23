import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import FocusPlaceholderInput from "../components/FocusPlaceholderInput";

const SignupPage = () => {

  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  // 회원가입
  const handleSignup = async (e) => {
    e.preventDefault(); // 새로고침 방지

    try {
      const response = await axios.post(
        `${apiUrl}/api/v1/auth/signup`,
        { username, password, email },
        { withCredentials: true }
      );

      if (response.status === 201) {
        navigate('/signup/success');
      }
    } catch(error) {
      console.log('회원가입 실패: ', error.response?.data || error.message);
      alert(error.response.data.message);
    }
  }

  return (
    <>
      <div className='block w-full min-h-full flex justify-center items-center'>
        <div className='block w-lg py-18 px-22 flex flex-col justify-center border border-zinc-100 rounded-3xl bg-white shadow-lg'>
          <h1 className='text-3xl text-center font-bold mb-4'>회원가입</h1>
          <div className='text-base mt-8'>
            <form onSubmit={handleSignup} className='block flex flex-col justify-center'>
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
                <label htmlFor="email" className='text-left mb-1 font-bold px-2'>Email</label>
                <FocusPlaceholderInput 
                  type="text"
                  value={email}
                  id={email} 
                  name={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholderText="이메일을 입력하세요"
                />
              </div>
              <div className='flex flex-col mt-4'>
                <label htmlFor="password" className='text-left mb-1 font-bold px-2'>Password</label>
                <FocusPlaceholderInput 
                  type="text"
                  value={password}
                  id={password} 
                  name={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholderText="비밀번호를 입력하세요"
                />
              </div>
              <button type="submit" className="py-4 border rounded-xl text-white hover:bg-teal-600 active:bg-blue-700 bg-teal-500 mt-7 mb-6 cursor-pointer">확인</button>
              <div className='text-center'>
                이미 회원이신가요?<Link to="/login" className='inline-block ml-2 text-teal-600 cursor-pointer'>로그인하기</Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default SignupPage;