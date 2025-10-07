import React, { useState } from 'react'
import axios from 'axios'
import FocusPlaceholderInput from '../components/FocusPlaceholderInput';
import { Link, useNavigate } from 'react-router-dom';

const LoginPage = () => {
  
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault(); // 새로고침 방지

    try {
      const response = await axios.post('http://localhost:8080/api/v1/auth/signin', 
        { username, password },
        { withCredentials: true }
      );

      // 로그인 성공 시 home으로 이동
      if (response.status === 200) {
        navigate('/');
      }
    
    } catch (error) {
      console.log('로그인 실패: ', error.response?.data || error.message);
      alert('로그인 실패');
    }
  }

  return (
    <>
      <div className='block w-full h-screen flex justify-center items-center bg-zinc-50'>
        <div className='block w-lg h-auto py-18 px-22 flex flex-col justify-center border border-zinc-100 rounded-3xl bg-white shadow-lg'>
          <h1 className='text-3xl text-center font-bold mb-4'>로그인</h1>
          <div className='text-base mt-8'>
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
              <button type="submit" className="py-4 border rounded-xl text-blue-600 text-white hover:bg-blue-600 active:bg-blue-700 bg-blue-500 mt-7 mb-6 cursor-pointer">로그인</button>
              <div className='text-center'>
                아직 회원이 아닌가요?<Link to="/signup" className='inline-block ml-2 text-blue-500 cursor-pointer'>회원가입하기</Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}

export default LoginPage;