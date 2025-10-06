import React, { useState } from 'react'
import axios from 'axios'

const LoginPage = () => {
  
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault(); // 새로고침 방지

    try {
      const response = await axios.post('http://localhost:8080/api/v1/auth/signin', 
        { username, password },
        { withCredential: true }
      );
    } catch (error) {
      console.log('로그인 실패: ', error.response?.data || error.message);
      alert('로그인 실패');
    }
  }

  return (
    <>
      <h1 className='text-3xl'>로그인 페이지</h1>
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="username">ID: </label>
          <input type="text" value={username}
            onChange={(e) => setUsername(e.target.value)}
            id="username" name="username" />
        </div>
        <div>
          <label htmlFor="password">Password: </label>
          <input type="password" value={password}
            onChange={(e) => setPassword(e.target.value)}
            id="password" name="password" />
        </div>
        <button type="submit">로그인</button>
      </form>
    </>
  );
}

export default LoginPage;